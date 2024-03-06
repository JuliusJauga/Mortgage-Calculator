package com.example.paskolos;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julius Jauga 5 gr.
 * A class for making a table of information about the whole mortgage schedule.
 */
public class PayTable extends Converter {
    private double mortgage;
    private double annual_percentage;
    private boolean linear_schedule;
    private boolean annuital_schedule;
    private int years;
    private int months;
    private int start;
    private int end;

    /**
     * Constructor for PayTable class
     * @param mortgage - Double
     * @param annual_percentage - Double
     * @param linear_schedule - Boolean
     * @param annuital_schedule - Boolean
     * @param years - Integer.
     * @param months - Integer.
     * @param start - Start of postponement in months.
     * @param end - End of postponement in months.
     */
    public PayTable(double mortgage, double annual_percentage, boolean linear_schedule, boolean annuital_schedule, int years, int months, int start, int end) {
        super(mortgage, annual_percentage, linear_schedule, annuital_schedule, years, months, start, end);
    }
    public double getMortgage() {
        return mortgage;
    }

    public void setMortgage(float mortgage) {
        this.mortgage = mortgage;
    }

    public double getAnnual_percentage() {
        return annual_percentage;
    }

    public void setAnnual_percentage(float annual_percentage) {
        this.annual_percentage = annual_percentage;
    }

    public boolean isLinear_schedule() {
        return linear_schedule;
    }

    public void setLinear_schedule(boolean linear_schedule) {
        this.linear_schedule = linear_schedule;
    }

    public boolean isAnnuit_schedule() {
        return annuital_schedule;
    }

    public void setAnnuital_schedule(boolean annuital_schedule) {
        this.annuital_schedule = annuital_schedule;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public int getMonths() {
        return months;
    }
    public void setMonths(int months) {
        this.months = months;
    }

    /**
     * Method to calculate a linear list based on variables inside the PayTable object.
     * @return A list of Pair Objects.
     */
    public List<Pair> generateLinearList() {
        if (super.linear_schedule) {
            int totalMonths = super.years * 12 + super.months;
            int counter = 1;
            double remainingMortgage = super.mortgage;
            double monthlyReduction = super.mortgage / totalMonths;
            double monthlyInterestRate = super.annual_percentage / 12 / 100;
            double monthlyPayment;
            double totalToPay = 0;
            List<Pair> LinearList = new ArrayList<>();
            for (int month = 1; month <= totalMonths; month++) {
                monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
                if (month >= super.start && month < super.end && super.end < totalMonths) {
                    monthlyPayment = 0;
                    counter++;
                }
                else if (super.end == month && month > 1 && super.end < totalMonths) {
                    remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                    monthlyReduction = remainingMortgage / (totalMonths - super.months);
                    monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
                }
                else {
                    remainingMortgage -= monthlyReduction;
                }
                if (remainingMortgage < 0) remainingMortgage = 0;
                totalToPay += monthlyPayment;
            }
            double percentPay = totalToPay;
            monthlyReduction = super.mortgage / totalMonths;
            remainingMortgage = super.mortgage;
            counter = 1;
            double percent;
            for (int month = 1; month <= totalMonths; month++) {
                monthlyPayment = monthlyReduction + monthlyInterestRate * remainingMortgage;
                if (month >= super.start && month < super.end && super.end < totalMonths) {
                    monthlyPayment = 0;
                    counter++;
                    percent = remainingMortgage * monthlyInterestRate;
                }
                else if (super.end == month && month > 1 && super.end < totalMonths) {
                    remainingMortgage += remainingMortgage * counter * monthlyInterestRate;
                    monthlyReduction = remainingMortgage / (totalMonths - super.months);
                    monthlyPayment = monthlyReduction + remainingMortgage * monthlyInterestRate;
                    percent = remainingMortgage * monthlyInterestRate;
                }
                else {
                    percent = remainingMortgage * monthlyInterestRate;
                    remainingMortgage -= monthlyReduction;
                }
                if (remainingMortgage < 0) remainingMortgage = 0;
                totalToPay -= monthlyPayment;
                LinearList.add(new Pair(month,monthlyPayment, percent, Math.abs(totalToPay)));
            }
            return LinearList;
        }
        else return new ArrayList<>();
    }
    /**
     * Method to calculate an annuity list based on variables inside the PayTable object.
     * @return A list of Pair Objects.
     */
    public List<Pair> generateAnnuitList() {
        double monthlyInterestRate = super.annual_percentage / 12 / 100;
        int totalMonths = super.years * 12 + super.months;
        double monthlyPayment = (super.mortgage * monthlyInterestRate) / (1 - Math.pow((1+monthlyInterestRate), -totalMonths));
        if (Double.isNaN(monthlyPayment)) {
            monthlyPayment = (super.mortgage) / totalMonths;
        }
        List<Pair> AnnuitList = new ArrayList<>();
        int counter = 1;
        double remainingBalance = monthlyPayment * totalMonths;
        double principal = super.mortgage;
        double percent;
        for (int month = 1; month <= totalMonths; month++) {
            remainingBalance -= monthlyPayment;
            if (month >= super.start && month < super.end && super.end < totalMonths) {
                monthlyPayment = 0;
                counter++;
            }
            if (super.end == month && month > 1 && super.end < totalMonths) {
                remainingBalance += remainingBalance * counter * monthlyInterestRate;
                monthlyPayment = remainingBalance / (totalMonths - month);
                principal += principal * monthlyInterestRate;
            }
            if (principal <= 0) principal = 0;
            percent = principal * monthlyInterestRate;
            principal -= percent;
            AnnuitList.add(new Pair(month,monthlyPayment, percent, Math.abs(remainingBalance)));
        }
        return AnnuitList;
    }
}
