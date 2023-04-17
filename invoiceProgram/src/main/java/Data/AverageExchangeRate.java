package Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
/**
 * dosent work yet
 */
public class AverageExchangeRate {
    private static final String URL = "https://www.nbp.pl/kursy/kursya.html";
    private static final String TABLE_SUMMARY = "Kursy walut: tabela A";
    private static final String EUR_CODE = "EUR";
    
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect(URL).get();
            Element table = doc.select("table[summary=" + TABLE_SUMMARY + "]").first();
            if (table == null) {
                throw new RuntimeException("Could not find the exchange rate table.");
            }
            Elements rows = table.select("tr");
            String averageRate = null;
            for (Element row : rows) {
                Elements cells = row.select("td");
                if (cells.size() == 5 && EUR_CODE.equals(cells.get(1).text())) {
                    averageRate = cells.get(2).text();
                    break;
                }
            }
            if (averageRate == null) {
                throw new RuntimeException("Could not find the average exchange rate for EUR.");
            }
            System.out.println("Average EUR to PLN exchange rate: " + averageRate);
        } catch (IOException e) {
            System.err.println("Error connecting to the website: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
