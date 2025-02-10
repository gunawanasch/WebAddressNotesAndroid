package com.gunawan.webaddressnotes.repository;

import android.app.Application;

import com.gunawan.webaddressnotes.database.Database;
import com.gunawan.webaddressnotes.model.WebAddress;

import java.util.ArrayList;

public class WebAddressRepository {
    private Database db;

    public WebAddressRepository(Application application) {
        Database db = new Database(application);
        this.db = db;
    }

    public void insert(WebAddress webAddress) {
        db.addWebAddress(webAddress.getName(), webAddress.getAddress());
    }

    public void update(WebAddress webAddress) {
        db.updateWebAddres(webAddress.getId(), webAddress.getName(), webAddress.getAddress());
    }

    public void delete(WebAddress webAddress) {
        db.deleteWebAddres(webAddress.getId());
    }

    public ArrayList<WebAddress> getAllWebAddress() {
        return db.getListWebAddress();
    }

}