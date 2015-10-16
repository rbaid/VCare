package com.me.i8080.employee;

import java.util.Map;

/**
 * Created by rishabh.baid on 5/13/2015.
 */
public class AnalyticData {

    private static AnalyticData instance;
    public static AnalyticData getInstance() {
        if(instance == null){
            instance = new AnalyticData();
        }
        return instance;
    }
    private AnalyticData() {}

    private int[] rating;
    private Map<String,Integer> happyMap;
    private Map<String,Integer> sadMap;
    private Map<String,Integer> rememberMap;

    public int[] getRating() {
        return rating;
    }

    public void setRating(int[] rating) {
        this.rating = rating;
    }

    public Map<String, Integer> getHappyMap() {
        return happyMap;
    }

    public void setHappyMap(Map<String, Integer> happyMap) {
        this.happyMap = happyMap;
    }

    public Map<String, Integer> getSadMap() {
        return sadMap;
    }

    public void setSadMap(Map<String, Integer> sadMap) {
        this.sadMap = sadMap;
    }

    public Map<String, Integer> getRememberMap() {
        return rememberMap;
    }

    public void setRememberMap(Map<String, Integer> rememberMap) {
        this.rememberMap = rememberMap;
    }


}
