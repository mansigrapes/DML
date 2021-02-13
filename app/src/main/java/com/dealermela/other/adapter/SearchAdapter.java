package com.dealermela.other.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dealermela.R;
import com.dealermela.dbhelper.SuggestionsDatabase;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import java.util.ArrayList;

import static com.dealermela.other.activity.NewSearchAct.database;
import static com.dealermela.other.activity.NewSearchAct.searchView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{
    private Activity activity;
    private ArrayList<String> itemlist;
    private long insertresult;

    public SearchAdapter(Activity activity,ArrayList<String> itemlist){
        super();
        this.activity = activity;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_adapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLastSearch.setText(itemlist.get(position));
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView tvLastSearch;

        ViewHolder(View itemView){
            super(itemView);
            tvLastSearch = itemView.findViewById(R.id.tvLastSearch);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            searchView.setQuery(tvLastSearch.getText().toString(),false);

            Cursor c = database.compareSearch(tvLastSearch.getText().toString());
            if(c.getCount() != 0){
                String id = c.getString(c.getColumnIndex(SuggestionsDatabase.FIELD_ID));
                AppLogger.e("Compare Method: Which id going to be delete ","------"+id);
                boolean deleteresult = database.deleteItem(id);
                if(deleteresult == true) {
                    insertresult = database.insertSuggestion(searchView.getQuery().toString());
                    AppLogger.e("Which row inserted with new index ", "----------" + insertresult);
                }
            }
            Intent intent = new Intent(activity, ListAct.class);
            intent.putExtra(AppConstants.ID, "search");
            intent.putExtra(AppConstants.NAME, searchView.getQuery().toString());
            intent.putExtra(AppConstants.bannerListCheck, "");
//            startNewActivityWithIntent(intent);
            activity.startActivity(intent);
            AppLogger.e("search", "---"+ searchView.getQuery());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}