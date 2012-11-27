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

import kdd.ui.KDDGUI;

/**
 * DiscernableData class stores data for the Table 4 in the paper.
 */
public class DiscernableData {

    /**
     * The DiscernableData object instance.
     */
    private static DiscernableData instance = null;

    /**
     * The desired record set.
     */
    private String[] desiredSet;

    /**
     * The undesired record set.
     */
    private String[] undesiredSet;

    /**
     * The desired reducts set.
     */
    private List<String>[][] desiredAlphaReducts;

    /**
     * The desired record set in a hashmap.
     */
    private Map<String, Integer> desiredMap = new HashMap<String, Integer>();

    /**
     * The undesired record set in a hashmap.
     */
    private Map<String, Integer> unDesiredMap = new HashMap<String, Integer>();

    /**
     * All discernable attribute values in a two-dimension array.
     */
    private List<String>[][] dataSet;

    /**
     * The private constructor in singleton pattern.
     */
    private DiscernableData() {
        // Nothing here.
    }

    /**
     * The private constructor in singleton pattern.
     * 
     * @param desiredSet
     *            The desired record set.
     * @param undesiredSet
     *            The undesired record set.
     * @param dataSet
     *            All discernable attribute values in a two-dimension array.
     */
    private DiscernableData(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        this.desiredMap.clear();
        this.unDesiredMap.clear();

        this.dataSet = dataSet;

        this.desiredSet = desiredSet;
        this.undesiredSet = undesiredSet;
        if (desiredSet != null) {
            this.desiredAlphaReducts = new List[desiredSet.length][];
        }

        if (desiredSet != null) {
            for (int i = 0; i < desiredSet.length; i++) {
                this.desiredMap.put(desiredSet[i], i);
            }
        }
        if (undesiredSet != null) {
            for (int i = 0; i < undesiredSet.length; i++) {
                this.unDesiredMap.put(undesiredSet[i], i);
            }
        }
    }

    /**
     * Initialize data.
     * 
     * @param desiredSet
     *            The desired record set.
     * @param undesiredSet
     *            The undesired record set.
     * @param dataSet
     *            All discernable attribute values in a two-dimension array.
     * 
     * @return Return the DiscernableData instance.
     */
    public static DiscernableData initialize(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        DiscernableData data = new DiscernableData(desiredSet, undesiredSet, dataSet);
        DiscernableData.instance = data;
        return data;
    }

    /**
     * Get the DiscernableData instance.
     * 
     * @return Return the DiscernableData instance.
     */
    public static DiscernableData getInstance() {
        return DiscernableData.instance;
    }

    /**
     * Get data for JTable display
     * 
     * @return The object array for JTable.
     */
    public Object[][] getTableData() {
        String[][] tableData = new String[this.undesiredSet.length][this.desiredSet.length + 1];
        for (int i = 0; i < this.undesiredSet.length; i++) {
            tableData[i][0] = this.undesiredSet[i];
            for (int j = 0; j < this.desiredSet.length; j++) {

                String str = "";

                if (this.dataSet[i][j] != null) {
                    for (int k = 0; k < this.dataSet[i][j].size(); k++) {
                        // if (this.dataSet[i][j].get(k) != null) {
                        if (k == 0) {
                            str += this.dataSet[i][j].get(k);
                        } else {
                            str += " + " + this.dataSet[i][j].get(k);
                        }
                        // }
                    }
                }
                tableData[i][j + 1] = str;
            }
        }

        return tableData;
    }

    /**
     * Get the column names for JTable display.
     * 
     * @return Return the column names for JTable.
     */
    public String[] getTableColumnNames() {
        String[] names = new String[this.desiredSet.length + 1];
        for (int i = 1; i < names.length; i++) {
            names[i] = this.desiredSet[i - 1];
        }
        return names;
    }

    /**
     * Get the specified discernable.
     * 
     * @param desiredName
     *            The desired record name.
     * @param unDesiredName
     *            The undesired record name.
     * @return Return the discernable value.
     */
    public List<String> getValue(String desiredName, String unDesiredName) {
        int iDesiredName = this.desiredMap.get(desiredName);
        int iUnDesiredName = this.unDesiredMap.get(unDesiredName);
        return this.dataSet[iUnDesiredName][iDesiredName];
    }

    /**
     * Return the desired set.
     * 
     * @return Return the desired set.
     */
    public String[] getDesiredSet() {
        return this.desiredSet;
    }

    /**
     * Return the undesired set.
     * 
     * @return Return the undesired set.
     */
    public String[] getUnDesiredSet() {
        return this.undesiredSet;
    }

    /**
     * Calculate reducts and create the ReductData instance.
     * 
     * @return Return the ReductData instance.
     */
    public ReductData calculateReductData() {
        for (int i = 0; i < this.desiredAlphaReducts.length; i++) {
            List<String>[] inputList = new ArrayList[this.dataSet.length];
            for (int j = 0; j < this.dataSet.length; j++) {
                if (this.dataSet[j][i] == null || this.dataSet[j][i].get(0) == null) {
                    this.desiredAlphaReducts[i] = null;
                    break;
                } else {
                    inputList[j] = this.dataSet[j][i];
                }
            }
            this.desiredAlphaReducts[i] = convertCNF2DNF(absorptionLaw(inputList));
        }

        List<List<String>> reductsList = new ArrayList<List<String>>();
        for (int i = 0; i < this.desiredAlphaReducts.length; i++) {
            if (this.desiredAlphaReducts[i] != null) {
                for (int j = 0; j < this.desiredAlphaReducts[i].length; j++) {
                    if (this.desiredAlphaReducts[i][j] != null) {
                        reductsList.add(this.desiredAlphaReducts[i][j]);
                    }
                }
            }
        }
        List<String>[] reducts = reductsList.toArray(new List[reductsList.size()]);

        return ReductData.initialize(absorptionLaw(reducts));
    }

    /**
     * Convert CNF objects to DNF objects.
     * 
     * @param inputList
     *            The CNF objects.
     * @return Return the DNF objects.
     */
    public List<String>[] convertCNF2DNF(List<String>[] inputList) {
        int numOfDNF = 1;
        for (int i = 0; i < inputList.length; i++) {
            numOfDNF *= inputList[i].size();
        }
        List<String>[] listDNF = new ArrayList[numOfDNF];

        int[] position = new int[inputList.length];
        for (int i = 0; i < listDNF.length; i++) {
            for (int j = 0; j < inputList.length; j++) {
                if (listDNF[i] == null) {
                    listDNF[i] = new ArrayList<String>();
                }
                if (position[j] >= inputList[j].size()) {
                    position[j] = 0;
                }
                inputList[j].get(position[j]);
                if (!hasString(listDNF[i], inputList[j].get(position[j]))) {
                    listDNF[i].add(inputList[j].get(position[j]));
                }
                position[j]++;
            }
        }
        return listDNF;
    }

    /**
     * Check if a list includes a string.
     * 
     * @param list
     *            The list.
     * @param str
     *            The string.
     * @return Return true if the list includes the string, otherwise return false.
     */
    private boolean hasString(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The absorption laws. Remove all sub string.
     * 
     * @param inputList
     *            The list which includes reducts.
     * @return Return the new reducts after removed all sub string.
     */
    public List<String>[] absorptionLaw(List<String>[] inputList) {
        int total = inputList.length - 2;
        for (int i = 0; i < inputList.length - 1; i++) {
            KDDGUI.showProgress(100 * i / total);
            for (int j = i + 1; j < inputList.length; j++) {
                if (inputList[i] != null && inputList[j] != null) {
                    if (inputList[i].size() <= inputList[j].size()) {
                        if (isImplicant(inputList[i], inputList[j])) {
                            inputList[j] = null;
                        }
                    } else {
                        if (isImplicant(inputList[j], inputList[i])) {
                            inputList[i] = null;
                        }
                    }
                }
            }
        }

        List<List<String>> result = new ArrayList<List<String>>();
        for (List<String> list : inputList) {
            if (list != null) {
                result.add(list);
            }
        }
        return result.toArray(new List[result.size()]);
    }

    /**
     * Check if a list objects implicants another list objects.
     * 
     * @param a
     *            The list objects.
     * @param b
     *            Another list objects.
     * @return Return true if list a implicants list b, otherwise return false.
     */
    public boolean isImplicant(List<String> a, List<String> b) {
        for (String strA : a) {
            boolean isFound = false;
            for (String strB : b) {
                if (strA != null && strA.equals(strB)) {
                    isFound = true;
                }
            }
            if (!isFound) {
                return false;
            }
        }
        return true;
    }
}
