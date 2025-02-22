package br.com.devgabrel.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.devgabrel.events.model.Event;
import br.com.devgabrel.events.service.EventService;

@RestController
public class EventController {
    
    @Autowired
    private EventService eventService;

    /**
     * Endpoint para adicionar um novo evento.
     *
     * @param newEvent O evento a ser adicionado, recebido no corpo da requisição.
     * @return O evento recém-adicionado.
     */
    @PostMapping("/events")
    public Event addNewEvent(@RequestBody Event newEvent) {
        return eventService.addNewEvent(newEvent);
    }

    /**
     * Endpoint para buscar todos os eventos.
     *
     * @return Uma lista contendo todos os eventos cadastrados.
     */
    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    /**
     * Endpoint para buscar um evento pelo seu "pretty name".
     *
     * @param prettyName O "pretty name" do evento a ser buscado, passado como parte da URL.
     * @return Um ResponseEntity contendo:
     *         - Um código 200 (OK) e o evento no corpo, se o evento for encontrado.
     *         - Um código 404 (Not Found), se o evento não for encontrado.
     */
    @GetMapping("/events/{prettyName}")
    public ResponseEntity<Event> getEventByPrettyName(@PathVariable String prettyName) {
        Event evt = eventService.getByPrettyName(prettyName);
        if (evt != null) {
            return ResponseEntity.ok().body(evt);
        }
        return ResponseEntity.notFound().build();
    }
}