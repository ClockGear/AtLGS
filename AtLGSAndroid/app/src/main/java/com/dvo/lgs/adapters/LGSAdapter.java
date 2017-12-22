package com.dvo.lgs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvo.lgs.R;
import com.dvo.lgs.domain.LGS;
import com.dvo.lgs.util.OnItemLongClickListener;

import java.util.List;

/**
 * Created by Dennis van Opstal on 15-12-2017.
 */

public class LGSAdapter extends RecyclerView.Adapter<LGSAdapter.ViewHolder> {

    private List<LGS> lgsList;
    private OnItemLongClickListener listener;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, address;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvLGSName);
            address = view.findViewById(R.id.tvLGSAddress);
        }

        void bind(final int position, final OnItemLongClickListener listener) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(position);
                    return false;
                }
            });
        }
    }

    public LGSAdapter(List<LGS> lgsList, OnItemLongClickListener listener) {
        this.lgsList = lgsList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_lgs, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LGS lgs = lgsList.get(position);
        holder.name.setText(lgs.getName());
        holder.address.setText(lgs.getAddress());
        holder.bind(position, listener);
    }




    @Override
    public int getItemCount() {
        return lgsList.size();
    }
}
