package com.savita.nasa_app.models;

import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.EstimatedDiameterConfig;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EstimatedDiameter {
    private double min;
    private double max;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "EstimatedDiameter{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    private static double getDouble(JSONObject obj, String key) {
        try {
            return obj.getDouble(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static EstimatedDiameter deserialize (JSONObject obj) {
        EstimatedDiameter estimatedDiameter = new EstimatedDiameter();
        estimatedDiameter.min = getDouble(obj, EstimatedDiameterConfig.MIN_KEY);
        estimatedDiameter.max = getDouble(obj, EstimatedDiameterConfig.MAX_KEY);

        return estimatedDiameter;
    }
}
