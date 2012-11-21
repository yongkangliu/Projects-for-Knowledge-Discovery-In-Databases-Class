package kdd.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Table 1 in the paper
public class OriginalData {

    public static int ATTRIBUTE_STATUS_FLEXIBLE = 0;
    public static int ATTRIBUTE_STATUS_STABLE = 1;
    public static int ATTRIBUTE_STATUS_DECISION = 2;

    private static OriginalData instance = null;

    private Map<String, Integer> attributesMap = new HashMap<String, Integer>();

    private Map<String, Integer> nameMap = new HashMap<String, Integer>();

    private String[][] dataSet = new String[][] {};

    private String desiredValue = "d2";

    private OriginalData() {
        // Nothing here.
    }

    private OriginalData(String[][] dataSet) {
        this.dataSet = dataSet;

        for (int i = 1; i < dataSet[0].length; i++) {
            this.attributesMap.put(dataSet[0][i], i);
        }
        for (int i = 2; i < dataSet.length; i++) {
            this.nameMap.put(dataSet[i][0], i);
        }
    }

    public static OriginalData initialize(String[][] dataSet) {
        OriginalData data = new OriginalData(dataSet);
        OriginalData.instance = data;
        return data;
    }

    public static OriginalData getInstance() {
        return OriginalData.instance;
    }

    public String getValue(String name, String attribute) {
        int iName = this.nameMap.get(name);
        int iAttribute = this.attributesMap.get(attribute);
        return this.dataSet[iName][iAttribute];
    }

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
                    String str2 = getValue(unDesiredList.get(j), this.dataSet[0][k]);
                    if (str1 != str2) {
                        discernableData[i][j].add(str1);
                    }
                }
            }
        }

        // TODO
        return DiscernableData.initialize(desiredList.toArray(new String[desiredList.size()]),
                unDesiredList.toArray(new String[unDesiredList.size()]), discernableData);
    }

    public String getDesiredValue() {
        return desiredValue;
    }

    public void setDesiredValue(String desiredValue) {
        this.desiredValue = desiredValue;
    }
}
