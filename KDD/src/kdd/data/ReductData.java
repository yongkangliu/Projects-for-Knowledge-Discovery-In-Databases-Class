package kdd.data;

import java.util.List;

//Table 5 in the paper
public class ReductData {
    public static int WEIGHT = 0;
    public static int FREQUENCY = 1;
    public static int HIT_RATIO = 2;

    private static ReductData instance = null;

    private List<String>[] reducts;
    private double[][] reductValues;

    private ReductData() {
        // Nothing here.
    }

    private ReductData(List<String>[] reducts) {
        this.reducts = reducts;

        // 3 means that there are three data: weight, frequency and hit ratio.
        this.reductValues = new double[reducts.length][3];
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
            data[i][1] = String.format("%.4f", this.reductValues[i][0]);
            data[i][2] = String.format("%.0f", this.reductValues[i][1]);
            data[i][3] = String.format("%.4f", this.reductValues[i][2]);
        }
        return data;
    }

    public String[] getTableColumnNames() {
        return new String[] { "Alpha-reduct", "Weight (w)", "Frequency (f)", "Hit ratio (h)" };
    }

    private void calculateWeight() {
        DiscernableData discernableData = DiscernableData.getInstance();
        String[] desiredSet = discernableData.getDesiredSet();
        String[] undesiredSet = discernableData.getUnDesiredSet();

        OriginalData originalData = OriginalData.getInstance();

        double sum = 0;
        for (int i = 0; i < this.reducts.length; i++) {
            double frequencyInDesiredSet = 0;
            for (int j = 0; j < desiredSet.length; j++) {
                if (originalData.isFoundWithReduct(desiredSet[j], this.reducts[i])) {
                    frequencyInDesiredSet++;
                }
            }
            this.reductValues[i][ReductData.FREQUENCY] = frequencyInDesiredSet;

            double frequencyInUnDesiredSet = 0;
            for (int j = 0; j < undesiredSet.length; j++) {
                if (originalData.isFoundStableWithReduct(undesiredSet[j], this.reducts[i])) {
                    frequencyInUnDesiredSet++;
                }
            }
            this.reductValues[i][ReductData.HIT_RATIO] = (undesiredSet.length - frequencyInUnDesiredSet)
                    / undesiredSet.length;

            sum += this.reductValues[i][ReductData.FREQUENCY] * this.reductValues[i][ReductData.HIT_RATIO];
        }

        for (int i = 0; i < this.reducts.length; i++) {
            this.reductValues[i][ReductData.WEIGHT] = this.reductValues[i][ReductData.FREQUENCY]
                    * this.reductValues[i][ReductData.HIT_RATIO] / sum;
        }
    }

    public String[] getRecommendationColumn() {
        return new String[] { "Recommendations" };
    }

    public String[][] createRecommendation() {
        calculateWeight();

        int row = 0;
        double maxWeight = 0;
        for (int i = 0; i < this.reducts.length; i++) {
            if (this.reductValues[i][0] > maxWeight) {
                maxWeight = this.reductValues[i][0];
                row = i;
            } else if (Math.abs(this.reductValues[i][0] - maxWeight) < 0.0001) {
                if (this.reducts[i].size() < this.reducts[row].size()) {
                    row = i;
                }
            }
        }

        OriginalData originalData = OriginalData.getInstance();
        List<String> rules = originalData.getRecommendationRules(this.reducts[row]);
        String[][] recommendations = new String[1][1];
        recommendations[0][0] = "Change the value of ";
        for (int i = 0; i < rules.size(); i++) {
            if (i == 0) {
                recommendations[0][0] += rules.get(i);
            } else {
                recommendations[0][0] += ", " + rules.get(i);
            }
        }
        recommendations[0][0] += " in order to induce the decision value in X-b to " + originalData.getDesiredValue();
        return recommendations;
    }
}
