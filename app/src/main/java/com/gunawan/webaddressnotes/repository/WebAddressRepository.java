package com.gunawan.webaddressnotes.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gunawan.webaddressnotes.database.WebAddressDAO;
import com.gunawan.webaddressnotes.database.WebAddressDatabase;
import com.gunawan.webaddressnotes.model.WebAddress;

import java.util.List;

public class WebAddressRepository {
    private WebAddressDAO webAddressDAO;
    private LiveData<List<WebAddress>> allWebAddress;

    public WebAddressRepository(Application application) {
        WebAddressDatabase webAddressDatabase = WebAddressDatabase.getInstance(application);
        webAddressDAO = webAddressDatabase.webAddressDAO();
    }

    public void insert(WebAddress webAddress) {
        new InsertWebAddressAsyncTask(webAddressDAO).execute(webAddress);
    }

    public void update(WebAddress webAddress) {
        new UpdateWebAddressAsyncTask(webAddressDAO).execute(webAddress);
    }

    public void delete(WebAddress webAddress) {
        new DeleteWebAddressAsyncTask(webAddressDAO).execute(webAddress);
    }

    public LiveData<List<WebAddress>> getAllWebAddress() {
        try {
            allWebAddress = new GetAllWebAddressAsyncTask(webAddressDAO).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allWebAddress;
    }

//    public LiveData<List<WebAddress>> getAllWebAddress() {
//        return allWebAddress;
//    }

    private static class InsertWebAddressAsyncTask extends AsyncTask<WebAddress, Void, Void> {
        private WebAddressDAO webAddressDAO;

        public InsertWebAddressAsyncTask(WebAddressDAO webAddressDAO) {
            this.webAddressDAO = webAddressDAO;
        }

        @Override
        protected Void doInBackground(WebAddress... webAddresses) {
            webAddressDAO.insert(webAddresses[0]);
            return null;
        }
    }

    private static class UpdateWebAddressAsyncTask extends AsyncTask<WebAddress, Void, Void> {
        private WebAddressDAO webAddressDAO;

        public UpdateWebAddressAsyncTask(WebAddressDAO webAddressDAO) {
            this.webAddressDAO = webAddressDAO;
        }

        @Override
        protected Void doInBackground(WebAddress... webAddresses) {
            webAddressDAO.update(webAddresses[0]);
            return null;
        }
    }

    private static class DeleteWebAddressAsyncTask extends AsyncTask<WebAddress, Void, Void> {
        private WebAddressDAO webAddressDAO;

        public DeleteWebAddressAsyncTask(WebAddressDAO webAddressDAO) {
            this.webAddressDAO = webAddressDAO;
        }

        @Override
        protected Void doInBackground(WebAddress... webAddresses) {
            webAddressDAO.delete(webAddresses[0]);
            return null;
        }
    }

    private static class GetAllWebAddressAsyncTask extends AsyncTask<Void, Void, LiveData<List<WebAddress>>> {
        private WebAddressDAO webAddressDAO;

        public GetAllWebAddressAsyncTask(WebAddressDAO webAddressDAO) {
            this.webAddressDAO = webAddressDAO;
        }

        @Override
        protected LiveData<List<WebAddress>> doInBackground(Void... voids) {
            return webAddressDAO.getAllWebAddress();
        }
    }

}