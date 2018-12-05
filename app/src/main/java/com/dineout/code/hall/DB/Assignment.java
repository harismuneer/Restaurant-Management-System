package com.dineout.code.hall.DB;

/**
 * Created by Khalid on 12/1/2018.
 */

public class Assignment {
    String employeeid;
    String tableID;
    String tabletID;


    public Assignment() {
    }

    public Assignment(String employeeid, String tableID, String tabletID) {
        this.employeeid = employeeid;
        this.tableID = tableID;
        this.tabletID = tabletID;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getTabletID() {
        return tabletID;
    }

    public void setTabletID(String tabletID) {
        this.tabletID = tabletID;
    }
}
