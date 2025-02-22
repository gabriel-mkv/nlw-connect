package br.com.devgabrel.events.dto;

/**
 * Record (DTO) que representa um item do ranking de inscrições, contendo informações sobre um usuário
 * e o número de inscritos que ele indicou.
 *
 * @param subscribers O número de inscritos que este usuário indicou.
 * @param userId O ID do usuário no ranking.
 * @param name O nome do usuário no ranking.
 */
public record SubscriptionRankingItem(Long subscribers, Integer userId, String name) {

}