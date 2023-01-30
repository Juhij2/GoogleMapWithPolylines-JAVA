package com.example.hw09.models;

import java.io.Serializable;

/*
 Name: Juhi Jadhav, Saifuddin Mohammed
  Assignment: HW09
  file: path.java
  Group: 05
 */

public class Path implements Serializable {
    public String latitude;
    public String longitude;

    public Path(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
/*
    public Path(JSONObject json) throws JSONException{
        this.latitude = json.getDouble("latitude");
        this.longitude = json.getDouble("longitude");

    }

 */


}
