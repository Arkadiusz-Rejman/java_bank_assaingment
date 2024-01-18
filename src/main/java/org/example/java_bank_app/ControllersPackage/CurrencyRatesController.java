package org.example.java_bank_app.ControllersPackage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.CurrencyPackage.CurrencyRateAPI;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CurrencyRatesController implements Initializable {
    @FXML
    ComboBox<CurrencyCode> SourceCCComboBox, TargetCCComboBox;
    @FXML
    Label RateLabel;
    @FXML
    TextField SourceValueTextField, TargetValueTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<CurrencyCode> currencyCodes = FXCollections.observableArrayList(CurrencyCode.values());
        SourceCCComboBox.setItems(currencyCodes);
        SourceCCComboBox.setValue(CurrencyCode.PLN);
        TargetCCComboBox.setItems(currencyCodes);
        TargetCCComboBox.setValue(CurrencyCode.PLN);


        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // to moze byc do przelokowania

        SourceCCComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            double rate1 = CurrencyRateAPI.currencyRate(newValue);
            double rate2 = CurrencyRateAPI.currencyRate(TargetCCComboBox.getValue());
            double finalRate = rate2/rate1;
            RateLabel.setText("Exchange Rate: " + decimalFormat.format(finalRate));
        });

        TargetCCComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            double rate1 = CurrencyRateAPI.currencyRate(newValue);
            double rate2 = CurrencyRateAPI.currencyRate(SourceCCComboBox.getValue());
            double finalRate = rate1/rate2;
            RateLabel.setText("Exchange Rate: " + decimalFormat.format(finalRate));
        });

    }


    public void calculaterRate(){

    }
}
