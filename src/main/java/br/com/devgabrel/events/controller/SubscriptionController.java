package br.com.devgabrel.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.devgabrel.events.dto.ErrorMessage;
import br.com.devgabrel.events.dto.SubscriptionResponse;
import br.com.devgabrel.events.exception.EventNotFoundException;
import br.com.devgabrel.events.exception.SubscriptionConflitException;
import br.com.devgabrel.events.exception.UserIndicadorNotFoundException;
import br.com.devgabrel.events.model.User;
import br.com.devgabrel.events.service.SubscriptionService;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subService;

    /**
     * Endpoint para criar uma nova inscrição em um evento.  Possui duas variações de URL:
     *  - `/subscription/{prettyName}`: Requer o `prettyName` do evento e os dados do assinante no corpo da requisição.
     *  - `/subscription/{prettyName}/{userId}`: Requer o `prettyName` do evento, os dados do assinante e o `userId`.
     *
     * @param prettyName  O "pretty name" do evento para o qual o usuário está se inscrevendo.
     * @param subscriber  Os dados do usuário que está se inscrevendo (nome e email), passados no corpo da requisição (RequestBody).
     * @param userId  (Opcional) O ID do usuário indicador (quem indicou o assinante). Se não for fornecido, é considerado que não houve indicação.
     * @return Um ResponseEntity contendo:
     *         - 200 (OK) e o objeto SubscriptionResponse se a inscrição for bem-sucedida.
     *         - 404 (Not Found) e um ErrorMessage se o evento não for encontrado (EventNotFoundException) ou o usuário indicador não for encontrado (UserIndicadorNotFoundException).
     *         - 409 (Conflict) e um ErrorMessage se já houver uma inscrição conflitante (SubscriptionConflitException).
     *         - 400 (Bad Request) se houver algum outro erro.
     */
    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(@PathVariable String prettyName,
                                                @RequestBody User subscriber,
                                                @PathVariable(required = false) Integer userId) {
        try {
            SubscriptionResponse res = subService.createNewSubscription(prettyName, subscriber, userId);
            if (res != null) {
                return ResponseEntity.ok(res);
            }

        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));

        } catch (SubscriptionConflitException ex) {
            return ResponseEntity.status(409).body(new ErrorMessage(ex.getMessage()));

        } catch (UserIndicadorNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Endpoint para obter o ranking dos 3 primeiros usuários de um evento.
     *
     * @param prettyName O "pretty name" do evento para o qual se deseja obter o ranking.
     * @return Um ResponseEntity contendo:
     *         - 200 (OK) e uma lista contendo os 3 primeiros usuários do ranking, se o evento for encontrado.
     *         - 404 (Not Found) e um ErrorMessage se o evento não for encontrado (EventNotFoundException).
     */
    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> getRanking(@PathVariable String prettyName) {
        try {
            return ResponseEntity.ok(subService.getCompleteRanking(prettyName).subList(0, 3));
        } catch (EventNotFoundException ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }

    /**
     * Endpoint para obter a posição de um usuário específico no ranking de um evento.
     *
     * @param prettyName O "pretty name" do evento para o qual se deseja obter o ranking.
     * @param userId   O ID do usuário para o qual se deseja obter a posição no ranking.
     * @return Um ResponseEntity contendo:
     *         - 200 (OK) e a posição do usuário no ranking.
     *         - 404 (Not Found) e um ErrorMessage se o evento não for encontrado ou se ocorrer algum outro erro (Exception genérica).
     */
    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> getRankingByUser(@PathVariable String prettyName, @PathVariable Integer userId) {
        try {
            return ResponseEntity.ok(subService.getRankingByUser(prettyName, userId));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ErrorMessage(ex.getMessage()));
        }
    }
}