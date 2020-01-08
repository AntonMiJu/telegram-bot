package com.objects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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
    @Column(name = "BID_DIFFERENCE")
    private Integer bidDifference;
    @Column(name = "ASK")
    private Integer ask;
    @Column(name = "ASK_DIFFERENCE")
    private Integer askDifference;
    @Column(name = "UPDATE_DATE")
    private LocalDate lastUpdateDate;

    @Override
    public String toString() {
        return currencyEmojiCode + currency + currencyEmojiCode + ": \n" +
                "bid - " + bid + " (" + getEmoji(bidDifference) + " " + bidDifference + ") \n" +
                "ask - " + ask + " (" + getEmoji(askDifference) + " " + askDifference + ") \n";
    }

    private String getEmoji(Integer difference) {
        if (difference > 0)
            return increaseEmoji;
        else
            return decreaseEmoji;
    }
}