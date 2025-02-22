package br.com.devgabrel.events.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade que representa um usuário no sistema.
 */
@Entity
@Table(name = "tbl_user")
public class User {

    /**
     * Identificador único do usuário. Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    /**
     * Nome do usuário. Não pode ser nulo e tem um tamanho máximo de 255 caracteres.
     */
    @Column(name = "user_name", length = 255, nullable = false)
    private String name;

    /**
     * Endereço de e-mail do usuário. Não pode ser nulo, tem um tamanho máximo de 255 caracteres e deve ser único no sistema.
     */
    @Column(name = "user_email", length = 255, nullable = false, unique = true)
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}