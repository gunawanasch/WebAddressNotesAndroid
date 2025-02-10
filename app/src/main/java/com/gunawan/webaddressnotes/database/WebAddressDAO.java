package com.gunawan.webaddressnotes.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gunawan.webaddressnotes.model.WebAddress;

import java.util.List;

@Dao
public interface WebAddressDAO {

    @Insert
    void insert(WebAddress webAddress);

    @Update
    void update(WebAddress webAddress);

    @Delete
    void delete(WebAddress webAddress);

    @Query("SELECT * FROM web_address")
    LiveData<List<WebAddress>> getAllWebAddress();

}