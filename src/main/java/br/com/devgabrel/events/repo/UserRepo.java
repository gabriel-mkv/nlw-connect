package br.com.devgabrel.events.repo;

import org.springframework.data.repository.CrudRepository;

import br.com.devgabrel.events.model.User;

/**
 * Repositório para a entidade {@link User}, permitindo realizar operações de CRUD (Create, Read, Update, Delete)
 * no banco de dados.
 */
public interface UserRepo extends CrudRepository<User, Integer> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     *
     * @param email O endereço de e-mail do usuário a ser buscado.
     * @return O usuário com o endereço de e-mail fornecido.
     */
    public User findByEmail(String email);
}