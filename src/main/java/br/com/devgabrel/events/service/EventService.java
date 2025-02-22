package br.com.devgabrel.events.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.repo.EventRepo;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    /**
     * Adiciona um novo evento ao sistema.  Converte o título do evento para um "pretty name"
     * (minúsculas e espaços substituídos por hífens) antes de salvar.
     *
     * @param event O evento a ser adicionado.  Espera-se que o evento tenha um título.
     * @return O evento recém-adicionado, incluindo o "pretty name" gerado e o ID atribuído pelo banco de dados.
     */
    public Event addNewEvent(Event event) {
        event.setPrettyName(event.getTitle().toLowerCase().replaceAll(" ", "-"));
        return eventRepo.save(event);
    }

    /**
     * Busca todos os eventos cadastrados no sistema.
     *
     * @return Uma lista contendo todos os eventos.  Retorna uma lista vazia se não houver eventos cadastrados.
     */
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepo.findAll();
    }

    /**
     * Busca um evento pelo seu "pretty name".
     *
     * @param prettyName O "pretty name" do evento a ser buscado.
     * @return O evento correspondente ao "pretty name" fornecido.
     */
    public Event getByPrettyName(String prettyName) {
        return eventRepo.findByPrettyName(prettyName);
    }
}