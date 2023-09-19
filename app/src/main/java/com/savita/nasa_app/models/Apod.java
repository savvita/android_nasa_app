package com.savita.nasa_app.models;

import android.util.Log;

import com.savita.nasa_app.configs.ApodConfig;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Apod {
    private String copyright;
    private Date date;
    private String explanation;
    private String hdurl;
    private String title;
    private String url;

    public Apod() {
    }

    public static Apod deserialize (JSONObject obj) {
        Apod apod = new Apod();
        apod.copyright = getString(obj, ApodConfig.COPYRIGHT_KEY);
        apod.explanation = getString(obj, ApodConfig.EXPLANATION_KEY);
        apod.hdurl = getString(obj, ApodConfig.HDURL_KEY);
        apod.title = getString(obj, ApodConfig.TITLE_KEY);
        apod.url = getString(obj, ApodConfig.URL_KEY);
        apod.copyright = getString(obj, ApodConfig.COPYRIGHT_KEY);
        try {
            String dateJson = obj.getString(ApodConfig.DATE_KEY);
            DateFormat formatter = new SimpleDateFormat(ApodConfig.DATE_FORMAT);
            apod.date = formatter.parse(dateJson);
        } catch (Exception ex) {
            apod.date = null;
        }

        return apod;
    }

    private static String getString(JSONObject obj, String key) {
        try {
            return obj.getString(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Apod{" +
                "copyright='" + copyright + '\'' +
                ", date=" + date +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
