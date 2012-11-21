package kdd.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Table 4 in the paper
public class DiscernableData {
    private static DiscernableData instance = null;

    private String[] desiredSet;
    private List<String>[][] desiredAlphaReducts;
    private Map<String, Integer> desiredMap = new HashMap<String, Integer>();
    private Map<String, Integer> unDesiredMap = new HashMap<String, Integer>();

    private List<String>[][] dataSet;

    private DiscernableData() {
        // Nothing here.
    }

    private DiscernableData(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        this.dataSet = dataSet;

        this.desiredSet = desiredSet;
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

    public static DiscernableData initialize(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        DiscernableData data = new DiscernableData(desiredSet, undesiredSet, dataSet);
        DiscernableData.instance = data;
        return data;
    }

    public static DiscernableData getInstance() {
        return DiscernableData.instance;
    }

    public List<String> getValue(String desiredName, String unDesiredName) {
        int iDesiredName = this.desiredMap.get(desiredName);
        int iUnDesiredName = this.unDesiredMap.get(unDesiredName);
        return this.dataSet[iUnDesiredName][iDesiredName];
    }

    public ReductData calculateReductData() {
        for (int i = 0; i < this.desiredAlphaReducts.length; i++) {
            List<String>[] inputList = new List[this.dataSet.length];
            for (int j = 0; j < this.dataSet.length; j++) {
                if (this.dataSet[j][i].get(0) == null) {
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
                    reductsList.add(this.desiredAlphaReducts[i][j]);
                }
            }
        }
        List<String>[] reducts = reductsList.toArray(new List[reductsList.size()]);

        return ReductData.initialize(absorptionLaw(reducts));
    }

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
                    listDNF[i] = new ArrayList();
                }
                if (position[j] >= inputList[j].size()) {
                    position[j] = 0;
                }
                inputList[j].get(position[j]);
                listDNF[i].add(inputList[j].get(position[j]));
                position[j]++;
            }
        }
        return listDNF;
    }

    public List<String>[] absorptionLaw(List<String>[] inputList) {
        for (int i = 0; i < inputList.length - 1; i++) {
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

    public boolean isImplicant(List<String> a, List<String> b) {
        for (String strA : a) {
            boolean isFound = false;
            for (String strB : b) {
                if (strA.equals(strB)) {
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
