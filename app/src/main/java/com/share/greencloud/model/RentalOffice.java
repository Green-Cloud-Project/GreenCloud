package com.share.greencloud.model;

import com.google.gson.annotations.SerializedName;

public class RentalOffice {
    @SerializedName("office_id")
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

    public RentalOffice() {
    }

    public RentalOffice(String office_name) {
        this.office_name = office_name;
    }

    public int getOffice_id() {
        return office_id;
    }

    public String getOffice_name() {
        return office_name;
    }

    public String getOffice_location() {
        return office_location;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }

    public int getUmbrella_count() {
        return umbrella_count;
    }
}

