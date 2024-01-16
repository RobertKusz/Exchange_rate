package pl.Exchange_Rate.Exchange_Rate.API.api_connetion;

import org.springframework.stereotype.Component;
import pl.Exchange_Rate.Exchange_Rate.exceptions.exception.ConnectionFailException;
import pl.Exchange_Rate.Exchange_Rate.exceptions.exception.InvalidUrlException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

@Component
public class NbpApi {

    public String getTodaysCurrency() {
        String link = "http://api.nbp.pl/api/exchangerates/tables/A/";
        return getDataFromApi(link);
    }
    public String get52WeeksData(String code) {
        LocalDate now = LocalDate.now();
        LocalDate nowMinus52weeks = now.minusWeeks(52);
        String link = "http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/last/52/";
        return getDataFromApi(link);
    }
    public String getDataFromTo(String code, LocalDate from, LocalDate to) {
        URL url = null;
        String link = "http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/"+from+"/"+to+"/" ;
        return getDataFromApi(link);
    }

    private static String getDataFromApi(String link) {
        try {
            URL url = new URL(link);
            return getData(url);
        } catch (MalformedURLException e) {
            throw new InvalidUrlException(link);
        } catch (IOException e) {
            throw new ConnectionFailException(e);
        }
    }

    
    private static String getData(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        return changeJsonToString(conn);
    }

    private static String changeJsonToString(HttpURLConnection conn) throws IOException {
        StringBuilder informationString = new StringBuilder();
        try (InputStream inputStream = conn.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                informationString.append(line);
            }
        }
        return informationString.toString();
    }
}