package br.com.devgabrel.events.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devgabrel.events.dto.SubscriptionRankingByUser;
import br.com.devgabrel.events.dto.SubscriptionRankingItem;
import br.com.devgabrel.events.dto.SubscriptionResponse;
import br.com.devgabrel.events.exception.EventNotFoundException;
import br.com.devgabrel.events.exception.SubscriptionConflitException;
import br.com.devgabrel.events.exception.UserIndicadorNotFoundException;
import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.model.Subscription;
import br.com.devgabrel.events.model.User;
import br.com.devgabrel.events.repo.EventRepo;
import br.com.devgabrel.events.repo.SubscriptionRepo;
import br.com.devgabrel.events.repo.UserRepo;

@Service
public class SubscriptionService {

    @Autowired
    private EventRepo evtRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SubscriptionRepo subRepo;

    /**
     * Cria uma nova inscrição para um evento.
     *
     * @param eventName O "pretty name" do evento.
     * @param user      O usuário a ser inscrito.  Se o usuário já existir no banco de dados (mesmo email),
     *                  o usuário existente será usado. Caso contrário, um novo usuário será criado.
     * @param userId    (Opcional) O ID do usuário que indicou a inscrição. Se fornecido, o usuário com este ID
     *                  será associado como o indicador. Se não for fornecido, a inscrição não terá um indicador.
     * @return Um objeto `SubscriptionResponse` contendo o número da inscrição e a URL de acesso.
     * @throws EventNotFoundException Se o evento com o "pretty name" fornecido não for encontrado.
     * @throws UserIndicadorNotFoundException Se o `userId` for fornecido, mas o usuário com esse ID não existir.
     * @throws SubscriptionConflitException Se o usuário já estiver inscrito no evento.
     */
    public SubscriptionResponse createNewSubscription(String eventName, User user, Integer userId) {

        Event evt = evtRepo.findByPrettyName(eventName);
        if (evt == null) {
            throw new EventNotFoundException("Evento " + eventName + " não existe!");
        }

        User userRec = userRepo.findByEmail(user.getEmail());
        if (userRec == null) {
            userRec = userRepo.save(user);
        }

        User indicador = null;
        if (userId != null) {
            indicador = userRepo.findById(userId).orElse(null);
            if (indicador == null) {
                throw new UserIndicadorNotFoundException("Usuário " + userId + " indicador não existe!");
            }
        }

        Subscription subs = new Subscription();
        subs.setEvent(evt);
        subs.setSubscriber(userRec);
        subs.setIndication(indicador);

        Subscription tmpSub = subRepo.findByEventAndSubscriber(evt, userRec);
        if (tmpSub != null) {
            throw new SubscriptionConflitException("Usuário " + userRec.getName() + " já está inscrito no evento " + evt.getTitle());
        }

        Subscription res = subRepo.save(subs);
        return new SubscriptionResponse(res.getSubscriptionNumber(), "http://codecraft.com/subscription/" + res.getEvent().getPrettyName() + "/" + res.getSubscriber().getId());
    }

    /**
     * Obtém o ranking completo de um evento.
     *
     * @param prettyName O "pretty name" do evento para o qual se deseja obter o ranking.
     * @return Uma lista de objetos `SubscriptionRankingItem`, representando o ranking dos usuários no evento.
     * @throws EventNotFoundException Se o evento com o "pretty name" fornecido não for encontrado.
     */
    public List<SubscriptionRankingItem> getCompleteRanking(String prettyName) {
        Event evt = evtRepo.findByPrettyName(prettyName);
        if (evt == null) {
            throw new EventNotFoundException("Ranking do evento " + prettyName + " não existe!");
        }
        return subRepo.generateRanking(evt.getEventId());
    }

    /**
     * Obtém a posição de um usuário específico no ranking de um evento.
     *
     * @param prettyName O "pretty name" do evento.
     * @param userId     O ID do usuário para o qual se deseja obter a posição no ranking.
     * @return Um objeto `SubscriptionRankingByUser` contendo as informações do usuário no ranking, incluindo sua posição.
     * @throws EventNotFoundException Se o evento com o "pretty name" fornecido não for encontrado (lançado por `getCompleteRanking`).
     * @throws UserIndicadorNotFoundException Se não houver inscrições com indicação do usuário especificado (ou seja, o usuário não está no ranking).
     */
    public SubscriptionRankingByUser getRankingByUser(String prettyName, Integer userId) {
        List<SubscriptionRankingItem> ranking = getCompleteRanking(prettyName);

        SubscriptionRankingItem item = ranking.stream().filter(r -> r.userId().equals(userId)).findFirst().orElse(null);
        if (item == null) {
            throw new UserIndicadorNotFoundException("Não há inscrições com indicação do usuário " + userId);
        }

        Integer rankingPosition = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).userId().equals(userId))
                .findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, rankingPosition + 1);
    }
}