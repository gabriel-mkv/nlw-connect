package br.com.devgabrel.events.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.devgabrel.events.model.Event;

public interface EventRepo extends CrudRepository<Event, Integer> {
    public Event findByPrettyName(String prettyName);
}