package br.com.devgabrel.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidade que representa uma inscrição de um usuário em um evento.
 */
@Entity
@Table(name = "tbl_subscription")
public class Subscription {

    /**
     * Número único da inscrição, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_number")
    private Integer subscriptionNumber;

    /**
     * Evento ao qual o usuário está inscrito.  Representa a relação Many-to-One com a entidade {@link Event}.
     */
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    /**
     * Usuário que se inscreveu no evento.  Representa a relação Many-to-One com a entidade {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "subscribed_user_id")
    private User subscriber;

    /**
     * Usuário que indicou a inscrição (opcional).  Pode ser `null` se a inscrição não foi feita por indicação.
     * Representa a relação Many-to-One com a entidade {@link User}.
     */
    @ManyToOne
    @JoinColumn(name = "indication_user_id", nullable = true)
    private User indication;

    public Integer getSubscriptionNumber() {
        return subscriptionNumber;
    }

    public void setSubscriptionNumber(Integer subscriptionNumber) {
        this.subscriptionNumber = subscriptionNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getIndication() {
        return indication;
    }

    public void setIndication(User indication) {
        this.indication = indication;
    }
}