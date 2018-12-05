package com.dineout.code.hall.DB;

public class Tablet {
    String tabletID;
    String status;

    public Tablet() {
    }

    public Tablet(String tabletID, String status) {
        this.tabletID = tabletID;
        this.status = status;
    }

    public String getTabletID() {
        return tabletID;
    }

    public void setTabletID(String tabletID) {
        this.tabletID = tabletID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

