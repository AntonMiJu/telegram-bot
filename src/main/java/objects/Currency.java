package objects;

import lombok.AllArgsConstructor;
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
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "BID")
    private String bid;
    @Column(name = "ASK")
    private String ask;

    @Override
    public String toString() {
        return currency + ": \n" +
                "bid - " + bid + " " +
                "ask - " + ask;
    }
}
