package objects;

public class Currency {
    private String currency;
    private String bid;
    private String ask;

    public Currency(String currency, String bid, String ask) {
        this.currency = currency;
        this.bid = bid;
        this.ask = ask;
    }

    public String getNameOfCurrency() {
        return currency;
    }

    public String getBid() {
        return bid;
    }

    public String getAsk() {
        return ask;
    }

    @Override
    public String toString() {
        return currency + ": \n" +
                "bid - " + bid + " " +
                "ask - " + ask;
    }
}
