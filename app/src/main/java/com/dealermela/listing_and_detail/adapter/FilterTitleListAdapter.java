package com.dealermela.listing_and_detail.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.listing_and_detail.model.FilterTitleItem;
import com.dealermela.util.AppLogger;
import com.dealermela.util.ThemePreferences;
import com.dealermela.util.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.dealermela.listing_and_detail.activity.FilterAct.filterCurrentPosition;
import static com.dealermela.listing_and_detail.activity.FilterAct.mapFilter;
import static com.dealermela.listing_and_detail.activity.FilterAct.paramKey;
import static com.dealermela.listing_and_detail.activity.ListAct.filterSelectItems;

public class FilterTitleListAdapter extends BaseAdapter {
    private final Activity context; //context
    public final ArrayList<FilterItem.Datum> items; //data source of the list adapter
    public int selectedPosition = 0;
    private ThemePreferences themePreferences;

    //public constructor
    public FilterTitleListAdapter(Activity context, ArrayList<FilterItem.Datum> items) {
        this.context = context;
        this.items = items;
        themePreferences = new ThemePreferences(context);
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

//        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.act_filter_item_title, parent, false);
        }
        // get current item to be displayed

        // get the TextView for item name and item description
        LinearLayout linLayout = convertView.findViewById(R.id.linLayout);
//        ImageView imgIcon = convertView.findViewById(R.id.imgIcon);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvinvcount = convertView.findViewById(R.id.tvinvcount);

        RelativeLayout relArrow = convertView.findViewById(R.id.relArrow);

        tvTitle.setText(items.get(position).getLabel());

        AppLogger.e("svg url", "-------"+items.get(position).getIcon());
//        Utils.fetchSvg(context, items.get(position).getIcon(), imgIcon);

        if (selectedPosition == position) {
            linLayout.setBackgroundColor(context.getResources().getColor(R.color.filter_select_item_color));
            tvTitle.setTextColor(context.getResources().getColor(R.color.white));
            relArrow.setVisibility(View.VISIBLE);
//            imgIcon.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        } else {
            if (themePreferences.getTheme().equalsIgnoreCase("black")) {
                linLayout.setBackgroundColor(context.getResources().getColor(R.color.transaction_round_back_black));
                tvTitle.setTextColor(context.getResources().getColor(R.color.white));
                relArrow.setVisibility(View.INVISIBLE);
//                imgIcon.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
            } else {
                linLayout.setBackgroundColor(context.getResources().getColor(R.color.filter_un_select_item_color));
                tvTitle.setTextColor(context.getResources().getColor(R.color.black));
                relArrow.setVisibility(View.INVISIBLE);
//                imgIcon.setColorFilter(context.getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            }
        }

        if (items.get(position).getFiltercount() == 0) {
                tvinvcount.setVisibility(View.GONE);
        } else {
            tvinvcount.setVisibility(View.VISIBLE);
            tvinvcount.setText(String.valueOf(items.get(position).getFiltercount()));
        }

        return convertView;
    }
}
