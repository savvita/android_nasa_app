package com.savita.nasa_app.models;

import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.CloseApproachDataConfig;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CloseApproachData {
    private Date closeApproachDateFull;
    private double relativeVelocity;
    private double missDistance;
    private String orbitingBody;

    public Date getCloseApproachDateFull() {
        return closeApproachDateFull;
    }

    public void setCloseApproachDateFull(Date closeApproachDateFull) {
        this.closeApproachDateFull = closeApproachDateFull;
    }

    public double getRelativeVelocity() {
        return relativeVelocity;
    }

    public void setRelativeVelocity(double relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }

    public double getMissDistance() {
        return missDistance;
    }

    public void setMissDistance(double missDistance) {
        this.missDistance = missDistance;
    }

    public String getOrbitingBody() {
        return orbitingBody;
    }

    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }

    @Override
    public String toString() {
        return "CloseApproachData{" +
                "closeApproachDateFull=" + closeApproachDateFull +
                ", relativeVelocity=" + relativeVelocity +
                ", missDistance=" + missDistance +
                ", orbitingBody='" + orbitingBody + '\'' +
                '}';
    }

    private static String getString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

    private static double getDouble(JSONObject obj, String key) {
        try {
            return obj.getDouble(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static CloseApproachData deserialize (JSONObject obj) {
        CloseApproachData data = new CloseApproachData();
        try {
            String dateJson = obj.getString(CloseApproachDataConfig.CLOSE_APPROACH_DATE_FULL_KEY);
            DateFormat formatter = new SimpleDateFormat(CloseApproachDataConfig.DATE_FORMAT, Locale.ENGLISH);
            data.closeApproachDateFull = formatter.parse(dateJson);
        } catch (Exception ex) {
            data.closeApproachDateFull = null;
        }

        try {
            data.relativeVelocity = getDouble(
                    obj.getJSONObject(CloseApproachDataConfig.RELATIVE_VELOCITY_KEY),
                    CloseApproachDataConfig.KILOMETERS_PER_SECOND_KEY);
        } catch (Exception ex) {
        }

        try {
            data.missDistance = getDouble(
                    obj.getJSONObject(CloseApproachDataConfig.MISS_DISTANCE_KEY),
                    CloseApproachDataConfig.ASTRONOMICAL_DISTANCE_KEY);
        } catch (Exception ex) {
        }

        data.orbitingBody = getString(obj, CloseApproachDataConfig.ORBITING_BODY_KEY);

        return data;
    }
}
