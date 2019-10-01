package com.share.greencloud.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "rentaloffices")
public class RentalOffice {
    @PrimaryKey
    @SerializedName("office_id")
    @ColumnInfo(name = "office_id")
    private int office_id;
    @SerializedName("office_name")
    private String office_name;
    @SerializedName("office_location")
    private String office_location;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("umbrella_count")
    private int umbrella_count;

    public int getOffice_id() {
        return office_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public String getOffice_location() {
        return office_location;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public int getUmbrella_count() {
        return umbrella_count;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public void setOffice_location(String office_location) {
        this.office_location = office_location;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setUmbrella_count(int umbrella_count) {
        this.umbrella_count = umbrella_count;
    }
}

