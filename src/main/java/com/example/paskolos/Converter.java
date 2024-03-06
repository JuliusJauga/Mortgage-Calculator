package com.example.paskolos;

import java.util.List;

/**
 * @author Julius Jauga 5 gr.
 * Class for returning necessary lists needed for Controller functionality.
 */
public class Converter extends Controller {
    protected double mortgage;
    protected double annual_percentage;
    protected boolean linear_schedule;
    protected boolean annuital_schedule;
    protected int years;
    protected int months;
    protected int start;
    protected int end;

    /**
     * Constructor for converter object.
     * @param mortgage - Double.
     * @param annual_percentage - Double.
     * @param linear_schedule - Boolean.
     * @param annuital_schedule - Boolean.
     * @param years - Integer
     * @param months - Integer
     * @param start - Start of postponement in months Integer.
     * @param end - End of postponement in months Integer.
     */
    public Converter(double mortgage, double annual_percentage, boolean linear_schedule, boolean annuital_schedule, int years, int months, int start, int end) {
        this.mortgage = mortgage;
        this.annual_percentage = annual_percentage;
        this.linear_schedule = linear_schedule;
        this.annuital_schedule = annuital_schedule;
        this.years = years;
        this.months = months;
        this.start = start;
        this.end = end;
    }

    /**
     * Method to handle Pay table object.
     * @return returns a linear list of Pair Objects containing information about mortgage.
     */
    public List<Pair> fillLinearList() {
        PayTable newTable = new PayTable(this.mortgage, this.annual_percentage, this.linear_schedule, this.annuital_schedule, this.years, this.months, this.start, this.end);
        return newTable.generateLinearList();
    }
    /**
     * Method to handle Pay table object.
     * @return returns an annuity list of Pair Objects containing information about mortgage.
     */
    public List<Pair> fillAnnuitList() {
        PayTable newTable = new PayTable(this.mortgage, this.annual_percentage, this.linear_schedule, this.annuital_schedule, this.years, this.months, this.start, this.end);
        return newTable.generateAnnuitList();
    }

}
