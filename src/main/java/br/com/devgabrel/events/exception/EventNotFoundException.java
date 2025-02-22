package br.com.devgabrel.events.exception;

/**
 * Exceção lançada quando um evento não é encontrado.
 */
public class EventNotFoundException extends RuntimeException {

    /**
     * Construtor da exceção.
     *
     * @param message A mensagem de erro a ser associada à exceção.
     */
    public EventNotFoundException(String message) {
        super(message);
    }

}