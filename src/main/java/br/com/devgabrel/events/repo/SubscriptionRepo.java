package br.com.devgabrel.events.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.devgabrel.events.dto.SubscriptionRankingItem;
import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.model.Subscription;
import br.com.devgabrel.events.model.User;

/**
 * Repositório para a entidade {@link Subscription}, permitindo realizar operações de CRUD (Create, Read, Update, Delete)
 * no banco de dados.
 */
public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {

    /**
     * Busca uma inscrição pelo evento e pelo usuário inscrito.
     *
     * @param evt  O evento da inscrição.
     * @param user O usuário inscrito.
     * @return A inscrição correspondente ao evento e usuário fornecidos.
     */
    public Subscription findByEventAndSubscriber(Event evt, User user);

    /**
     * Gera o ranking de inscrições para um determinado evento, ordenado pelo número de indicações.
     *
     * @param eventId O ID do evento para o qual se deseja gerar o ranking.
     * @return Uma lista de objetos {@link SubscriptionRankingItem}, contendo o número de indicações,
     *         o ID do usuário indicador e o nome do usuário indicador, ordenada pelo número de indicações
     *         em ordem decrescente.
     */
    @Query(value = "select count(subscription_number) as quantidade, indication_user_id, user_name" +
                " from tbl_subscription inner join tbl_user" +
                " on tbl_subscription.indication_user_id = tbl_user.user_id" +
                " where indication_user_id is not null and event_id = :eventId" +
                " group by indication_user_id" +
                " order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}