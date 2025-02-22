package br.com.devgabrel.events.exception;

/**
 * Exceção lançada quando um usuário indicador (usuário que indicou outro usuário) não é encontrado.
 */
public class UserIndicadorNotFoundException extends RuntimeException {

    /**
     * Construtor da exceção.
     *
     * @param message A mensagem de erro a ser associada à exceção.
     */
    public UserIndicadorNotFoundException(String message) {
        super(message);
    }

}