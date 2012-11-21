package kdd.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Table 4 in the paper
public class DiscernableData {
    private static DiscernableData instance = null;

    // P is the number of data set with desired decision attribute
    private int P = 0;
    // Q is the number of data set with undesired decision attribute
    private int Q = 0;

    private String[] desiredSet = new String[P];
    private String[] undesiredSet = new String[Q];
    private List<String>[][] dataSet;

    private DiscernableData() {
        // Nothing here.
    }

    private DiscernableData(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        this.desiredSet = desiredSet;
        this.undesiredSet = undesiredSet;
        this.dataSet = dataSet;
    }

    public static DiscernableData initialize(String[] desiredSet, String[] undesiredSet, List<String>[][] dataSet) {
        DiscernableData data = new DiscernableData(desiredSet, undesiredSet, dataSet);
        DiscernableData.instance = data;
        return data;
    }

    public static DiscernableData getInstance() {
        return DiscernableData.instance;
    }

    public ReductData calculateReductData() {
        // TODO
        return ReductData.initialize(null);
    }
}
