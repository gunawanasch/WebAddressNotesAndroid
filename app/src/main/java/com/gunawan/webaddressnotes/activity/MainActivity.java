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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gunawan.webaddressnotes.R;
import com.gunawan.webaddressnotes.adapter.WebAddressAdapter;
import com.gunawan.webaddressnotes.model.WebAddress;
import com.gunawan.webaddressnotes.viewmodel.WebAddressViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerWebAddress;
    private WebAddressViewModel viewModel;
    private FloatingActionButton fabAdd;
    private WebAddressAdapter adapter;
    private ArrayList<WebAddress> listWeb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        recyclerWebAddress  = findViewById(R.id.recyclerWebAddress);
        fabAdd              = findViewById(R.id.fab);
        Toolbar toolbar     = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewModel = new ViewModelProvider(this).get(WebAddressViewModel.class);
        getDataWebAddress();
        fabAdd.setOnClickListener(view -> showDialogFormWebAddress(false, 0));
    }

    private void getDataWebAddress() {
        viewModel.getAllWebAddress().observe(this, new Observer<List<WebAddress>>() {
            @Override
            public void onChanged(List<WebAddress> webAddress) {
                ViewCompat.setNestedScrollingEnabled(recyclerWebAddress, false);
                recyclerWebAddress.setHasFixedSize(true);
                recyclerWebAddress.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
                listWeb.clear();
                listWeb = (ArrayList<WebAddress>) webAddress;
                if (listWeb != null) {
                    adapter = new WebAddressAdapter(MainActivity.this, listWeb);
                    adapter.notifyDataSetChanged();
                    recyclerWebAddress.setAdapter(adapter);
                    adapter.setOnItemClickListener(new WebAddressAdapter.OnCustomItemClickListener() {
                        @Override
                        public void onEditClick(int position) {
                            showDialogFormWebAddress(true, position);
                        }
                        @Override
                        public void onDeleteClick(int position) {
                            viewModel.delete(listWeb.get(position));
                            getDataWebAddress();
                        }
                    });
                }
            }
        });
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
                if (etName.getText().toString().isEmpty() || etAddress.getText().toString().isEmpty()) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                    ab.setMessage("Please complete all fields.");
                    ab.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dlg, int sumthin) {}
                    }).show();
                }
                else {
                    if (isEdit) {
                        WebAddress webAddress = new WebAddress(etName.getText().toString().trim(), etAddress.getText().toString().trim());
                        webAddress.setId(listWeb.get(position).getId());
                        viewModel.update(webAddress);
                    }
                    else {
                        viewModel.insert(new WebAddress(etName.getText().toString().trim(), etAddress.getText().toString().trim()));
                    }
                    mBottomSheetDialog.dismiss();
                    getDataWebAddress();
                }
            }
        });
    }

}