package Data;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class AverageExchangeRate {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    private static final String EUR_CODE = "EUR";
    public static final String PLN_CODE = "PLN";
    private static final String USD_CODE = "USD";
    
      
    public double callExchangeRateMethod(String baseCurrency) throws IOException
    {
    	 return getExchangeRate(baseCurrency, PLN_CODE);
    }
    
    
    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException {
        URL url = new URL(API_URL + baseCurrency);
        Scanner scanner = new Scanner(url.openStream());
        String response = scanner.useDelimiter("\\Z").next();
        scanner.close();
        double baseRate = 0;
        double targetRate = 0;
        if(!baseCurrency.equals(PLN_CODE))
        {
        	// Parse JSON response to retrieve the exchange rate
            String[] rates = response.split("\"rates\":\\{")[1].split("\\}")[0].split(",");
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
        }else {
             baseRate = 1;
            targetRate = 1;
        }
        

        return targetRate / baseRate;
    }
}
