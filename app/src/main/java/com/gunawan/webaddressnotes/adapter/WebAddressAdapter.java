package com.gunawan.webaddressnotes.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.gunawan.webaddressnotes.R;
import com.gunawan.webaddressnotes.model.WebAddress;

import java.util.ArrayList;

public class WebAddressAdapter extends RecyclerView.Adapter<WebAddressAdapter.ViewHolder> {
    private ArrayList<WebAddress> wa;
    private Activity activity;
    private OnCustomItemClickListener mListener;

    public WebAddressAdapter(Activity activity, ArrayList<WebAddress> wa) {
        this.wa         = wa;
        this.activity   = activity;
    }

    public interface OnCustomItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnCustomItemClickListener listener) { mListener = listener; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View view               = inflater.inflate(R.layout.row_web_address, viewGroup, false);
        ViewHolder viewHolder   = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WebAddressAdapter.ViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(wa.get(position).getName());
        viewHolder.tvAddress.setText(wa.get(position).getAddress());
        viewHolder.cardView.setOnClickListener(onClickListener(position));
    }

    private View.OnClickListener onClickListener(final int position) {
        return v -> {
            String url = wa.get(position).getAddress();
            openToBrowser(v, url);
        };
    }

    public void openToBrowser(View v, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))  url = "http://" + url;
        Uri webpage     = Uri.parse(url);
        Intent intent   = new Intent(Intent.ACTION_VIEW, webpage);
        v.getContext().startActivity(intent);
        try {
            v.getContext().startActivity(intent);
        }
        catch (Exception e) {
            if (url.startsWith("http://")) {
                openToBrowser(v, url.replace("http://","https://"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return (null != wa ? wa.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvAddress;
        private ImageView ivOptionMenu;
        private View cardView;

        public ViewHolder(View view, final OnCustomItemClickListener listener) {
            super(view);
            tvName          = view.findViewById(R.id.tvName);
            tvAddress       = view.findViewById(R.id.tvAddress);
            ivOptionMenu    = view.findViewById(R.id.ivOptionMenu);
            cardView        = view.findViewById(R.id.cardView);

            ivOptionMenu.setOnClickListener(v -> {
                if (listener != null) {
                    final int position = getAdapterPosition();
                    Log.e("data adapter", wa.get(position).getName());
                    if(position != RecyclerView.NO_POSITION) {
                        final Context context = ivOptionMenu.getContext();
                        PopupMenu popup = new PopupMenu(context, ivOptionMenu);
                        popup.inflate(R.menu.option_menu_list);
                        popup.setOnMenuItemClickListener(item -> {
                            int itemId = item.getItemId();
                            if (itemId == R.id.action_edit) {
                                listener.onEditClick(position);
                            } else {
                                listener.onDeleteClick(position);
                            }
                            return true;
                        });
                        popup.show();
                    }
                }
            });
        }
    }
}