package com.btracsolution.deliverysystem.Utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.afollestad.materialdialogs.MaterialDialog;
import com.btracsolution.deliverysystem.Base.BaseBeautifulDialog;
import com.btracsolution.deliverysystem.Base.BaseBeautifulDialogListener;
import com.btracsolution.deliverysystem.R;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public class ProgressDialogOwn {
    ProgressDialog progressDialog;

    public ProgressDialogOwn() {

    }

    public void showDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Loading...");
        //show dialog
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showAlertDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(message)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    public void showInfoAlertDialog(Context context, String message) {
        showBeautifulDialog(context, new BaseBeautifulDialogListener() {
            @Override
            public void onClickOfPositiveButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
                dialog.dismiss();
            }

            @Override
            public void onClickOfNegativeButton(boolean isClick, int position, String extra, MaterialDialog dialog, int identifier) {
                dialog.dismiss();

            }
        }).setIconDrawable(R.drawable.ic_info_outline_white_36dp).setWithAnimationIcon(true).showMessage(message, 107);

    }

    public BaseBeautifulDialog showBeautifulDialog(Context context, BaseBeautifulDialogListener baseBeautifulDialogListener) {
        return new BaseBeautifulDialog(baseBeautifulDialogListener, (Activity) context);
    }

}
