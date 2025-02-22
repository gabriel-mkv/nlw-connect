package br.com.devgabrel.events.dto;

/**
 * Record (DTO) que representa uma mensagem de erro genérica.
 * Usado para retornar informações de erro em respostas da API.
 *
 * @param message A mensagem de erro descritiva.
 */
public record ErrorMessage(String message) {

}