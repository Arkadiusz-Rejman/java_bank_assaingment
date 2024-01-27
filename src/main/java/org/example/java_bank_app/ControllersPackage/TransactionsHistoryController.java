package org.example.java_bank_app.ControllersPackage;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.java_bank_app.AlertPackage.CustomAlert;
import org.example.java_bank_app.CurrencyPackage.CurrencyCode;
import org.example.java_bank_app.UtilsPackage.HoveredThresholdNode;
import org.example.java_bank_app.SQLPackage.mySQL_class;
import org.example.java_bank_app.TransactionsPackage.HistoryTransaction;
import org.example.java_bank_app.TransactionsPackage.TransactionType;
import org.example.java_bank_app.UserClassesPackage.User;
import org.example.java_bank_app.UserClassesPackage.Wallet;
import org.example.java_bank_app.UtilsPackage.MyItem;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class TransactionsHistoryController implements Initializable {

    @FXML
    ComboBox<Wallet> WalletComboBox;
    @FXML
    ComboBox<MyItem> RangeComboBox;
    @FXML
    LineChart<String, BigDecimal> WalletTransactionLineChart;
    @FXML
    TableView<HistoryTransaction> HistoryTableView;

    @FXML
    TableColumn<HistoryTransaction, LocalDateTime> DateTimeColumn;
    @FXML
    TableColumn<HistoryTransaction, TransactionType> TypeColumn;
    @FXML
    TableColumn<HistoryTransaction, String> SenderColumn, ReceiverColumn, WalletColumn, TransactionAmountColumn, BalanceBeforeColumn, BalanceAfterColumn;


    ObservableList<HistoryTransaction> allUserTransactions;
    HashMap<Integer, ObservableList<HistoryTransaction>> walletIDTransactions;
    XYChart.Series<String, BigDecimal> currentSeries;

    User user;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            //INICJALIZACJA
            allUserTransactions = mySQL_class.getUserTransactions(user);
            Collections.reverse(allUserTransactions);
            walletIDTransactions = new HashMap<>();

            for(HistoryTransaction historyTransaction : allUserTransactions){
                int ID = historyTransaction.getWallet().getId();
                if(walletIDTransactions.containsKey(ID)) walletIDTransactions.get(ID).add(historyTransaction);
                else walletIDTransactions.put(ID, FXCollections.observableArrayList(historyTransaction));
            }



            //TABLE VIEW AREA
            DateTimeColumn.setCellValueFactory(cellData -> cellData.getValue().getTransactionDateTimeProperty());
            TypeColumn.setCellValueFactory(cellData -> cellData.getValue().getTransactionTypeProperty());
            BalanceBeforeColumn.setCellValueFactory(cellData -> {
                BigDecimal amount = cellData.getValue().getBalanceBefore();
                CurrencyCode currencyCode = cellData.getValue().getWallet().getCurrency().getCurrencyCode();
                String amountCurrency = amount + " " + currencyCode;
                return new SimpleStringProperty(amountCurrency);
            });
            TransactionAmountColumn.setCellValueFactory(cellData -> {
                BigDecimal amount = cellData.getValue().getTransactionAmountProperty().get();
                CurrencyCode currencyCode = cellData.getValue().getWallet().getCurrency().getCurrencyCode();
                String amountCurrency = amount + " " + currencyCode;
                return new SimpleStringProperty(amountCurrency);
            });
            BalanceAfterColumn.setCellValueFactory(cellData -> {
                BigDecimal amount = cellData.getValue().getBalanceAfter();
                CurrencyCode currencyCode = cellData.getValue().getWallet().getCurrency().getCurrencyCode();
                String amountCurrency = amount + " " + currencyCode;
                return new SimpleStringProperty(amountCurrency);
            });
            SenderColumn.setCellValueFactory(cellData -> cellData.getValue().getSenderProperty());
            ReceiverColumn.setCellValueFactory(cellData -> cellData.getValue().getReceiverProperty());
            WalletColumn.setCellValueFactory(cellData -> cellData.getValue().getWalletProperty());

            HistoryTableView.setItems(allUserTransactions);


            //COMBO BOX AREA
            WalletComboBox.setItems(user.getWallets());
            WalletComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
                WalletTransactionLineChart.getData().clear();
                if(newValue != null){
                    if(walletIDTransactions.containsKey(newValue.getId())){
                        RangeComboBox.setDisable(false);
                        currentSeries = createSeriesArray(newValue, RangeComboBox.getValue().getItemValue());
                        addSymbolsAction(currentSeries);
                        WalletTransactionLineChart.getData().add(currentSeries);
                    }else {
                        CustomAlert.showInfoAlert("Wallet has no history");
                        RangeComboBox.setDisable(true);
                    }
                }
            });

            WalletComboBox.getSelectionModel().selectFirst();

        });

        //COMBO BOX
        RangeComboBox.setItems(FXCollections.observableArrayList(
                new MyItem(1, "Today"),
                new MyItem(3, "3 Days"),
                new MyItem(7, "7 Days"),
                new MyItem(14, "14 Days"),
                new MyItem(30, "30 Days")
        ));

        RangeComboBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            WalletTransactionLineChart.getData().clear();
            if(newValue != null && WalletComboBox.getValue() != null){
                currentSeries = createSeriesArray(WalletComboBox.getValue(), newValue.getItemValue());
                addSymbolsAction(currentSeries);
                WalletTransactionLineChart.getData().add(currentSeries);
            }
        });

        RangeComboBox.getSelectionModel().selectFirst();

        //HISTORY TABLE VIEW
        HistoryTableView.setRowFactory(new Callback<>() {
            @Override
            public TableRow<HistoryTransaction> call(TableView<HistoryTransaction> tableView) {
                return new TableRow<>() {
                    @Override
                    protected void updateItem(HistoryTransaction item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty && item != null) {
                            if (item.getTransactionType() == TransactionType.RECEIVE) {
                                setStyle("-fx-background-color: rgba(0, 255, 0, 0.1);");
                            } else if (item.getTransactionType() == TransactionType.SEND) {
                                setStyle("-fx-background-color: rgba(255, 0, 0, 0.1);");
                            }
                        } else {
                            setStyle("");
                        }
                    }
                };
            }
        });

        //CHART AREA
        WalletTransactionLineChart.setAnimated(false);


    }

    @FXML
    public void passUser(User user) { this.user = user; }

    public void refresh() {
        allUserTransactions.setAll(mySQL_class.getUserTransactions(user));
        Collections.reverse(allUserTransactions);
        walletIDTransactions.clear();

        for(HistoryTransaction historyTransaction : allUserTransactions){
            int ID = historyTransaction.getWallet().getId();
            if(walletIDTransactions.containsKey(ID)) walletIDTransactions.get(ID).add(historyTransaction);
            else walletIDTransactions.put(ID, FXCollections.observableArrayList(historyTransaction));
        }

        HistoryTableView.refresh();
        WalletTransactionLineChart.getData().clear();
        RangeComboBox.setDisable(true);
        WalletComboBox.getSelectionModel().clearSelection();

    }

    //priv methods

    private XYChart.Series<String, BigDecimal> createSeriesArray(Wallet wallet, int range){

        LocalDateTime targetDateTime = LocalDateTime.now().minusDays(range);

        XYChart.Series<String, BigDecimal> series = new XYChart.Series<>();
        series.setName(wallet.getName());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm:ss");

        for(HistoryTransaction historyTransaction : walletIDTransactions.get(wallet.getId())){
            if(historyTransaction.getTransactionDateTime().isAfter(targetDateTime)){
                String formattedDate = historyTransaction.getTransactionDateTime().format(dateFormatter);
                series.getData().add(new XYChart.Data<>(formattedDate, historyTransaction.getBalanceAfter()));
            }
        }

        return series;
    }

    private void addSymbolsAction(XYChart.Series<String, BigDecimal> series){

        DecimalFormat decimalFormat = new DecimalFormat("#. ##");

            for(XYChart.Data<String, BigDecimal> data : series.getData()){
                String formattedDouble = decimalFormat.format(data.getYValue().doubleValue());
                String labelText = formattedDouble + " " + WalletComboBox.getValue().getCurrency().getCurrencyCode();
                data.setNode(new HoveredThresholdNode(labelText, 0));
            }

    }

}
