package pl.Exchange_Rate.Exchange_Rate.API.api_connetion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NbpApi {

    public String getDataNbpApi() {
        try {
            URL url = new URL("http://api.nbp.pl/api/exchangerates/tables/A/today/");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();

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
