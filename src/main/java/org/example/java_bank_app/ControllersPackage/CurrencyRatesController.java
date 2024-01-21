package org.example.java_bank_app.ControllersPackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.CurrencyPackage.CurrencyRateAPI;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class CurrencyRatesController implements Initializable {
    @FXML
    ComboBox<CurrencyCode> SourceCCComboBox, TargetCCComboBox;
    @FXML
    Label RateLabel;
    @FXML
    TextField SourceValueTextField, TargetValueTextField;
    @FXML
    ListView<CurrencyCode> CCListView;
    @FXML
    LineChart<Date, Double> ExchangeRatesLineChart;
    @FXML
    CategoryAxis DateAxis;
    @FXML
    NumberAxis RateAxis;

    private double finalRate;
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        CCListView.setItems(currencyCodes);
        CCListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        CCListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            System.out.println("zmieniono zaznaczenie ");
        });

        //Label Area
        RateLabel.setAlignment(Pos.CENTER);

        //Line Chart & Axis Area
        RateAxis.setUpperBound(getMaxYValue());






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
            RateLabel.setText("Exchange Rate: " + decimalFormat.format(outputRate));
            TargetValueTextField.clear();
            return outputRate;
    }

    private double getMaxYValue() {
        ArrayList<Currency> allCurrencies = new ArrayList<>(CurrencyRateAPI.getCurrencyAray());
        double max = Double.MIN_VALUE;

        for (Currency currency : allCurrencies)
            if (currency.getCurrencyRate() > max) max = currency.getCurrencyRate();

        return max;
    }

}
