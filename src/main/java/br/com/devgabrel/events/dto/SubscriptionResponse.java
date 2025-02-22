package br.com.devgabrel.events.dto;

/**
 * Record (DTO) que representa a resposta da criação de uma inscrição.
 *
 * @param subscriptionNumber O número da inscrição gerado.
 * @param designation A URL de designação da inscrição.
 */
public record SubscriptionResponse(Integer subscriptionNumber, String designation) {

}