package kdd.data;

import static org.junit.Assert.*;

import kdd.ui.CSVFile;

import org.junit.Test;

public class OriginalDataTest {

    @Test
    public void test() throws Exception {
        OriginalData file = CSVFile.readCSV("test/dataSet1.csv");
        assertEquals("d1", file.getValue("x1", "d"));
        assertEquals("d1", file.getValue("x2", "d"));
        assertEquals("d2", file.getValue("x3", "d"));
        assertEquals("d2", file.getValue("x4", "d"));
        assertEquals("d1", file.getValue("x5", "d"));
        assertEquals("d2", file.getValue("x6", "d"));
        assertEquals("d2", file.getValue("x7", "d"));
        assertEquals("d1", file.getValue("x8", "d"));
    }

}
