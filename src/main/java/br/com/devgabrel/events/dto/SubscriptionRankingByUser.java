package br.com.devgabrel.events.dto;

/**
 * Record (DTO) que representa a posição de um usuário específico no ranking de inscrições.
 *
 * @param item As informações do usuário no ranking, representadas por um objeto {@link SubscriptionRankingItem}.
 * @param position A posição do usuário no ranking (ex: 1 para o primeiro lugar, 2 para o segundo, etc.).
 */
public record SubscriptionRankingByUser(SubscriptionRankingItem item, Integer position) {

}