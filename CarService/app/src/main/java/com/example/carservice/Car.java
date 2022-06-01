package com.example.carservice;

import java.util.Calendar;

public class Car {





    String oil;
    String timingBelt;
    String repairShop;
    String tyres;
    String name;
    String kmInitial;
    String kmNew;
    String oilDate;
    String timingBeltDate;
    String repairShopDate;
    String tyresDate;
    String kmNewDate;
    String kmInitalDate;
    int Update;


    public Car(){ }

    public Car(String name,String oil,String timingBelt,String repairShop,String tyres, Calendar calendar,String kmInitialDate,String kmInitial)
    {
        this.name=name;
        this.oil=oil;
        this.timingBelt=timingBelt;
        this.repairShop=repairShop;
        this.tyres=tyres;

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month=month+1;
        oilDate=day +"/"+month+"/"+year;
        timingBeltDate=day +"/"+month+"/"+year;
        repairShopDate=day +"/"+month+"/"+year;
        tyresDate=day +"/"+month+"/"+year;
        this.kmNew="1";
        kmNewDate=day +"/"+month+"/"+year;
        this.kmInitial=kmInitial;
        this.kmInitalDate=kmInitialDate;
        Update=0;


    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getTimingBelt() {
        return timingBelt;
    }

    public void setTimingBelt(String timingBelt) {
        this.timingBelt = timingBelt;
    }

    public String getRepairShop() {
        return repairShop;
    }

    public void setRepairShop(String repairShop) {
        this.repairShop = repairShop;
    }

    public String getTyres() {
        return tyres;
    }

    public void setTyres(String tyres) {
        this.tyres = tyres;
    }

    public String getKmInitial() {
        return kmInitial;
    }

    public void setKmInitial(String kmInitial) {
        this.kmInitial = kmInitial;
    }

    public String getKmNew() {
        return kmNew;
    }

    public void setKmNew(String kmNew) {
        this.kmNew = kmNew;
    }

    public String getOilDate() {
        return oilDate;
    }

    public void setOilDate(String oilDate) {
        this.oilDate = oilDate;
    }

    public String getTimingBeltDate() {
        return timingBeltDate;
    }

    public void setTimingBeltDate(String timingBeltDate) {
        this.timingBeltDate = timingBeltDate;
    }

    public String getRepairShopDate() {
        return repairShopDate;
    }

    public void setRepairShopDate(String repairShopDate) {
        this.repairShopDate = repairShopDate;
    }

    public String getTyresDate() {
        return tyresDate;
    }

    public void setTyresDate(String tyresDate) {
        this.tyresDate = tyresDate;
    }

    public String getKmNewDate() {
        return kmNewDate;
    }

    public void setKmNewDate(String kmNewDate) {
        this.kmNewDate = kmNewDate;
    }

    public String getKmInitalDate() {
        return kmInitalDate;
    }

    public void setKmInitalDate(String kmInitalDate) {
        this.kmInitalDate = kmInitalDate;
    }

    public int getUpdate() {
        return Update;
    }

    public void setUpdate(int update) {
        Update = update;
    }
}
