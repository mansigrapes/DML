package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.ForgotPasswordAct;
import com.dealermela.cart.activity.CartAct;
import com.dealermela.cart.fragment.ShoppingFrg;
import com.dealermela.cart.model.CartLocalDataItem;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Activity activity;
    private final ShoppingFrg shoppingFrg;
    private final List<CartLocalDataItem> itemArrayList;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.imgMinus.setVisibility(View.GONE);
        holder.imgPlus.setVisibility(View.GONE);
        holder.tvSku.setText(Html.fromHtml("<b>" + "SKU : " + "</b> " + itemArrayList.get(i).getSku()));

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
            sourceString = "<b>" + "Ring Size : " + "</b> " + itemArrayList.get(i).getRing_size();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.BRACELETS_ID)) {
            sourceString = "<b>" + "Bracelet Size : " + "</b> " + itemArrayList.get(i).getBracelet_size();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.BANGLE_ID)) {
            sourceString = "<b>" + "Bangle Size : " + "</b> " + itemArrayList.get(i).getBangle_size();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getCategoryId().equalsIgnoreCase(AppConstants.PENDANTS_SETS_ID)) {
            sourceString = "<b>" + "Pendent Size : " + "</b> " + itemArrayList.get(i).getPendent_set_type();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));
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
                qty++;
                holder.tvQuantity.setText(String.valueOf(qty));
                AppLogger.e("plus old price", "----------" + itemArrayList.get(i).getPrice());
                itemArrayList.get(i).setQty(String.valueOf(qty));
                notifyItemChanged(i);
                notifyItemRangeChanged(i, itemArrayList.size());
                shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty));
                DealerMelaBaseActivity.cartCount++;           //Added this line to update cartcount for every item
            }
        });


        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemArrayList.get(i).getQty());
                if (String.valueOf(qty).equalsIgnoreCase("1")) {
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());
                    shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty));
                    DealerMelaBaseActivity.cartCount++;              //Added this line to update cartcount for every item

                } else {
                    qty--;
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
//                    float updatePrice = Float.parseFloat(itemArrayList.get(i).getPrice()) * qty;
//                    itemArrayList.get(i).setPrice(String.valueOf(updatePrice));
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());
                    shoppingFrg.updateCartItem(String.valueOf(itemArrayList.get(i).getId()), String.valueOf(qty));
                    DealerMelaBaseActivity.cartCount++;            //Added this line to update cartcount for every item
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
                        .setMessage(activity.getString(R.string.delete_msg))
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                                shoppingFrg.deleteItem(String.valueOf(itemArrayList.get(i).getId()));
                                itemArrayList.remove(i);
                                if (itemArrayList.isEmpty()) {
                                    activity.finish();
                                }
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

        final TextView tvRemove, tvSku, tvRingSize, tvMetalDetail, tvStoneDetail, tvPrice, tvQuantity;
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
