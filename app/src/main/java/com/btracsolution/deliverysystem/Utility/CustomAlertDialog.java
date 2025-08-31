package com.btracsolution.deliverysystem.Utility;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.btracsolution.deliverysystem.R;


/**
 * Created by mahmudul.hasan on 3/5/2018.
 */

public class CustomAlertDialog implements DialogClickInterface, DialogInterface.OnClickListener {
    public static CustomAlertDialog mDialog;
    public DialogClickInterface mDialogClickInterface;
    private int mDialogIdentifier;
    private Context mContext;

    public static CustomAlertDialog getInstance() {

        if (mDialog == null)
            mDialog = new CustomAlertDialog();

        return mDialog;

    }

    /**
     * Show confirmation dialog with two buttons
     *
     * @param pMessage
     * @param pPositiveButton
     * @param pNegativeButton
     * @param pContext
     * @param pDialogIdentifier
     */
    public void showConfirmDialog(String pTitle, String pMessage,
                                  String pPositiveButton, String pNegativeButton,
                                  Context pContext, final int pDialogIdentifier) {

        mDialogClickInterface = (DialogClickInterface) pContext;
        mDialogIdentifier = pDialogIdentifier;
        mContext = pContext;

        final Dialog dialog = new Dialog(pContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_confirmation);

        if (!pTitle.equals("")) {
            TextView title = (TextView) dialog.findViewById(R.id.textTitle);
            title.setText(pTitle);
            title.setVisibility(View.VISIBLE);
        }

        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText(pMessage);
        Button button = (Button) dialog.findViewById(R.id.button1);
        button.setText(pPositiveButton);
        Button button1 = (Button) dialog.findViewById(R.id.button);
        button1.setText(pNegativeButton);
        dialog.setCancelable(false);
        dialog.show();      // if decline button is clicked, close the custom dialog
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                mDialogClickInterface.onClickPositiveButton(dialog, pDialogIdentifier,"");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                mDialogClickInterface.onClickNegativeButton(dialog, pDialogIdentifier);
            }
        });

    }

    public void showConfirmDialog(String pTitle, String pMessage,
                                  String pPositiveButton, String pNegativeButton,
                                  final Context pContext, final int pDialogIdentifier, DialogClickInterface mDialogClickInterface) {

        this.mDialogClickInterface = mDialogClickInterface;
        mDialogIdentifier = pDialogIdentifier;
        mContext = pContext;

        final Dialog dialog = new Dialog(pContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_confirmation);

        if (!pTitle.equals("")) {
            TextView title = (TextView) dialog.findViewById(R.id.textTitle);
            title.setText(pTitle);
            title.setVisibility(View.VISIBLE);
        }
        final EditText shortNote_et = (EditText) dialog.findViewById(R.id.et_short_note);
        if (pDialogIdentifier==4){
            shortNote_et.setVisibility(View.VISIBLE);
        }
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText(pMessage);
        Button button = (Button) dialog.findViewById(R.id.button1);
        button.setText(pPositiveButton);
        Button button1 = (Button) dialog.findViewById(R.id.button);
        button1.setText(pNegativeButton);
        dialog.setCancelable(false);
        dialog.show();      // if decline button is clicked, close the custom dialog
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                CustomAlertDialog.this.mDialogClickInterface.onClickPositiveButton(dialog, pDialogIdentifier,shortNote_et.getText().toString());
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                CustomAlertDialog.this.mDialogClickInterface.onClickNegativeButton(dialog, pDialogIdentifier);
                InputMethodManager imm = (InputMethodManager) pContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(shortNote_et.getWindowToken(), 0);
                dialog.cancel();
            }
        });

    }

    @Override
    public void onClick(DialogInterface pDialog, int pWhich) {

        switch (pWhich) {
            case DialogInterface.BUTTON_POSITIVE:
                mDialogClickInterface.onClickPositiveButton(pDialog, mDialogIdentifier,"");

                break;
            case DialogInterface.BUTTON_NEGATIVE:
                mDialogClickInterface.onClickNegativeButton(pDialog, mDialogIdentifier);
                break;
        }

    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier,String shortNote) {
    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
    }


}
