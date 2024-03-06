package com.example.paskolos;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;

/**
 * @author Julius Jauga 5 gr.
 * A class designed to write a list of Pair objects to a CSV file.
 */
public class WriterCSV {
    private List<Pair> data;

    /**
     * Constructor for WriterCSV class.
     * @param other Needs a list of Pair objects.
     */
    public WriterCSV(List<Pair> other) {
        data = new ArrayList<>(other);
    }

    /**
     * Method to write to a CSV file based on given headers and given file path.
     * @param filePath String file path for the CSV file.
     * @param headers String array for headers in the CSV file.
     */
    public void write(String filePath, String[] headers) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(headers);
            for (int i = 0; i < data.size(); i++) {
                String[] row = {
                        Integer.toString(i+1),
                        String.format("%.2f", data.get(i).getMonthlyPayment()),
                        String.format("%.2f", data.get(i).getMonthlyPaymentPercent()),
                        String.format("%.2f", data.get(i).getRemainingMortgage())
                };
                writer.writeNext(row);
            }
        } catch (IOException e) {
            System.out.println("Failed to write");
        }
    }

    public List<Pair> getData() {
        return data;
    }

    public void setData(List<Pair> data) {
        this.data = data;
    }
}
