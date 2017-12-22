package com.dvo.lgs.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dvo.lgs.R;
import com.dvo.lgs.domain.User;
import com.dvo.lgs.enums.EmailDisplayOption;
import com.dvo.lgs.util.OnItemLongClickListener;

import java.util.List;

/**
 * Created by Dennis van Opstal on 22-12-2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private OnItemLongClickListener listener;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, role, email;
        ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tvUserName);
            role = view.findViewById(R.id.tvUserRole);
            email = view.findViewById(R.id.tvUserEmail);
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

    public UserAdapter(List<User> users, OnItemLongClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_user, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getDisplayName());
        holder.role.setText(user.getRole().getRole());
        if (user.getEmailDisplayOption() == EmailDisplayOption.VISIBLE) {
            holder.email.setText(user.getEmail());
        }
        holder.bind(position, listener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
