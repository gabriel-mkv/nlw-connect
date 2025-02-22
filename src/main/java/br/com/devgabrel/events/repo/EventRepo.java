package br.com.devgabrel.events.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.devgabrel.events.model.Event;

/**
 * Repositório para a entidade {@link Event}, permitindo realizar operações de CRUD (Create, Read, Update, Delete)
 * no banco de dados.
 */
public interface EventRepo extends CrudRepository<Event, Integer> {

    /**
     * Busca um evento pelo seu "pretty name".
     *
     * @param prettyName O "pretty name" do evento a ser buscado.
     * @return O evento correspondente ao "pretty name" fornecido.
     */
    public Event findByPrettyName(String prettyName);
}