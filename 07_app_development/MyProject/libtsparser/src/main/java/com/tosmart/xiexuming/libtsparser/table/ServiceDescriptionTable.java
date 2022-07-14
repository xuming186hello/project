package com.tosmart.xiexuming.libtsparser.table;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class ServiceDescriptionTable {
    private ArrayList<Service> service;
    private int versionNumber;

    @NonNull
    @Override
    public String toString() {
        return "ServiceDescriptionTable{" +
                "versionNumber=" + versionNumber +
                " ,service=" + service +
                '}';
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public ArrayList<Service> getService() {
        return service;
    }

    public void setService(ArrayList<Service> service) {
        this.service = service;
    }
}
