package com.objects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CURRENCY")
@Getter
@Setter
public class Currency {
    private static final String increaseEmoji = "\uD83D\uDCC8";
    private static final String decreaseEmoji = "\uD83D\uDCC9";

    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "CURRENCY_EMOJI")
    private String currencyEmojiCode;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "BID")
    private Integer bid;
    @Column(name = "ASK")
    private Integer ask;

    @Override
    public String toString() {
        return currencyEmojiCode + currency + currencyEmojiCode + ": \n" +
                "bid - " + bid + " " +
                "ask - " + ask;
    }
}
