package com.crawler;

import com.database.CurrencyDAO;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import com.objects.Currency;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class GoverlaCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");
    private static CurrencyDAO currencyDAO;

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the com.crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "https://goverla.ua/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://goverla.ua/");
    }

    @Override
    public void visit(Page page) {
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            Document doc = Jsoup.parse(htmlParseData.getHtml());
            Elements allElements = doc.select("div.gvrl-table-body");

            saveCurrencyRate(getNames(allElements), getBids(allElements), getAsks(allElements));
        }
    }

    private void saveCurrencyRate(Elements names, Elements bid, Elements ask){
        for (int i = 0; i < names.toArray().length; i++) {
            if (getCurrencyDAO().getByName(names.get(i).attr("title")) == null)
                continue;
            Currency currentCurrency = getCurrencyDAO().getByName(names.get(i).attr("title"));
            currentCurrency.setAsk(Integer.valueOf(ask.get(i).text()));
            currentCurrency.setBid(Integer.valueOf(bid.get(i).text()));
            System.out.println(currentCurrency.toString());
            getCurrencyDAO().update(currentCurrency);
        }
    }

    private Elements getNames(Elements allElements){
        return allElements.select("div.gvrl-table-cell").select("img[alt]");
    }

    private Elements getBids(Elements allElements){
        return allElements.select("div.gvrl-table-cell.bid");
    }

    private Elements getAsks(Elements allElements){
        return allElements.select("div.gvrl-table-cell.ask");
    }

    public static CurrencyDAO getCurrencyDAO(){
        if (currencyDAO == null)
            currencyDAO = new CurrencyDAO();
        return currencyDAO;
    }
}
