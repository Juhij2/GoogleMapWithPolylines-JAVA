package com.example.hw09.models;

import java.util.ArrayList;
import java.util.List;

/*
 Name: Juhi Jadhav, Saifuddin Mohammed
  Assignment: HW09
  file: PathResponse.java
  Group: 05
 */

public class PathResponse {
    public ArrayList<Path> path;
    public String title;

    public ArrayList<Path> getPath() {
        return path;
    }

    public void setPath(ArrayList<Path> path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PathResponse(ArrayList<Path> path, String title) {
        this.path = path;
        this.title = title;
    }
}