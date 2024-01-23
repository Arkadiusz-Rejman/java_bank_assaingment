package org.example.java_bank_app.ControllersPackage;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.CurrencyPackage.CurrencyRateAPI;
import org.example.java_bank_app.CurrencyPackage.ManualCurrency;

import javax.security.auth.callback.Callback;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CurrencyRatesController implements Initializable {
    @FXML
    ComboBox<CurrencyCode> SourceCCComboBox, TargetCCComboBox;
    @FXML
    Label RateLabel, DayLabel;
    @FXML
    TextField SourceValueTextField, TargetValueTextField;
    @FXML
    ListView<CurrencyCode> CCListView;
    @FXML
    LineChart<String, Double> ExchangeRatesLineChart;
    @FXML
    CategoryAxis DateAxis;
    @FXML
    NumberAxis RateAxis;
    @FXML
    Slider DaySlider;

    private double finalRate;
    HashMap<CurrencyCode, ArrayList<Currency>> currenciesDaysRange;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        currenciesDaysRange = new HashMap<>(CurrencyRateAPI.getCurrenciesDayRange(30));
        //HashMap u góry jest juz kopią, mozna go zmieniac SLIDER,
        //chciałbym aby ListView mogło być zaznaczane i odznaczane,
        //na tej podstawie beda widoczne trendy dotyczące zaznaczonych CurrencieCode

        //
        for (Map.Entry<CurrencyCode, ArrayList<Currency>> entry : currenciesDaysRange.entrySet()) {
            CurrencyCode currencyCode = entry.getKey();
            ArrayList<Currency> currencyList = entry.getValue();

            // Wyświetlenie danych dla danego CurrencyCode
            System.out.println("Currency Code: " + currencyCode);

            for (Currency currency : currencyList) {
                // Wyświetlenie danych dla każdej waluty w danym CurrencyCode
                System.out.println("  - Currency Name: " + currency.getCurrencyRate());
                System.out.println("    Exchange Rate: " + currency.getUpdateDate());
                // Dodaj inne pola, które chcesz wyświetlić
            }
        }

        //



        ObservableList<CurrencyCode> currencyCodes = FXCollections.observableArrayList(CurrencyCode.values());
        finalRate = 1;

        //ComboBox area
        SourceCCComboBox.setItems(currencyCodes);
        SourceCCComboBox.setValue(CurrencyCode.PLN);
        TargetCCComboBox.setItems(currencyCodes);
        TargetCCComboBox.setValue(CurrencyCode.PLN);

        SourceCCComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> finalRate = updateExchangeRateLabel());

        TargetCCComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> finalRate = updateExchangeRateLabel());


        //ListView Area
        CCListView.setItems(FXCollections.observableArrayList(currencyCodes).filtered(code -> code != CurrencyCode.PLN));

        CCListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CCListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setChartSeries(newValue);
        });

        //Label Area
        RateLabel.setAlignment(Pos.CENTER);

        //Line Chart & Axis Area
        RateAxis.setLabel("Exchange Rate");
        DateAxis.setLabel("Days");

        setChartSeries(CurrencyCode.BRL);

        //Slider Area
        DaySlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            DayLabel.setText(newValue.intValue() + " Days");

        });







    }


    @FXML
    public void calculateRate(){
        boolean isInputEmpty = SourceValueTextField.getText().isEmpty();
        boolean isBalanceValid = SourceValueTextField.getText().matches("\\d+(\\.\\d{1,2})?");

        if(!isBalanceValid || isInputEmpty) CustomAlert.showInfoAlert("wrong input");
        else{
            double calculatedValue = Double.parseDouble(SourceValueTextField.getText().trim()) * finalRate;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            TargetValueTextField.setText(String.valueOf(decimalFormat.format(calculatedValue)));
        }
    }

    //priv methods

    private double updateExchangeRateLabel() {
            double sourceRate = CurrencyRateAPI.currencyRate(SourceCCComboBox.getValue());
            double targetRate = CurrencyRateAPI.currencyRate(TargetCCComboBox.getValue());
            double outputRate = sourceRate / targetRate;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            RateLabel.setText("Exchange Rate: " + decimalFormat.format(outputRate));
            TargetValueTextField.clear();
            return outputRate;
    }

    private void setChartSeries(CurrencyCode currencyCode){

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(currencyCode.toString());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd");

        for (Currency currency : currenciesDaysRange.get(currencyCode)) {
            String formattedDate = currency.getUpdateDate().format(dateFormatter);
            series.getData().add(new XYChart.Data<>(formattedDate, currency.getCurrencyRate()));
        }

        ExchangeRatesLineChart.getData().add(series);
    }



}
