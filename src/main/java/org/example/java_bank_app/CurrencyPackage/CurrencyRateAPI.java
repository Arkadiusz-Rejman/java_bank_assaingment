package org.example.java_bank_app.CurrencyPackage;

import com.google.gson.*;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRateAPI {
    public static double currencyRate(CurrencyCode currencyCode){
        HttpURLConnection connection = null;
        try {
            // Adres URL do API NBP z kursami walut
            String url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

            // Tworzymy obiekt URL i otwieramy połączenie HTTP
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Odczytujemy dane z odpowiedzi
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parsowanie danych JSON
            JsonArray ratesArray = JsonParser.parseString(response.toString()).getAsJsonArray();
            JsonObject ratesObject = ratesArray.get(0).getAsJsonObject();
            JsonArray currenciesArray = ratesObject.getAsJsonArray("rates");


            // Przetwarzanie danych
            for (JsonElement element : currenciesArray.getAsJsonArray()) {
                JsonObject currency = element.getAsJsonObject();
                String CC = currency.get("code").getAsString();
                if (CC.equals(currencyCode.toString())) return currency.get("mid").getAsDouble();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if(connection != null) connection.disconnect();
        }

        return 1; //jeżeli nie zwrócono odpowiedzi w bloku przetwarzania danych to jest to PLN (kurs 1:1)
    }


}
