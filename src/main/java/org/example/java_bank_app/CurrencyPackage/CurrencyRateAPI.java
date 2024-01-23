package org.example.java_bank_app.CurrencyPackage;

import com.google.gson.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class CurrencyRateAPI {
    public static double currencyRate(CurrencyCode currencyCode){
        HttpURLConnection connection = null;
        try {
            // Adres URL do API NBP z kursami walut
            String url = "http://api.nbp.pl/api/exchangerates/tables/A?format=json";

            // Tworzymy obiekt URL i otwieramy połączenie HTTP
            connection = (HttpURLConnection) new URI(url).toURL().openConnection();
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

    public static HashMap<CurrencyCode, ArrayList<Currency>> getCurrenciesDayRange(int dayRange){
        HashMap<CurrencyCode, ArrayList<Currency>> currencies = new HashMap<>();
        HttpURLConnection connection = null;
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate selectedDate = currentDate.minusDays(dayRange);
                
            String url = "http://api.nbp.pl/api/exchangerates/tables/A/" + selectedDate + "/" + currentDate + "?format=json";

            // Tworzymy obiekt URL i otwieramy połączenie HTTP
            connection = (HttpURLConnection) new URI(url).toURL().openConnection();
            connection.setRequestMethod("GET");

            // Odczytujemy dane z odpowiedzi
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonArray tablesArray = JsonParser.parseString(response.toString()).getAsJsonArray();

            for(JsonElement tableElement : tablesArray){
                JsonObject tableObject = tableElement.getAsJsonObject();
                String effectiveDate = tableObject.get("effectiveDate").getAsString();
                JsonArray ratesArray = tableObject.getAsJsonArray("rates");

                for (JsonElement rateElement : ratesArray) {
                    JsonObject currency = rateElement.getAsJsonObject();
                    String CC = currency.get("code").getAsString();
                    double CR = currency.get("mid").getAsDouble();

                    Currency newCurrency = new ManualCurrency(CurrencyCode.valueOf(CC), CR, LocalDate.parse(effectiveDate));

                    currencies.compute(newCurrency.getCurrencyCode(), (k,v) -> {
                        if(v == null){
                            ArrayList<Currency> newList = new ArrayList<>();
                            newList.add(newCurrency);
                            return newList;
                        }else {
                            v.add(newCurrency);
                            return v;
                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            if(connection != null) connection.disconnect();
        }
        return currencies;
    }

}
