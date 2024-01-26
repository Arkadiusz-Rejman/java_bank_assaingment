package org.example.java_bank_app.ControllersPackage;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.CurrencyPackage.Currency;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.CurrencyPackage.CurrencyRateAPI;
import org.example.java_bank_app.HoveredThreshholdNodePackage.HoveredThresholdNode;

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
    private int sliderDayValue;

    private HashMap<CurrencyCode, ArrayList<Currency>> currenciesDaysRangeConst;
    private HashMap<CurrencyCode, ArrayList<Currency>> currenciesDaysRange;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        finalRate = 1;
        sliderDayValue = (int) DaySlider.getValue();

        currenciesDaysRangeConst = CurrencyRateAPI.getCurrenciesDayRange(30); // tego nie zmieniamy!
        currenciesDaysRange = computeCurrenciesDaysRange(sliderDayValue);


        //Line Chart & Axis Area

        RateAxis.setLabel("Exchange Rate");
        DateAxis.setLabel("Days");
        ExchangeRatesLineChart.setAnimated(false);




        ObservableList<CurrencyCode> currencyCodes = FXCollections.observableArrayList(CurrencyCode.values());

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

        CCListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        CCListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super CurrencyCode>) change -> {
            List<XYChart.Series<String, Double>> seriesToAdd = new ArrayList<>();
            List<XYChart.Series<String, Double>> seriesToRemove = new ArrayList<>();

            while(change.next()){
                if(change.wasRemoved()){
                    seriesToRemove = ExchangeRatesLineChart.getData().stream()
                            .filter(series -> createSeriesArray(change.getRemoved()).stream()
                                    .anyMatch(series1 -> series.getName().equals(series1.getName()))).toList();
                }

                if(change.wasAdded()) seriesToAdd = createSeriesArray(change.getAddedSubList());

            }
            ExchangeRatesLineChart.getData().removeAll(seriesToRemove);
            addSymbolsAction(seriesToAdd);
            ExchangeRatesLineChart.getData().addAll(seriesToAdd);
        });

        CCListView.getSelectionModel().select(0);

        //Slider Area
        DaySlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            sliderDayValue = newValue.intValue();
            DayLabel.setText(sliderDayValue + " Days");
        });

        DaySlider.setOnMouseReleased(mouseEvent -> {
            currenciesDaysRange.putAll(computeCurrenciesDaysRange(sliderDayValue));
            List<XYChart.Series<String, Double>> listToUpdate = createSeriesArray(CCListView.getSelectionModel().getSelectedItems());
            updateSymbolsAction(listToUpdate);
            ExchangeRatesLineChart.getData().setAll(listToUpdate);
        });

        //Label Area
        RateLabel.setAlignment(Pos.CENTER);


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




    private List<XYChart.Series<String, Double>> createSeriesArray(List<? extends CurrencyCode> currencyCodes){
        List<XYChart.Series<String, Double>> seriesArray = new ArrayList<>();
        for(CurrencyCode currencyCode : currencyCodes){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(currencyCode.toString());

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");

            for (Currency currency : currenciesDaysRange.get(currencyCode)) {
                String formattedDate = currency.getUpdateDate().format(dateFormatter);
                series.getData().add(new XYChart.Data<>(formattedDate, currency.getCurrencyRate()));
            }
            seriesArray.add(series);

        }

        return seriesArray;
    }

    private HashMap<CurrencyCode, ArrayList<Currency>> computeCurrenciesDaysRange(int range){

        LocalDate targetDate = LocalDate.now().minusDays(range);
        HashMap<CurrencyCode, ArrayList<Currency>> outputCurrenciesDaysRange = new HashMap<>();

        for (Map.Entry<CurrencyCode, ArrayList<Currency>> entry : currenciesDaysRangeConst.entrySet()) {
            ArrayList<Currency> currencyList = new ArrayList<>(entry.getValue());
            currencyList.removeIf(currency -> currency.getUpdateDate().isBefore(targetDate));
            outputCurrenciesDaysRange.put(entry.getKey(), currencyList);
        }

        return outputCurrenciesDaysRange;
    }


    private void addSymbolsAction(List<XYChart.Series<String, Double>> listSeries){
        int chartSize = CCListView.getSelectionModel().getSelectedItems().size();

        for(XYChart.Series<String, Double> series : listSeries){
            for(XYChart.Data<String, Double> data : series.getData()){
                data.setNode(new HoveredThresholdNode(data.getYValue(), chartSize - 1));
            }
        }
    }

    private void updateSymbolsAction(List<XYChart.Series<String, Double>> listSeries){
        int startColor = 0;

        for(XYChart.Series<String, Double> series : listSeries){
            for(XYChart.Data<String, Double> data : series.getData()){
                data.setNode(new HoveredThresholdNode(data.getYValue(), startColor));
            }
            startColor++;
        }
    }

}
