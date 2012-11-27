/*
 * UNC Charlotte ITCS 6162 Knowledge Discovery in Databases - KDD  Class, Final Project
 * 
 * by Yongkang Liu, 11/27/2012
 */
package kdd.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import kdd.ui.CSVFile;

import org.junit.Test;

public class DiscernableDataTest {

    @Test
    public void testIsImplicant1() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("c1");
        a.add("e1");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");
        b.add("b2");

        assertTrue(data.isImplicant(a, b));
    }

    @Test
    public void testIsImplicant2() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("c1");
        a.add("b1");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");
        b.add("b2");

        assertFalse(data.isImplicant(a, b));
    }

    @Test
    public void testIsImplicant3() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("c1");
        a.add("e1");
        a.add("b2");
        a.add("e2");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");
        b.add("b2");

        assertFalse(data.isImplicant(a, b));
    }

    @Test
    public void testIsImplicant4() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("c1");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");
        b.add("b2");

        assertTrue(data.isImplicant(a, b));
    }

    @Test
    public void testIsImplicant5() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("c1");
        List<String> b = new ArrayList<String>();
        b.add("c1");

        assertTrue(data.isImplicant(a, b));
    }

    @Test
    public void testAbsorptionLaw1() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b2");
        List<String> b = new ArrayList<String>();
        b.add("b2");
        List<String> c = new ArrayList<String>();
        c.add("c1");
        c.add("e1");
        List<String> d = new ArrayList<String>();
        d.add("b2");
        d.add("c1");
        d.add("e1");

        List<String>[] result = data.absorptionLaw(new List[] { a, b, c, d });
        

        assertEquals(2, result.length);
        assertEquals(1, result[0].size());
        assertEquals("b2", result[0].get(0));
        assertEquals(2, result[1].size());
        assertEquals("c1", result[1].get(0));
        assertEquals("e1", result[1].get(1));
    }

    @Test
    public void testAbsorptionLaw2() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b1");
        List<String> b = new ArrayList<String>();
        b.add("b2");
        List<String> c = new ArrayList<String>();
        c.add("c1");
        c.add("e1");
        List<String> d = new ArrayList<String>();
        d.add("b2");
        d.add("c1");
        d.add("e1");

        List<String>[] result = data.absorptionLaw(new List[] { a, b, c, d });

        assertEquals(3, result.length);
        assertEquals(1, result[0].size());
        assertEquals("b1", result[0].get(0));
        assertEquals(1, result[1].size());
        assertEquals("b2", result[1].get(0));
        assertEquals(2, result[2].size());
        assertEquals("c1", result[2].get(0));
        assertEquals("e1", result[2].get(1));
    }

    @Test
    public void testAbsorptionLaw3() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b1");
        a.add("b2");
        List<String> b = new ArrayList<String>();
        b.add("b2");
        List<String> c = new ArrayList<String>();
        c.add("c1");
        c.add("e1");
        List<String> d = new ArrayList<String>();
        d.add("b2");
        d.add("c1");
        d.add("e1");

        List<String>[] result = data.absorptionLaw(new List[] { a, b, c, d });

        assertEquals(2, result.length);
        assertEquals(1, result[0].size());
        assertEquals("b2", result[0].get(0));
        assertEquals(2, result[1].size());
        assertEquals("c1", result[1].get(0));
        assertEquals("e1", result[1].get(1));
    }

    @Test
    public void testConvertCNF2DNF1() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b2");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");

        List<String>[] result = data.convertCNF2DNF(new List[] { a, b });
        assertEquals(2, result.length);
        assertEquals(2, result[0].size());
        assertEquals("b2", result[0].get(0));
        assertEquals("c1", result[0].get(1));
        assertEquals(2, result[1].size());
        assertEquals("b2", result[1].get(0));
        assertEquals("e1", result[1].get(1));
    }

    @Test
    public void testConvertCNF2DNF2() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b1");
        a.add("b2");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");

        List<String>[] result = data.convertCNF2DNF(new List[] { a, b });
        assertEquals(4, result.length);
        assertEquals(2, result[0].size());
        assertEquals("b1", result[0].get(0));
        assertEquals("c1", result[0].get(1));
        assertEquals(2, result[1].size());
        assertEquals("b2", result[1].get(0));
        assertEquals("e1", result[1].get(1));

        assertEquals(2, result[2].size());
        assertEquals("b1", result[2].get(0));
        assertEquals("c1", result[2].get(1));
        assertEquals(2, result[3].size());
        assertEquals("b2", result[3].get(0));
        assertEquals("e1", result[3].get(1));
    }

    @Test
    public void testConvertCNF2DNF3() {
        DiscernableData data = DiscernableData.initialize(null, null, null);
        List<String> a = new ArrayList<String>();
        a.add("b1");
        a.add("b2");
        List<String> b = new ArrayList<String>();
        b.add("c1");
        b.add("e1");
        b.add("f1");

        List<String>[] result = data.convertCNF2DNF(new List[] { a, b });
        assertEquals(6, result.length);
        assertEquals(2, result[0].size());
        assertEquals("b1", result[0].get(0));
        assertEquals("c1", result[0].get(1));
        assertEquals(2, result[1].size());
        assertEquals("b2", result[1].get(0));
        assertEquals("e1", result[1].get(1));
        assertEquals(2, result[2].size());
        assertEquals("b1", result[2].get(0));
        assertEquals("f1", result[2].get(1));

        assertEquals(2, result[3].size());
        assertEquals("b2", result[3].get(0));
        assertEquals("c1", result[3].get(1));
        assertEquals(2, result[4].size());
        assertEquals("b1", result[4].get(0));
        assertEquals("e1", result[4].get(1));
        assertEquals(2, result[5].size());
        assertEquals("b2", result[5].get(0));
        assertEquals("f1", result[5].get(1));
    }

    @Test
    public void testCalculateReductData1() throws Exception {
        OriginalData originalData = CSVFile.readCSV("test/dataSet1.csv");
        originalData.setDesiredValue("d2");
        DiscernableData discernableData = originalData.caLculateDiscernableData();

        ReductData result = discernableData.calculateReductData();
        result.createRecommendation();
        Object[][] objects = result.getTableData();

        assertEquals(3, objects.length);
    }
}
