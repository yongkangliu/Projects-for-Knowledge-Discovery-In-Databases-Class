package kdd.data;

import java.util.ArrayList;
import java.util.List;

//Table 5 in the paper
public class ReductData {
    private static ReductData instance = null;

    // K is the number of reducts.
    private int K = 0;
    // 3 means that there are three data: weight, frequency and hit ratio.

    private List[] reducts = new ArrayList[K];
    private int[][] reductValues = new int[K][3];

    private ReductData() {
        // Nothing here.
    }

    private ReductData(List[] reducts) {
        this.reducts = reducts;
        this.reductValues = new int[reducts.length][3];
    }

    public static ReductData initialize(List[] reducts) {
        ReductData data = new ReductData(reducts);
        ReductData.instance = data;
        return data;
    }

    public static ReductData getInstance() {
        return ReductData.instance;
    }

    public Object[][] getTableData() {
        String[][] data = new String[reducts.length][4];
        for (int i = 0; i < reducts.length; i++) {
            data[i][0] = this.reducts[i].toString();
            for (int j = 0; j < 3; j++) {
                data[i][j + 1] = String.valueOf(this.reductValues[i][j]);
            }
        }
        return data;
    }

    public String[] getTableColumnNames() {
        return new String[] { "Alpha-reduct", "Weight (w)", "Frequency (f)", "Hit ratio (h)" };
    }

    public String[] calculateRecommendation() {
        // TODO
        return new String[] {};
    }

    // public static void main(String[] args) {
    // ReductData a = ReductData.initialize(null);
    // a.calculateRecommendation();
    // }
}
