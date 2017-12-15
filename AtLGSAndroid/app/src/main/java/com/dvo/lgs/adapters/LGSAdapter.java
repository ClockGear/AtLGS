package com.dvo.lgs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvo.lgs.R;
import com.dvo.lgs.domain.LGS;

import java.util.List;

/**
 * Created by Dennis van Opstal on 15-12-2017.
 */

public class LGSAdapter extends RecyclerView.Adapter<LGSAdapter.ViewHolder> {

    private List<LGS> lgsList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;

        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvLGSName);
            address = view.findViewById(R.id.tvLGSAddress);
        }
    }

    public LGSAdapter(List<LGS> lgsList) {
        this.lgsList = lgsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_lgs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LGS lgs = lgsList.get(position);
        holder.name.setText(lgs.getName());
        holder.address.setText(lgs.getAddress());
    }

    @Override
    public int getItemCount() {
        return lgsList.size();
    }
}
