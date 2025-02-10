package com.gunawan.webaddressnotes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gunawan.webaddressnotes.model.WebAddress;
import com.gunawan.webaddressnotes.repository.WebAddressRepository;

import java.util.List;

public class WebAddressViewModel extends AndroidViewModel {
    private WebAddressRepository repository;

    public WebAddressViewModel(@NonNull Application application) {
        super(application);
        repository = new WebAddressRepository(application);
    }

    public void insert(WebAddress webAddress) {
        repository.insert(webAddress);
    }

    public void update(WebAddress webAddress) {
        repository.update(webAddress);
    }

    public void delete(WebAddress webAddress) {
        repository.delete(webAddress);
    }

    public LiveData<List<WebAddress>> getAllWebAddress() {
        return repository.getAllWebAddress();
    }

}