package com.dealermela;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dealermela.interfaces.ViewBindingListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;

public abstract class DealerMelaBaseFragment extends Fragment implements ViewBindingListener {

    private KProgressHUD hud;
    protected ProgressDialog mProgressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = myFragmentView(inflater, parent, savedInstanseState);
        hud = new KProgressHUD(getActivity());
        init();
        initView();
        postInitView();
        addListener();
        loadData();
        return view;
    }

    public abstract View myFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_content, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    protected void startNewActivity(Class<? extends DealerMelaBaseActivity> activity) {
        startActivity(new Intent(getActivity(), activity));
        overridePendingTransitionEnter();
    }

    protected void startNewActivityWithIntent(Intent intent) {
        startActivity(intent);
        overridePendingTransitionEnter();
    }

    private void overridePendingTransitionEnter() {
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    protected void showProgressDialog(String title, String message) {

        if (hud != null) {
            hud.dismiss();
        }
        hud = KProgressHUD.create(Objects.requireNonNull(getActivity()))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setDetailsLabel(message)
                .show();

       /* if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(getActivity(), title, message);*/
    }

    protected void hideProgressDialog() {
       /* if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }*/

        if (hud != null && hud.isShowing()) {
            hud.dismiss();
            hud = null;
        }
    }
}
