/*
 * UNC Charlotte ITCS 6162 Knowledge Discovery in Databases - KDD  Class, Final Project
 * 
 * by Yongkang Liu, 11/27/2012
 */
package kdd.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kdd.data.OriginalData;

/**
 * Read CSV file
 */
public class CSVFile {

    /**
     * Read CSV file and create OriginalData instance.
     * 
     * @param fileName
     *            The CSV file name.
     * @return Return the OriginalData instance.
     * @throws IOException
     *             throw exception if the file isn't found.
     */
    public static OriginalData readCSV(String fileName) throws IOException {

        BufferedReader CSVFile = new BufferedReader(new FileReader(fileName));
        String dataRow = CSVFile.readLine();

        List<String[]> list = new ArrayList<String[]>();

        while (dataRow != null) {
            String[] dataArray = dataRow.split(",");
            if (dataArray.length > 1) {
                for (int i = 0; i < dataArray.length; i++) {
                    dataArray[i] = dataArray[i].trim();
                }
                list.add(dataArray);
            }
            dataRow = CSVFile.readLine();
        }

        CSVFile.close();
        String[][] result = list.toArray(new String[list.size()][list.get(0).length]);

        // for (int i = 2; i < result.length; i++) {
        // for (int j = 1; j < result[0].length; j++) {
        // if (!"".equals(result[i][j])) {
        // result[i][j] = result[0][j] + ":" + result[i][j];
        // }
        // }
        // }

        return OriginalData.initialize(result);
    }
}