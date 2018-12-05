package com.dineout.code.admin;

public class Tablet {
    String tabletID;
    String status;

    public Tablet(String tabletID, String status) {
        this.tabletID = tabletID;
        this.status = status;
    }

    public Tablet() {
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

