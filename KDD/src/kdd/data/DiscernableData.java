package kdd.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Table 4 in the paper
public class DiscernableData {
    private static DiscernableData instance = null;

    private Map<String, Integer> desiredMap = new HashMap<String, Integer>();
    private Map<String, Integer> unDesiredMap = new HashMap<String, Integer>();

    private List<String>[][] dataSet;

    private DiscernableData() {
        // Nothing here.
    }

    private DiscernableData(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        this.dataSet = dataSet;

        for (int i = 0; i < desiredSet.length; i++) {
            this.desiredMap.put(desiredSet[i], i);
        }
        for (int i = 0; i < undesiredSet.length; i++) {
            this.unDesiredMap.put(undesiredSet[i], i);
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
        // TODO
        return ReductData.initialize(null);
    }

}
