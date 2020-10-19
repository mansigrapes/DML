package com.dealermela.inventary.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.model.InventoryItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity activity;
    private final int VIEW_TYPE_ITEM = 0;
    private final List<InventoryItem.Datum> itemArrayList;
    private RecyclerViewClickListener clickListener;


    public InventoryAdapter(Activity activity, List<InventoryItem.Datum> itemArrayList, RecyclerViewClickListener clickListener) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_inventary_list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList == null ? 0 : itemArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private LinearLayout linHideShow;
        private ImageView imgHideShowToggle;
        private CheckBox checkBoxInventory;
        private SimpleDraweeView imgInventoryProduct;
        private TextView tvSku, tvCertificate, tvCategory, tvPrice, tvDiamondQuality, tvVirtualProductPosition, tvStatus, tvName,tvMoreLess;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            linHideShow = itemView.findViewById(R.id.linHideShow);
            imgHideShowToggle = itemView.findViewById(R.id.imgHideShowToggle);
            checkBoxInventory = itemView.findViewById(R.id.checkBoxInventory);
            checkBoxInventory.setClickable(false);
            imgInventoryProduct = itemView.findViewById(R.id.imgInventoryProduct);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvCertificate = itemView.findViewById(R.id.tvCertificate);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiamondQuality = itemView.findViewById(R.id.tvDiamondQuality);
            tvVirtualProductPosition = itemView.findViewById(R.id.tvVirtualProductPosition);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvName = itemView.findViewById(R.id.tvName);
            tvMoreLess = itemView.findViewById(R.id.tvMoreLess);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setData(InventoryItem.Datum datum) {
            tvSku.setText(Html.fromHtml("<b>SKU :</b>" + datum.getSku()));
            tvCertificate.setText(Html.fromHtml("<b>Certificate :</b>" + datum.getCertificateNo()));
            tvCategory.setText(Html.fromHtml("<b>Category :</b>" + datum.getCategoryId()));
            tvPrice.setText(Html.fromHtml("<b>Price :</b> " + CommonUtils.priceFormat(Float.parseFloat(datum.getCustomPrice()))));
            tvDiamondQuality.setText(Html.fromHtml("<b>Diamond Quality :</b> " + datum.getMetalQuality()));
            tvVirtualProductPosition.setText(Html.fromHtml("<b>Virtual Product Position :</b>" + datum.getVirtualProductManager()));
            tvStatus.setText(Html.fromHtml("<b>Status :</b>" + datum.getInventoryStatusValue()));
            tvName.setText(Html.fromHtml("<b>Name :</b>" + datum.getPrName()));
            imgInventoryProduct.setImageURI(AppConstants.INVENTORY_IMAGE+datum.getProductImage());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(final ItemViewHolder holder, final int position) {
        final InventoryItem.Datum datum = itemArrayList.get(position);
        holder.setData(datum);

        if (datum.isSelected()) {
            holder.checkBoxInventory.setChecked(true);
        } else {
            holder.checkBoxInventory.setChecked(false);
        }

        if (datum.isOpen()) {
            Animation slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
            holder.linHideShow.setVisibility(View.VISIBLE);
            holder.linHideShow.startAnimation(slideUp);
            holder.imgHideShowToggle.setImageResource(R.drawable.ic_up);
        } else {
            Animation slideDown = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
            holder.linHideShow.startAnimation(slideDown);
            holder.linHideShow.setVisibility(View.GONE);
            holder.imgHideShowToggle.setImageResource(R.drawable.ic_down);
        }

        holder.imgHideShowToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linHideShow.getVisibility() == View.VISIBLE) {
                    Animation slideDown = AnimationUtils.loadAnimation(activity, R.anim.slide_down);
                    holder.linHideShow.startAnimation(slideDown);
                    holder.linHideShow.setVisibility(View.GONE);
                    holder.imgHideShowToggle.setImageResource(R.drawable.ic_down);
                    itemArrayList.get(position).setOpen(false);
                } else {
                    // slide-up animation
                    Animation slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up);
                    holder.linHideShow.setVisibility(View.VISIBLE);
                    holder.linHideShow.startAnimation(slideUp);
                    holder.imgHideShowToggle.setImageResource(R.drawable.ic_up);
                    itemArrayList.get(position).setOpen(true);
                }
            }
        });

        holder.tvMoreLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.linHideShow.getVisibility() == View.VISIBLE) {
                    holder.linHideShow.setVisibility(View.GONE);
                    holder.imgHideShowToggle.setImageResource(R.drawable.ic_down);
                    holder.tvMoreLess.setText("more");
                    itemArrayList.get(position).setOpen(false);
                } else {
                    holder.linHideShow.setVisibility(View.VISIBLE);
                    holder.imgHideShowToggle.setImageResource(R.drawable.ic_up);
                    itemArrayList.get(position).setOpen(true);
                    holder.tvMoreLess.setText("less");
                }
            }
        });


        holder.checkBoxInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datum.setSelected(!datum.isSelected());
                holder.checkBoxInventory.setChecked(datum.isSelected());
                if (datum.isSelected()) {
                    // check
                    clickListener.onItemCheck(v, position);
                } else {
                    // uncheck
                    clickListener.onItemUnCheck(v, position);
                }
            }
        });
    }
}
