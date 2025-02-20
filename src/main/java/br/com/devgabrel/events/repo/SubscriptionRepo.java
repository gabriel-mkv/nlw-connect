package br.com.devgabrel.events.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.model.Subscription;
import br.com.devgabrel.events.model.User;

public interface SubscriptionRepo extends CrudRepository<Subscription, Integer> {
    public Subscription findByEventAndSubscriber(Event evt, User user);
}
