package Data;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AverageExchangeRate {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final String EUR_CODE = "EUR";
    private static final String PLN_CODE = "PLN";

    public static void main(String[] args) {
        try {
            double exchangeRate = getExchangeRate(EUR_CODE, PLN_CODE);
            System.out.println("Average EUR to PLN exchange rate: " + exchangeRate);
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException {
        URL url = new URL(API_URL + baseCurrency);
        Scanner scanner = new Scanner(url.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();

        // Parse JSON response to retrieve the exchange rate
        String[] rates = response.split("\"rates\":\\{")[1].split("\\}")[0].split(",");
        double baseRate = 0;
        double targetRate = 0;
        for (String rate : rates) {
            String[] currencyRate = rate.split(":");
            String currencyCode = currencyRate[0].replaceAll("\"", "");
            double rateValue = Double.parseDouble(currencyRate[1]);
            if (currencyCode.equals(baseCurrency)) {
                baseRate = rateValue;
            } else if (currencyCode.equals(targetCurrency)) {
                targetRate = rateValue;
            }
        }

        return targetRate / baseRate;
    }
}
