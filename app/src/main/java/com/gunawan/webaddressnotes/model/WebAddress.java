package com.gunawan.webaddressnotes.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "web_address")
public class WebAddress {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String address;

    public WebAddress(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}