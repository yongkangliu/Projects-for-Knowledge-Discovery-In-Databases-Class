package kdd.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kdd.data.OriginalData;

public class CSVFile {

    public static OriginalData readCSV(String fileName) throws IOException {

        BufferedReader CSVFile = new BufferedReader(new FileReader(fileName));
        String dataRow = CSVFile.readLine();

        List<String[]> list = new ArrayList<String[]>();

        while (dataRow != null) {
            String[] dataArray = dataRow.split(",");
            list.add(dataArray);
            dataRow = CSVFile.readLine();
        }

        CSVFile.close();
        String[][] result = list.toArray(new String[list.size()][list.get(0).length]);

        return OriginalData.initialize(result);
    }
}