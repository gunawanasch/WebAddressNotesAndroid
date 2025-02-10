package com.gunawan.webaddressnotes.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gunawan.webaddressnotes.R;
import com.gunawan.webaddressnotes.adapter.WebAddressAdapter;
import com.gunawan.webaddressnotes.database.Database;
import com.gunawan.webaddressnotes.model.WebAddress;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvWebAddress;
    private FloatingActionButton fabAdd;
    private Database db;
    private WebAddressAdapter adapter;
    private ArrayList<WebAddress> listWeb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        rvWebAddress    = findViewById(R.id.rvWebAddress);
        fabAdd          = findViewById(R.id.fab);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new Database(this);
        getDataWebAddress();
        fabAdd.setOnClickListener(view -> showDialogFormWebAddress(false, 0));
    }

    private void getDataWebAddress() {
        ViewCompat.setNestedScrollingEnabled(rvWebAddress, false);
        rvWebAddress.setHasFixedSize(true);
        rvWebAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listWeb.clear();
        listWeb = db.getListWebAddress();
        if (listWeb != null) {
            adapter = new WebAddressAdapter(this, listWeb);
            adapter.notifyDataSetChanged();
            rvWebAddress.setAdapter(adapter);
            adapter.setOnItemClickListener(new WebAddressAdapter.OnCustomItemClickListener() {
                @Override
                public void onEditClick(int position) {
                    showDialogFormWebAddress(true, position);
                }
                @Override
                public void onDeleteClick(int position) {
                    db.deleteWebAddres(listWeb.get(position).getId());
                    getDataWebAddress();
                }
            });
        }
    }

    private void showDialogFormWebAddress(final boolean isEdit, final int position) {
        final BottomSheetDialog mBottomSheetDialog  = new BottomSheetDialog(this);
        View sheetView                              = getLayoutInflater().inflate(R.layout.dialog_form_web_address, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
        final EditText etName       = sheetView.findViewById(R.id.etName);
        final EditText etAddress    = sheetView.findViewById(R.id.etAddress);
        Button btnSave              = sheetView.findViewById(R.id.btnSave);

        if (isEdit) {
            etName.setText(listWeb.get(position).getName());
            etAddress.setText(listWeb.get(position).getAddress());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty()) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                    ab.setMessage("Please complete all fields.");
                    ab.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dlg, int sumthin) {}
                    }).show();
                }
                else {
                    if (isEdit) {
                        db.updateWebAddres(listWeb.get(position).getId(), etName.getText().toString().trim(), etAddress.getText().toString().trim());
                    }
                    else {
                        db.addWebAddress(etName.getText().toString().trim(), etAddress.getText().toString().trim());
                    }
                    mBottomSheetDialog.dismiss();
                    getDataWebAddress();
                }
            }
        });
    }

}