package com.savita.nasa_app.models;

import com.savita.nasa_app.configs.ApodConfig;
import com.savita.nasa_app.configs.NeoConfig;

import org.json.JSONArray;
import org.json.JSONObject;

public class Neo {
    private String id;
    private String neoReferenceId;
    private String name;
    private EstimatedDiameter estimatedDiameterMeters;
    private boolean isPotentiallyHazardous;
    private CloseApproachData closeApproachData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EstimatedDiameter getEstimatedDiameterMeters() {
        return estimatedDiameterMeters;
    }

    public void setEstimatedDiameterMeters(EstimatedDiameter estimatedDiameterMeters) {
        this.estimatedDiameterMeters = estimatedDiameterMeters;
    }

    public boolean isPotentiallyHazardous() {
        return isPotentiallyHazardous;
    }

    public void setPotentiallyHazardous(boolean potentiallyHazardous) {
        isPotentiallyHazardous = potentiallyHazardous;
    }

    public CloseApproachData getCloseApproachData() {
        return closeApproachData;
    }

    public void setCloseApproachData(CloseApproachData closeApproachData) {
        this.closeApproachData = closeApproachData;
    }

    @Override
    public String toString() {
        return "Neo{" +
                "id='" + id + '\'' +
                ", neoReferenceId='" + neoReferenceId + '\'' +
                ", name='" + name + '\'' +
                ", estimatedDiameterMeters=" + estimatedDiameterMeters +
                ", isPotentiallyHazardous=" + isPotentiallyHazardous +
                ", closeApproachData=" + closeApproachData +
                '}';
    }

    private static String getString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Neo deserialize (JSONObject obj) {
        Neo neo = new Neo();
        neo.id = getString(obj, NeoConfig.ID_KEY);
        neo.neoReferenceId = getString(obj, NeoConfig.NEO_REFERENCE_ID_KEY);
        neo.name = getString(obj, NeoConfig.NAME_KEY);

        try {
            JSONObject diameterObj = obj.getJSONObject(NeoConfig.ESTIMATED_DIAMETER_KEY);
            JSONObject diameterInMetersObj = diameterObj.getJSONObject(NeoConfig.METERS_KEY);
            neo.estimatedDiameterMeters = EstimatedDiameter.deserialize(diameterInMetersObj);
        } catch (Exception ex) {}

        try {
            neo.isPotentiallyHazardous = obj.getBoolean(NeoConfig.IS_POTENTIALLY_HAZARDOUS_ASTEROID_KEY);
        } catch (Exception ex) {}

        try {
            JSONArray dataArr = obj.getJSONArray(NeoConfig.CLOSE_APPROACH_DATA_KEY);
            if(dataArr.length() > 0) {
                neo.closeApproachData = CloseApproachData.deserialize(dataArr.getJSONObject(0));
            }
        } catch (Exception ex) {}

        return neo;
    }
}
