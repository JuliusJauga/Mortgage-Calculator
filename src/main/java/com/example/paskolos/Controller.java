package com.example.paskolos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
/**
 * @author Julius Jauga 5 gr.
 * Controller class of application. Handles the functionality of application.
 */
public class Controller implements Initializable {
    @FXML
    private TextField postPoneEndMonth;
    @FXML
    private TextField postPoneEndYear;
    @FXML
    private TextField postPoneStartMonth;
    @FXML
    private TextField postPoneStartYear;
    @FXML
    private TextField mortgage;
    @FXML
    private TextField annual_percent;
    @FXML
    private TextField years;
    @FXML
    private TextField months;
    @FXML
    private CheckBox linear;
    @FXML
    private CheckBox annuital;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab pay_table_tab;
    @FXML
    private Tab linear_graph_tab;
    @FXML
    private Tab annuit_graph_tab;
    @FXML
    private LineChart<?, ?> annuity_chart;
    @FXML
    private LineChart<?, ?> linear_chart;
    @FXML
    private TableView<Pair> payTable;
    @FXML
    private Slider filterLow;
    @FXML
    private Slider filterHigh;
    @FXML
    private Label lowHigh;
    @FXML
    private Label lowLow;
    @FXML
    private Label highHigh;
    @FXML
    private Label highLow;
    @FXML
    private TextField low;
    @FXML
    private TextField high;
    @FXML
    private Button saveCSV;
    @FXML
    private Tab info_tab;
    @FXML
    private VBox v_box;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    private double mortgageDouble;
    private double annual_percentage;
    private boolean linear_schedule;
    private boolean annuital_schedule;
    private int yearsInt;
    private int monthsInt;
    private int postPoneStartYearInt;
    private int postPoneStartMonthInt;
    private int postPoneEndYearInt;
    private int postPoneEndMonthInt;
    private List<Pair> payment_list;

    /**
     * Initializing method for controller. Starts up the tabs, styles and text formats.
     * @param url necessary parameter.
     * @param resourceBundle necessary parameter.
     */
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getTabs().clear();
        tabPane.getTabs().add(info_tab);
        v_box.setStyle("-fx-background-color: rgba(116,188,215,0.5);");
        tabPane.setStyle("-fx-background-color: rgba(198,255,181,0.5);");
        label1.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label2.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label3.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label4.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label5.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label6.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        label7.setStyle("-fx-font-family: Arial; -fx-font-weight: bold;");
        setNumeric(mortgage);
        setNumeric(months);
        setNumeric(years);
        setNumeric(low);
        setNumeric(high);
        setNumeric(annual_percent);
        setNumeric(postPoneStartMonth);
        setNumeric(postPoneStartYear);
        setNumeric(postPoneEndMonth);
        setNumeric(postPoneEndYear);
    }
    /**
     * Method to set the text field so it takes numbers only.
     * @param field TextField parameter needed to modify the text field.
     */
    private void setNumeric(TextField field) {
        field.setTextFormatter(new TextFormatter<>(new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                if (change.isContentChange()) {

                    if (change.getControlNewText().matches("([0-9]*\\.?[0-9]*)?")) {
                        return change;
                    }
                    return null;
                }
                return change;
            }
        }));
    }

    /**
     * Method for the button to calculate mortgage in the application.
     */
    @FXML
    void onCalculateButton() {
        saveCSV.setVisible(true);
        tabPane.getTabs().clear();
        tabPane.getTabs().add(pay_table_tab);
        setValues();
        filterLow.setMin(1);
        filterLow.setMax(monthsInt + yearsInt * 12);
        filterHigh.setMin(filterLow.getValue());
        filterHigh.setMax(monthsInt + yearsInt * 12);
        filterHigh.setValue(monthsInt + yearsInt * 12);
        lowLow.setText("1");
        lowHigh.setText(Integer.toString((int)filterLow.getMax()));
        highLow.setText(Integer.toString((int)filterLow.getValue()));
        highHigh.setText(Integer.toString((int)filterHigh.getMax()));
        high.setText(String.format("%.0f", filterHigh.getValue()));
        low.setText("1");
        filterLow.setValue(1);
        if (high.getText().isEmpty()) {
            high.setText(Integer.toString((int)filterLow.getMax()));
            filterHigh.setValue(monthsInt + yearsInt * 12);
        }
        if (low.getText().isEmpty()) {
            low.setText("1");
        }
        calculate();
    }

    /**
     * Method to calculate linear or annuity mortgage schedules.
     */
    private void calculate() {
        Converter myConverter = new Converter(mortgageDouble, annual_percentage, linear_schedule, annuital_schedule, yearsInt, monthsInt, postPoneStartYearInt * 12 + postPoneStartMonthInt,
                postPoneEndYearInt * 12 + postPoneEndMonthInt);
        if (linear_schedule) {
            payment_list = new ArrayList<Pair>(myConverter.fillLinearList());
            tabPane.getTabs().add(linear_graph_tab);
        }
        else if (annuital_schedule) {
            payment_list = new ArrayList<Pair>(myConverter.fillAnnuitList());
            tabPane.getTabs().add(annuit_graph_tab);
        }
        fillList(payment_list);
    }

    /**
     * Method to handle choice boxes in FXML.
     */
    @FXML
    private void onAnnuit() {
        annuital.setSelected(true);
        linear.setSelected(false);
    }

    /**
     * Method to handle choice boxes in FXML.
     */
    @FXML
    private void onLinear() {
        annuital.setSelected(false);
        linear.setSelected(true);
    }

    /**
     * Method to pull all the values from user input inside the application.
     */
    private void setValues() {
        linear_schedule = linear.isSelected();
        annuital_schedule = annuital.isSelected();
        try {
            monthsInt = Integer.parseUnsignedInt(months.getText());
        } catch (NumberFormatException e) {
            monthsInt = 0;
        }
        try {
            yearsInt = Integer.parseUnsignedInt(years.getText());
        } catch (NumberFormatException e) {
            yearsInt = 0;
        }
        try {
            annual_percentage = Double.parseDouble(annual_percent.getText());
        } catch (NumberFormatException e) {
            annual_percentage = 0.0;
        }
        try {
            mortgageDouble = Double.parseDouble(mortgage.getText());
        } catch (NumberFormatException e) {
            mortgageDouble = 0.0;
        }
        try {
            mortgageDouble = Double.parseDouble(mortgage.getText());
        } catch (NumberFormatException e) {
            mortgageDouble = 0.0;
        }
        try {
            postPoneStartYearInt = Integer.parseUnsignedInt(postPoneStartYear.getText());
        } catch (NumberFormatException e) {
            postPoneStartYearInt = 0;
        }
        try {
            postPoneStartMonthInt = Integer.parseUnsignedInt(postPoneStartMonth.getText());
        } catch (NumberFormatException e) {
            postPoneStartMonthInt = 0;
        }
        try {
            postPoneEndYearInt = Integer.parseUnsignedInt(postPoneEndYear.getText());
        } catch (NumberFormatException e) {
            postPoneEndYearInt = 0;
        }
        try {
            postPoneEndMonthInt = Integer.parseUnsignedInt(postPoneEndMonth.getText());
        } catch (NumberFormatException e) {
            postPoneEndMonthInt = 0;
        }
    }

    /**
     * Method to fill the pay table with the given list and to draw the graph with the given list. Also handles filtering
     * @param list a list of Pair objects.
     */
    private void fillList(List<Pair> list) {
        payTable.getColumns().clear();
        TableColumn<Pair, String> monthColumn = new TableColumn<Pair, String>("Mėnesis");
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("monthString"));
        TableColumn<Pair, String> paymentColumn = new TableColumn<Pair, String>("Mėnesio įmoka");
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPaymentString"));
        TableColumn<Pair, String> percentColumn = new TableColumn<Pair, String>("Mėn. palūkanos");
        percentColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPaymentPercentString"));
        TableColumn<Pair, String> mortgageColumn = new TableColumn<Pair,String>("Likusi suma");
        mortgageColumn.setCellValueFactory(new PropertyValueFactory<>("remainingMortgageString"));
        payTable.getColumns().add(monthColumn);
        payTable.getColumns().add(paymentColumn);
        payTable.getColumns().add(percentColumn);
        payTable.getColumns().add(mortgageColumn);
        payTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ObservableList<Pair> data = FXCollections.observableArrayList();
        annuity_chart.getData().clear();
        linear_chart.getData().clear();
        annuity_chart.getXAxis().setAutoRanging(true);
        annuity_chart.getYAxis().setAutoRanging(true);
        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < list.size(); i++) {
            if (i + 1 >= (int)filterLow.getValue() && i + 1 <= (int)filterHigh.getValue()) {
                int x = i + 1;
                series.getData().add(new XYChart.Data(x, list.get(i).getMonthlyPayment()));
                data.add(list.get(i));
            }
        }
        annuity_chart.setLegendVisible(false);
        linear_chart.setLegendVisible(false);
        if (annuital_schedule) annuity_chart.getData().add(series);
        else if (linear_schedule) linear_chart.getData().add(series);
        payTable.setItems(data);
    }

    /**
     * Method to handle slider.
     */
    @FXML
    private void onFilterDrag() {
        filterHigh.setMin(filterLow.getValue());
        lowLow.setText("1");
        lowHigh.setText(Integer.toString((int)filterLow.getMax()));
        highLow.setText(Integer.toString((int)filterLow.getValue()));
        highHigh.setText(Integer.toString((int)filterHigh.getMax()));
        high.setText(Integer.toString((int)filterHigh.getValue()));
        low.setText(Integer.toString((int)filterLow.getValue()));
        calculate();
    }

    /**
     * Method to handle slider values if value is entered inside text field.
     */
    @FXML
    private void onKeyTyped() {
        if (!low.getText().isEmpty()) {
            filterLow.setValue(Integer.parseUnsignedInt(low.getText()));
        }
        else {
            filterLow.setValue(1);
            low.setText("1");
        }
        if (!high.getText().isEmpty()) {
            filterHigh.setValue(Integer.parseUnsignedInt(high.getText()));
        }
        else if (!low.getText().isEmpty()) {
            filterHigh.setValue(Integer.parseUnsignedInt(low.getText()));
            high.setText(Integer.toString((int)filterHigh.getValue()));
        }
        else {
            filterHigh.setValue(1);
            high.setText(Integer.toString((int)filterHigh.getValue()));
        }

        if (Integer.parseUnsignedInt(low.getText()) > monthsInt + yearsInt * 12) {
            filterLow.setValue(monthsInt + yearsInt * 12);
            low.setText(Integer.toString(monthsInt + yearsInt * 12));
        }
        if (Integer.parseUnsignedInt(high.getText()) > monthsInt + yearsInt * 12) {
            filterHigh.setValue(monthsInt + yearsInt * 12);
            high.setText(Integer.toString(monthsInt + yearsInt * 12));
        }
        calculate();
        low.selectRange(low.getText().length(), low.getText().length());
        high.selectRange(high.getText().length(), high.getText().length());
    }

    /**
     * Method to save a CSV file based on annuity or linear schedule booleans.
     */
    @FXML
    private void onSave() {
        String[] headers = {"Months", "Monthly payment", "Monthly interest", "Remaining balance"};
        WriterCSV save = new WriterCSV(payment_list);
        if (annuital_schedule) save.write("annuit.csv", headers);
        if (linear_schedule) save.write("linear.csv", headers);
    }
}