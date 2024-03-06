package com.example.paskolos;

/**
 * @author Julius Jauga 5 gr.
 * A class needed for Pair Objects. Blueprint for holding all the values needed for mortgage information.
 */
public class Pair {
    private double monthlyPayment;
    private double remainingMortgage;
    private int month;
    private double monthlyPaymentPercent;
    private String remainingMortgageString;
    private String monthlyPaymentString;
    private String monthString;
    private String monthlyPaymentPercentString;

    /**
     * Constructor for Pair class.
     * @param first - Current month Integer.
     * @param second - Monthly payment Double.
     * @param third - Monthly interest Double.
     * @param fourth - Remaining mortgage Double.
     */
    public Pair(int first, double second, double third, double fourth) {
        this.month= first;
        this.monthlyPayment = second;
        this.monthlyPaymentPercent = third;
        this.remainingMortgage = fourth;
        monthString = String.format("%d", first);
        monthlyPaymentString = String.format("%.2f €", second);
        monthlyPaymentPercentString = String.format("%.2f €", third);
        remainingMortgageString = String.format("%.2f €", fourth);
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public double getRemainingMortgage() {
        return remainingMortgage;
    }

    public void setRemainingMortgage(double remainingMortgage) {
        this.remainingMortgage = remainingMortgage;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getRemainingMortgageString() {
        return remainingMortgageString;
    }

    public void setRemainingMortgageString(String remainingMortgageString) {
        this.remainingMortgageString = remainingMortgageString;
    }

    public String getMonthlyPaymentString() {
        return monthlyPaymentString;
    }

    public void setMonthlyPaymentString(String monthlyPaymentString) {
        this.monthlyPaymentString = monthlyPaymentString;
    }

    public String getMonthString() {
        return monthString;
    }

    public void setMonthString(String monthString) {
        this.monthString = monthString;
    }

    public double getMonthlyPaymentPercent() {
        return monthlyPaymentPercent;
    }

    public void setMonthlyPaymentPercent(double monthlyPaymentPercent) {
        this.monthlyPaymentPercent = monthlyPaymentPercent;
    }

    public String getMonthlyPaymentPercentString() {
        return monthlyPaymentPercentString;
    }

    public void setMonthlyPaymentPercentString(String monthlyPaymentPercentString) {
        this.monthlyPaymentPercentString = monthlyPaymentPercentString;
    }
}
