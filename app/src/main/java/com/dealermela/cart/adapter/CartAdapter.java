package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.cart.fragment.ShoppingFrg;
import com.dealermela.cart.model.CartLocalDataItem;
import com.dealermela.dbhelper.DatabaseCartAdapter;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.List;

import static com.dealermela.DealerMelaBaseActivity.cartCount;
import static com.dealermela.DealerMelaBaseActivity.setupBadge;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Activity activity;
    private final ShoppingFrg shoppingFrg;
    private final List<CartLocalDataItem> itemArrayList;
    int  totalitem;

    public CartAdapter(Activity activity, List<CartLocalDataItem> itemArrayList, ShoppingFrg shoppingFrg) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        this.shoppingFrg = shoppingFrg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_shopping_cart_item_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.imgMinus.setVisibility(View.GONE);
        holder.imgPlus.setVisibility(View.GONE);
        holder.tvSku.setText(Html.fromHtml("<b>" + "SKU : " + "</b> " + itemArrayList.get(i).getSku()));

        holder.tv_product_category_type.setText(Html.fromHtml("<b>" + "Product Type : " + "</b> " + itemArrayList.get(i).getProduct_category_type()));

        holder.tvMetalDetail.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> " + itemArrayList.get(i).getMetal_detail()));

        if(itemArrayList.get(i).getStone_detail() != null){
            holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getStone_detail()));
        }
        else {
            holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " - "));
        }

        holder.tvPrice.setText(Html.fromHtml("<b>" + AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(itemArrayList.get(i).getPrice())) + "</b> "));
        holder.tvQuantity.setText(itemArrayList.get(i).getQty());

        String sourceString = "";
        if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.RING_ID)) {
            if(!itemArrayList.get(i).getRing_size().equalsIgnoreCase("null")){
                sourceString = "<b>" + "Ring Size : " + "</b> " + itemArrayList.get(i).getRing_size();
                holder.tvRingSize.setText(Html.fromHtml(sourceString));
            }else {
                holder.tvRingSize.setVisibility(View.GONE);
            }

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
            if(!itemArrayList.get(i).getBracelet_size().equalsIgnoreCase("null")){
                sourceString = "<b>" + "Bracelet Size : " + "</b> " + itemArrayList.get(i).getBracelet_size();
                holder.tvRingSize.setText(Html.fromHtml(sourceString));
            }else {
                holder.tvRingSize.setVisibility(View.GONE);
            }

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.BANGLE_ID)) {
            if(!itemArrayList.get(i).getBangle_size().equalsIgnoreCase("null")){
                sourceString = "<b>" + "Bangle Size : " + "</b> " + itemArrayList.get(i).getBangle_size();
                holder.tvRingSize.setText(Html.fromHtml(sourceString));
            }else {
                holder.tvRingSize.setVisibility(View.GONE);
            }

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
            holder.tvRingSize.setVisibility(View.GONE);
            //hide On 15/09/2020 this one for pendent&sets category Bcz parameter Product_Category_Type display same value for this category
//            if(itemArrayList.get(i).getPendent_set_type() != null){
//                sourceString = "<b>" + "Pendent Type : " + "</b> " + itemArrayList.get(i).getPendent_set_type();
//                holder.tvRingSize.setText(Html.fromHtml(sourceString));
//            }
        } else {
            holder.tvRingSize.setVisibility(View.GONE);
        }

        if (itemArrayList.get(i).getProductType().equalsIgnoreCase("simple")) {
            holder.imgPlus.setVisibility(View.GONE);
            holder.imgMinus.setVisibility(View.GONE);
        } else {
            holder.imgPlus.setVisibility(View.VISIBLE);
            holder.imgMinus.setVisibility(View.VISIBLE);
        }

        holder.imgProduct.setImageURI(itemArrayList.get(i).getProImage());

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemArrayList.get(i).getQty());
                totalitem = Integer.parseInt(itemArrayList.get(i).getTotal_item());
                AppLogger.e("TotalItem At Qty increase Time :","----"+totalitem);
                qty++;
                holder.tvQuantity.setText(String.valueOf(qty));
                AppLogger.e("plus old price", "----------" + itemArrayList.get(i).getPrice());
                itemArrayList.get(i).setQty(String.valueOf(qty));
                cartCount++;             //Added this line to update cartcount for every item  But count not updated from API SIDE.
                setupBadge();
                AppLogger.e("CountUpdated ","-----"+cartCount);
                totalitem = totalitem + 1;
                AppLogger.e("Updated Plus TotalItem ","----" + totalitem);
                shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty), String.valueOf(totalitem));
                DatabaseCartAdapter.updateTotalQTY(String.valueOf(totalitem));
//                shoppingFrg.updatetotalItem(String.valueOf())
                notifyItemChanged(i);
                notifyItemRangeChanged(i, itemArrayList.size());
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemArrayList.get(i).getQty());
                totalitem = Integer.parseInt(itemArrayList.get(i).getTotal_item());
                if (String.valueOf(qty).equalsIgnoreCase("1")) {
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    cartCount--;           //Added this line to update cartcount for every item
                    setupBadge();
                    totalitem = totalitem - 1;
                    AppLogger.e("Minus TotalItem ","----" + totalitem);
                    shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty), String.valueOf(totalitem));
                    DatabaseCartAdapter.updateTotalQTY(String.valueOf(totalitem));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());

                } else {
                    qty--;
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
//                    float updatePrice = Float.parseFloat(itemArrayList.get(i).getPrice()) * qty;
//                    itemArrayList.get(i).setPrice(String.valueOf(updatePrice));
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    cartCount--;           //Added this line to update cartcount for every item
                    setupBadge();
                    totalitem = totalitem - 1;
                    AppLogger.e("Minus TotalItem ","----" + totalitem);
                    shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty), String.valueOf(totalitem));
                    DatabaseCartAdapter.updateTotalQTY(String.valueOf(totalitem));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());
                }
            }
        });

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(activity,R.style.AppCompatAlertDialogStyle)
//                        .setTitle(CommonUtils.capitalizeString(activity.getString(R.string.delete)))
//                        .setMessage(activity.getString(R.string.delete_msg))
//                        .setCancelable(false)
//                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                notifyItemRemoved(i);
//                                notifyItemRangeChanged(i, itemArrayList.size());
//                                shoppingFrg.deleteItem(String.valueOf(itemArrayList.get(i).getId()));
//                                itemArrayList.remove(i);
//                                if (itemArrayList.isEmpty()) {
//                                    activity.finish();
//                                }
//                            }
//                        })
//
//                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        })
//                        .show();


                new IOSDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.delete))
                        .setMessage(activity.getString(R.string.remove_cartitem_msg))
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                String  removeqty = itemArrayList.get(i).getQty();
                                totalitem = Integer.parseInt(itemArrayList.get(i).getTotal_item());

                                if( removeqty.equalsIgnoreCase("1")){
                                    totalitem = totalitem - 1;
                                    AppLogger.e("remove product which QTY is 1 :TotalItem ","----" + totalitem);
                                }else {
                                    int qtyremove = Integer.parseInt(itemArrayList.get(i).getQty());
                                    totalitem = totalitem - qtyremove;
                                    AppLogger.e("remove product which QTY is more than 1 :TotalItem ","----" + totalitem);
                                }
                                shoppingFrg.deleteItem(String.valueOf(itemArrayList.get(i).getId()),String.valueOf(totalitem));
                                itemArrayList.remove(i);
                                if (itemArrayList.isEmpty()) {
//                                   activity.finish();
                                }
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                            }
                        })
                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvRemove, tvSku, tvRingSize, tvMetalDetail, tvStoneDetail, tvPrice, tvQuantity, tv_product_category_type;
        final ImageView imgPlus, imgMinus;
        final SimpleDraweeView imgProduct;
        ViewHolder(View itemView) {
            super(itemView);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvRingSize = itemView.findViewById(R.id.tvRingSize);
            tvMetalDetail = itemView.findViewById(R.id.tvMetalDetail);
            tvStoneDetail = itemView.findViewById(R.id.tvStoneDetail);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tv_product_category_type = itemView.findViewById(R.id.tv_product_category_type);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
