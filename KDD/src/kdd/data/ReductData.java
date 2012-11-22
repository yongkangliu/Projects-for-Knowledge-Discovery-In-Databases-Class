package kdd.data;

import java.util.List;

//Table 5 in the paper
public class ReductData {
    private static ReductData instance = null;

    private List<String>[] reducts;
    private int[][] reductValues;

    private ReductData() {
        // Nothing here.
    }

    private ReductData(List<String>[] reducts) {
        this.reducts = reducts;

        // 3 means that there are three data: weight, frequency and hit ratio.
        this.reductValues = new int[reducts.length][3];
    }

    public static ReductData initialize(List<String>[] reducts) {
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
        return new String[] { "print out recommendations here." };
    }

    // public static void main(String[] args) {
    // ReductData a = ReductData.initialize(null);
    // a.calculateRecommendation();
    // }
}
