package br.com.devgabrel.events.exception;

/**
 * Exceção lançada quando ocorre um conflito de inscrição, por exemplo,
 * quando um usuário tenta se inscrever em um evento no qual já está inscrito.
 */
public class SubscriptionConflitException extends RuntimeException {

    /**
     * Construtor da exceção.
     *
     * @param message A mensagem de erro a ser associada à exceção.
     */
    public SubscriptionConflitException(String message) {
        super(message);
    }

}