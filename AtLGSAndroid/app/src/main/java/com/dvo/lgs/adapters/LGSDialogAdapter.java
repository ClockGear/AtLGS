package com.dvo.lgs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dvo.lgs.R;
import com.dvo.lgs.domain.LGS;

import java.util.List;

/**
 * Created by Dennis van Opstal on 18-1-2018.
 */

public class LGSDialogAdapter extends ArrayAdapter<LGS> {
    public LGSDialogAdapter(Context context, List<LGS> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LGS lgs = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem_lgs_dialog, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvLGSNameDialog);
        tvName.setText(lgs.getName());
        return convertView;
    }
}

