package kdd.data;

import static org.junit.Assert.*;

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

}
