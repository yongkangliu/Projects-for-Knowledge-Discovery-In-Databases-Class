/*
 * UNC Charlotte ITCS 6162 Knowledge Discovery in Databases - KDD  Class, Final Project
 * 
 * by Yongkang Liu, 11/27/2012
 */
package kdd.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OriginalData class stores data for the Table 1 in the paper.
 */
public class OriginalData {

    /**
     * The OriginalData object instance.
     */
    private static OriginalData instance = null;

    /**
     * Stores all attribute names in a hashmap.
     */
    private Map<String, Integer> attributesMap = new HashMap<String, Integer>();

    /**
     * Stores all record names in a hashmap.
     */
    private Map<String, Integer> nameMap = new HashMap<String, Integer>();

    /**
     * Stores all values in a two-dimensional array.
     */
    private String[][] dataSet = new String[][] {};

    /**
     * The desired value of decision attribute.
     */
    private String desiredValue = "";

    /**
     * The private constructor in singleton pattern.
     */
    private OriginalData() {
        // Nothing here.
    }

    /**
     * The private constructor in singleton pattern.
     * 
     * @param dataSet
     *            The records values in a two-dimensional array.
     */
    private OriginalData(String[][] dataSet) {
        this.attributesMap.clear();
        this.nameMap.clear();
        this.dataSet = dataSet;

        if (dataSet != null) {
            for (int i = 1; i < dataSet[0].length; i++) {
                this.attributesMap.put(dataSet[0][i], i);
            }
            for (int i = 2; i < dataSet.length; i++) {
                this.nameMap.put(dataSet[i][0], i);
            }
        }
    }

    /**
     * Initialize data.
     * 
     * @param dataSet
     *            The records values in a two-dimensional array.
     * @return Return the OriginalData instance.
     */
    public static OriginalData initialize(String[][] dataSet) {
        OriginalData data = new OriginalData(dataSet);
        OriginalData.instance = data;
        return data;
    }

    /**
     * Get the OriginalData instance.
     * 
     * @return Return the OriginalData instance.
     */
    public static OriginalData getInstance() {
        return OriginalData.instance;
    }

    /**
     * Get all records data.
     * 
     * @return The all records data in a two-dimensional array.
     */
    public Object[][] getTableData() {
        String[][] data = new String[this.dataSet.length - 2][];
        for (int i = 0; i < data.length; i++) {
            data[i] = this.dataSet[i + 2];
        }
        return data;
    }

    /**
     * Get the table column names, which are attribute names.
     * 
     * @return Return the table column names.
     */
    public String[] getTableColumnNames() {
        String[] names = new String[this.dataSet[0].length];
        for (int i = 0; i < names.length; i++) {
            if (this.dataSet[1][i] != null && !"".equals(this.dataSet[1][i])) {
                names[i] = this.dataSet[0][i] + "(" + this.dataSet[1][i] + ")";
            }
        }
        return names;
    }

    /**
     * Get the attribute name and value for a reduct.
     * 
     * @param reduct
     *            The reduct.
     * @return Return the attribute name and value.
     */
    private String getReductPair(String reduct) {
        for (int i = 0; i < this.dataSet.length; i++) {
            for (int j = 0; j < this.dataSet[0].length; j++) {
                if (this.dataSet[i][j].equals(reduct)) {
                    if ("s".equals(this.dataSet[1][j])) {
                        return new String(this.dataSet[0][j] + " = " + reduct);
                    } else {
                        return new String(this.dataSet[0][j] + " to " + reduct);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get attributes names and values for reducts.
     * 
     * @param reduct
     *            The reducts.
     * @return Return the attributes names and values.
     */
    public List<String> getRecommendationRules(List<String> reduct) {
        List<String> rules = new ArrayList<String>();
        for (int i = 0; i < reduct.size(); i++) {
            String reductPair = getReductPair(reduct.get(i));
            if (reductPair != null) {
                rules.add(reductPair);
            }
        }
        return rules;
    }

    /**
     * Check if the reducts can be found in a undesired record and the reduct's attribute is stable.
     * 
     * @param name
     *            The undesired record name.
     * @param reduct
     *            The reducts
     * @return Return true if it is found, otherwise return false.
     */
    public boolean isFoundStableWithReduct(String name, List<String> reduct) {
        int row = this.nameMap.get(name);
        for (int j = 0; j < reduct.size(); j++) {
            for (int i = 1; i < this.dataSet[0].length; i++) {
                if (this.dataSet[row][i].equals(reduct.get(j))) {
                    if ("s".equals(this.dataSet[1][i])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the reducts can be found in a desired record. If the reducts' attribute is stable, ignore it and return
     * true.
     * 
     * @param name
     *            The desired record name.
     * @param reduct
     *            The reducts
     * @return Return true if it is found, otherwise return false.
     */
    public boolean isFoundWithReduct(String name, List<String> reduct) {
        int row = this.nameMap.get(name);
        for (int j = 0; j < reduct.size(); j++) {
            boolean isFound = false;
            for (int i = 1; i < this.dataSet[0].length; i++) {
                if (this.dataSet[row][i].equals(reduct.get(j))) {
                    if ("s".equals(this.dataSet[row][i])) {
                        return true;
                    }
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get one of the record values.
     * 
     * @param name
     *            The record name.
     * @param attribute
     *            The attribute name.
     * @return Return the value.
     */
    public String getValue(String name, String attribute) {
        int iName = this.nameMap.get(name);
        int iAttribute = this.attributesMap.get(attribute);
        String str = this.dataSet[iName][iAttribute].trim();
        if ("".equals(str)) {
            return null;
        } else {
            return str;
        }
    }

    /**
     * Calculate the discernable attributes and create DiscernableData instance.
     * 
     * @return Return the DiscernableData instance.
     */
    public DiscernableData caLculateDiscernableData() {
        List<String> desiredList = new ArrayList<String>();
        List<String> unDesiredList = new ArrayList<String>();

        int length = this.dataSet[0].length;
        for (int i = 2; i < this.dataSet.length; i++) {
            if (this.desiredValue.equals(this.dataSet[i][length - 1])) {
                desiredList.add(this.dataSet[i][0]);
            } else {
                unDesiredList.add(this.dataSet[i][0]);
            }
        }

        List<String>[][] discernableData = new ArrayList[unDesiredList.size()][desiredList.size()];

        for (int i = 0; i < unDesiredList.size(); i++) {
            for (int j = 0; j < desiredList.size(); j++) {
                for (int k = 1; k < this.dataSet[0].length - 1; k++) {
                    String str1 = getValue(desiredList.get(j), this.dataSet[0][k]);
                    String str2 = getValue(unDesiredList.get(i), this.dataSet[0][k]);

                    if (str1 == null) {
                        if (discernableData[i][j] == null) {
                            discernableData[i][j] = new ArrayList<String>();
                            discernableData[i][j].add(str1);
                        }
                    } else if (!str1.equals(str2)) {
                        if (discernableData[i][j] == null || discernableData[i][j].get(0) == null) {
                            discernableData[i][j] = new ArrayList<String>();
                        }
                        discernableData[i][j].add(str1);
                    }
                }
            }
        }

        return DiscernableData.initialize(desiredList.toArray(new String[desiredList.size()]),
                unDesiredList.toArray(new String[unDesiredList.size()]), discernableData);
    }

    /**
     * Get the desired value.
     * 
     * @return Return the desired value of decision attribute.
     */
    public String getDesiredValue() {
        return this.desiredValue;
    }

    /**
     * Set the desired value of decision attribute.
     * 
     * @param desiredValue
     *            The desired value.
     */
    public void setDesiredValue(String desiredValue) {
        this.desiredValue = desiredValue;
    }
}
