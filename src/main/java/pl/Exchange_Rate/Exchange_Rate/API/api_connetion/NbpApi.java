package pl.Exchange_Rate.Exchange_Rate.API.api_connetion;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;

@Component
public class NbpApi {

    public String getTodaysCurrency() {
        URL url = null;
        try {
            url = new URL("http://api.nbp.pl/api/exchangerates/tables/A/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return getData(url);
    }
    public String get52WeeksData(String code) {
        LocalDate now = LocalDate.now();
        LocalDate nowMinus52weeks = now.minusWeeks(52);
        URL url = null;
        try {
            //url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/"+nowMinus52weeks+"/"+now+"/");
            url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/last/52/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return getData(url);
    }
    public String getDataFromTo(String code, LocalDate from, LocalDate to) {
        URL url = null;
        try {
            url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/"+code+"/"+to+"/"+from+"/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return getData(url);
    }


    private static String getData(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
        int responseCode = conn.getResponseCode();
            System.out.println(url.toString());
            System.out.println(responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
