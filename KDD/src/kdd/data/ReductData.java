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
    private int[][] reductValues = new int[3][K];

    private ReductData() {
        // Nothing here.
    }

    private ReductData(List[] reducts) {
        this.reducts = reducts;
        this.reductValues = reductValues;
    }

    public static ReductData initialize(List[] reducts) {
        ReductData data = new ReductData(reducts);
        ReductData.instance = data;
        return data;
    }

    public static ReductData getInstance() {
        return ReductData.instance;
    }

    public String[] calculateRecommendation() {
        // TODO
        return new String[] {};
    }

//    public static void main(String[] args) {
//        ReductData a = ReductData.initialize(null);
//        a.calculateRecommendation();
//    }
}
