package com.dealermela.inventary.adapter;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.inventary.model.InventoryFilterItem;
import com.dealermela.util.ThemePreferences;

import java.util.List;

public class InventoryFilterTitleListAdapter extends BaseAdapter {
    private Activity context; //context
    public List<InventoryFilterItem.Datum> inventoryitems;  //data source of the list adapter
    public int selectedPosition = 0;
    private ThemePreferences themePreferences;

    //public constructor
    public InventoryFilterTitleListAdapter(Activity context, List<InventoryFilterItem.Datum> items) {
        this.context = context;
        this.inventoryitems = items;
        themePreferences = new ThemePreferences(context);
    }

    @Override
    public int getCount() {
        return inventoryitems.size();
    }

    @Override
    public Object getItem(int position) {
        return inventoryitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.act_filter_item_title, parent, false);
        }
        // get current item to be displayed
        // get the TextView for item name and item description
        final LinearLayout linLayout = convertView.findViewById(R.id.linLayout);
//        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);

        final TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        final TextView tvinvcount = convertView.findViewById(R.id.tvinvcount);
        final RelativeLayout relArrow = convertView.findViewById(R.id.relArrow);

        if (selectedPosition == position) {
            linLayout.setBackgroundColor(context.getResources().getColor(R.color.filter_select_item_color));
            tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            relArrow.setVisibility(View.VISIBLE);
//            imgIcon.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        } else {
            if (themePreferences.getTheme().equalsIgnoreCase("black")) {
                linLayout.setBackgroundColor(context.getResources().getColor(R.color.transaction_round_back_black));
                tvTitle.setTextColor(context.getResources().getColor(R.color.white));
                relArrow.setVisibility(View.GONE);
//                imgIcon.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            } else {
                linLayout.setBackgroundColor(context.getResources().getColor(R.color.filter_un_select_item_color));
                tvTitle.setTextColor(context.getResources().getColor(R.color.black));
                relArrow.setVisibility(View.GONE);
//                imgIcon.setColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            }
        }
        tvTitle.setText(inventoryitems.get(position).getLabel());
        if (inventoryitems.get(position).getFiltercount() == 0) {
            tvinvcount.setVisibility(View.GONE);
        } else {
            tvinvcount.setVisibility(View.VISIBLE);
            tvinvcount.setText(String.valueOf(inventoryitems.get(position).getFiltercount()));
        }
        return convertView;
    }
}
