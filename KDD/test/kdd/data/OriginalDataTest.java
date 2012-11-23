package kdd.data;

import static org.junit.Assert.*;

import java.util.Arrays;

import kdd.ui.CSVFile;

import org.junit.Test;

public class OriginalDataTest {

    @Test
    public void test1() throws Exception {
        OriginalData originalData = CSVFile.readCSV("test/dataSet1.csv");
        assertEquals("d2", originalData.getValue("x1", "D"));
        assertEquals("d2", originalData.getValue("x2", "D"));
        assertEquals("d2", originalData.getValue("x3", "D"));
        assertEquals("d2", originalData.getValue("x4", "D"));
        assertEquals("d1", originalData.getValue("x5", "D"));
        assertEquals("d1", originalData.getValue("x6", "D"));
        assertEquals("d1", originalData.getValue("x7", "D"));
        assertEquals("d1", originalData.getValue("x8", "D"));

        DiscernableData discernableData = originalData.caLculateDiscernableData();

        assertEquals("b2", discernableData.getValue("x1", "x5").get(0));
        assertEquals("b2", discernableData.getValue("x1", "x6").get(0));
        assertEquals("c1", discernableData.getValue("x1", "x7").get(0));
        assertEquals("e1", discernableData.getValue("x1", "x7").get(1));
        assertEquals("b2", discernableData.getValue("x1", "x8").get(0));
        assertEquals("c1", discernableData.getValue("x1", "x8").get(1));
        assertEquals("e1", discernableData.getValue("x1", "x8").get(2));

        assertEquals(null, discernableData.getValue("x3", "x5").get(0));
        assertEquals(null, discernableData.getValue("x3", "x6").get(0));
        assertEquals("b1", discernableData.getValue("x3", "x7").get(0));
        assertEquals("c1", discernableData.getValue("x3", "x7").get(1));
        assertEquals("c1", discernableData.getValue("x3", "x8").get(0));
    }

    @Test
    public void test2() throws Exception {
        OriginalData originalData = CSVFile.readCSV("test/dataSet1.csv");
        DiscernableData discernableData = originalData.caLculateDiscernableData();

        ReductData reductData = discernableData.calculateReductData();
        reductData.createRecommendation();

        Object[][] object = reductData.getTableData();
        assertEquals("0.29", object[0][1]);
        assertEquals("1", object[0][2]);
        assertEquals("1.00", object[0][3]);
        assertEquals("0.14", object[1][1]);
        assertEquals("1", object[1][2]);
        assertEquals("0.50", object[1][3]);
        assertEquals("0.57", object[2][1]);
        assertEquals("2", object[2][2]);
        assertEquals("1.00", object[2][3]);
    }
}