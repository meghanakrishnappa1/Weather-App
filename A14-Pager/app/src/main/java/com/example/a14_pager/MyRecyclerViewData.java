package com.example.a14_pager;

import java.io.Serializable;

public class MyRecyclerViewData implements Serializable {
    private int zipCode;
    private String name;
    private int temperature;
    private String conditions;
    private int climaCode;
    private int minTemp;
    private int maxTemp;

    public MyRecyclerViewData(int zipCode, String name, int temperature,String conditions,int climaCode, int minTemp, int maxTemp){
        this.zipCode = zipCode;
        this.name = name;
        this.temperature = temperature;
        this.conditions = conditions;
        this.climaCode = climaCode;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getName() {
        return name;
    }

    public String getConditions() {
        return conditions;
    }
    public int getClimaCode() {
        return climaCode;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "zipCode=" + zipCode +
                ", name='" + name + '\'' +
                ", temperature=" + temperature +
                ", conditions='" + conditions + '\'' +
                ", climaCode=" + climaCode +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                '}';
    }
}