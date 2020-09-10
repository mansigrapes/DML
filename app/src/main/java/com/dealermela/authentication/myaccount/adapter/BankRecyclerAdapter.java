package com.dealermela.authentication.myaccount.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.ManageBankAct;
import com.dealermela.authentication.myaccount.model.BankResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.Validator;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class BankRecyclerAdapter extends RecyclerView.Adapter<BankRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<BankResponse.Datum> itemArrayList;

    private TextInputEditText edBankName;
    private TextInputEditText edBankAccNo;
    private TextInputEditText edBankAccHolderName;
    private TextInputEditText edIfscCode;
    private TextInputEditText edBranchName;
//    private Dialog dialog;

    public BankRecyclerAdapter(Activity activity, List<BankResponse.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
//        dialog=new Dialog(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_manage_bank_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.tvBankName.setText(itemArrayList.get(i).getBankName());
        holder.tvBankHolderName.setText(itemArrayList.get(i).getBankAccountHolder());
        holder.tvAccountNumber.setText(itemArrayList.get(i).getBankAccountNumber());
        holder.tvIFSCode.setText(itemArrayList.get(i).getIfscCode());
        holder.tvBranchName.setText(itemArrayList.get(i).getBranchName());

        //Edit Bank
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.act_add_bank);
                dialog.show();
                Button btnSave = dialog.findViewById(R.id.btnSave);
                Button btnCancel = dialog.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                edBankName = dialog.findViewById(R.id.edBankName);
                edBankAccNo = dialog.findViewById(R.id.edBankAccNo);
                edBankAccHolderName = dialog.findViewById(R.id.edBankAccHolderName);
                edIfscCode = dialog.findViewById(R.id.edIfscCode);
                edBranchName = dialog.findViewById(R.id.edBranchName);

                edBankName.setText(itemArrayList.get(i).getBankName());
                edBankAccNo.setText(itemArrayList.get(i).getBankAccountNumber());
                edBankAccHolderName.setText(itemArrayList.get(i).getBankAccountHolder());
                edIfscCode.setText(itemArrayList.get(i).getIfscCode());
                edBranchName.setText(itemArrayList.get(i).getBranchName());

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean valid = validateBankDetail();
                        if (valid) {
                            editBank(itemArrayList.get(i).getBankId(), customerId, Objects.requireNonNull(edBankName.getText()).toString(), Objects.requireNonNull(edBankAccNo.getText()).toString(), Objects.requireNonNull(edBankAccHolderName.getText()).toString(), Objects.requireNonNull(edIfscCode.getText()).toString(), Objects.requireNonNull(edBranchName.getText()).toString());
                            itemArrayList.get(i).setBankName(Objects.requireNonNull(edBankName.getText()).toString());
                            itemArrayList.get(i).setBankAccountNumber(Objects.requireNonNull(edBankAccNo.getText()).toString());
                            itemArrayList.get(i).setBankAccountHolder(Objects.requireNonNull(edBankAccHolderName.getText()).toString());
                            itemArrayList.get(i).setIfscCode(Objects.requireNonNull(edIfscCode.getText()).toString());
                            itemArrayList.get(i).setBranchName(Objects.requireNonNull(edBranchName.getText()).toString());
                            notifyItemChanged(i);
                            dialog.dismiss();
                        }
                    }
                });

                Objects.requireNonNull(dialog.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        //Delete Bank
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IOSDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.delete))
                        .setMessage(activity.getString(R.string.delete_msg))
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // continue with delete
                                deleteBank(itemArrayList.get(i).getBankId());
                                itemArrayList.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                                if(itemArrayList.isEmpty()){
                                    ManageBankAct.constraintNoData.setVisibility(View.VISIBLE);
                                }
                                CommonUtils.showSuccessToast(activity,"Deleted successfully");
                            }
                        })
                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

               /* AlertDialog.Builder alert = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);

                // Initialize a new foreground color span instance
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(activity.getResources().getColor(R.color.dml_logo_color));

                // Initialize a new spannable string builder instance
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(activity.getString(R.string.delete));

                // Apply the text color span
                ssBuilder.setSpan(
                        foregroundColorSpan,
                        0,
                        activity.getString(R.string.delete).length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                alert.setTitle(ssBuilder);
                alert.setMessage(activity.getString(R.string.delete_msg));
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteBank(itemArrayList.get(i).getBankId());
                        itemArrayList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, itemArrayList.size());
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvBankName;
        final TextView tvBankHolderName;
        final TextView tvAccountNumber;
        final TextView tvIFSCode;
        final TextView tvBranchName;
        final Button btnEdit;
        final Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvBankName = itemView.findViewById(R.id.tvBankName);
            tvBankHolderName = itemView.findViewById(R.id.tvBankHolderName);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);
            tvIFSCode = itemView.findViewById(R.id.tvIFSCode);
            tvBranchName = itemView.findViewById(R.id.tvBranchName);
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

    private void editBank(String bankId, String customerId, String bankName, String bankAccountNumber, String bankAccountHolder, String ifscCode, String branchName) {
        final ProgressDialog progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.editBankDetail(bankId, customerId, bankName, bankAccountNumber, bankAccountHolder, ifscCode, branchName);

        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                String status = null, message = null;
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response.body()));
                        status = jsonObject.getString("status");
                        message = jsonObject.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    assert status != null;
                    if (status.equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                       progressDialog.dismiss();

                        new IOSDialog.Builder(activity)
                                .setTitle("Thank You!")
                                .setMessage(message)
                                .setCancelable(false)
                                .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        dialog.dismiss();
//                                        SharedPreferences sharedPreferences = new SharedPreferences(activity);
//                                        sharedPreferences.saveLoginData("");
//                                        activity.startActivity(new Intent(activity, LoginAct.class));
//                                        activity.finishAffinity();
                                    }
                                })
                                .show();
                    } else {
                        CommonUtils.showErrorToast(activity, message);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
            }

        });
    }

    private void deleteBank(String bankId) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.deleteBankDetail(bankId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
            }

        });
    }

    private boolean validateBankDetail() {
        if (!Validator.checkEmptyInputLayout(edBankName, "Please enter bank name.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edBankAccNo, "Please enter bank account number.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edBankAccHolderName, "Please enter bank account holder name.")) {
            return false;
        } else if (!Validator.checkEmptyInputLayout(edIfscCode, "Please enter bankIFSC Code.")) {
            return false;
        } else return Validator.checkEmptyInputLayout(edBranchName, "Please enter branch name.");
    }

}
