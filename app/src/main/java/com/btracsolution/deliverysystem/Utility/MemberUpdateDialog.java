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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterJobDetailPresenter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailActivity;
import com.btracsolution.deliverysystem.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberUpdateDialog implements DialogClickInterface, DialogInterface.OnClickListener {
    public static MemberUpdateDialog mDialog;
    public DialogClickInterface mDialogClickInterface;
    private int mDialogIdentifier;
    private Context mContext;
    private WaiterJobDetailPresenter waiterJobDetailPresenter;

    public static MemberUpdateDialog getInstance() {

        if (mDialog == null) {
            mDialog = new MemberUpdateDialog();

        }

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
//    public void showConfirmDialog(String pTitle, String pMessage,
//                                  String pPositiveButton, String pNegativeButton,
//                                  Context pContext, final int pDialogIdentifier) {
//
//        mDialogClickInterface = (DialogClickInterface) pContext;
//        mDialogIdentifier = pDialogIdentifier;
//        mContext = pContext;
//
//        final Dialog dialog = new Dialog(pContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_custom_member_update);
//
//        if (!pTitle.equals("")) {
//            TextView title = (TextView) dialog.findViewById(R.id.textTitle);
//            title.setText(pTitle);
//            title.setVisibility(View.VISIBLE);
//        }
//
//        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
//        text.setText(pMessage);
//        Button button = (Button) dialog.findViewById(R.id.button1);
//        button.setText(pPositiveButton);
//        Button button1 = (Button) dialog.findViewById(R.id.button);
//        button1.setText(pNegativeButton);
//        dialog.setCancelable(false);
//        dialog.show();      // if decline button is clicked, close the custom dialog
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                mDialogClickInterface.onClickPositiveButton(dialog, pDialogIdentifier, "");
//            }
//        });
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Close dialog
//                mDialogClickInterface.onClickNegativeButton(dialog, pDialogIdentifier);
//            }
//        });
//
//    }

    public void showMemberEditDialog(String pTitle, String pMessage,
                                  String pPositiveButton, String pNegativeButton,
                                  final Context pContext, final int pDialogIdentifier, DialogClickInterface mDialogClickInterface,WaiterJobDetailPresenter waiterJobDetailPresenter) {

        this.mDialogClickInterface = mDialogClickInterface;
        mDialogIdentifier = pDialogIdentifier;
        mContext = pContext;

        final Dialog dialog = new Dialog(pContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_member_update);

        if (!pTitle.equals("")) {
            TextView title = (TextView) dialog.findViewById(R.id.textTitle);
            title.setText(pTitle);
            title.setVisibility(View.VISIBLE);
        }
        final EditText shortNote_et = (EditText) dialog.findViewById(R.id.et_short_note);
        if (pDialogIdentifier == 5) {
            shortNote_et.setVisibility(View.VISIBLE);
        }
        TextView text = (TextView) dialog.findViewById(R.id.textDialog);
        text.setText(pMessage);
        Button button = (Button) dialog.findViewById(R.id.button1);
        button.setText(pPositiveButton);
        Button button1 = (Button) dialog.findViewById(R.id.button);
        button1.setText(pNegativeButton);
        ImageButton btnSearch = (ImageButton) dialog.findViewById(R.id.btnSearch);
        CircleImageView civMem = (CircleImageView) dialog.findViewById(R.id.civMem);
        TextView tvMemName = (TextView) dialog.findViewById(R.id.tvMemName);
        LinearLayout llMeminfo = (LinearLayout) dialog.findViewById(R.id.llMeminfo);
        dialog.setCancelable(false);
        dialog.show();      // if decline button is clicked, close the custom dialog

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shortNote_et.getText().toString().length()>=4){
                    waiterJobDetailPresenter.getSingleMemberData(true,shortNote_et.getText().toString(),shortNote_et,civMem,tvMemName,llMeminfo);
                }
                else {
                    Toast.makeText(pContext, "Please enter valid member id", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                if (shortNote_et.getText().toString().length()>=4){
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    MemberUpdateDialog.this.mDialogClickInterface.onClickPositiveButton(dialog, pDialogIdentifier, shortNote_et.getText().toString());

                }
                else {
                    Toast.makeText(pContext, "Please enter valid member id", Toast.LENGTH_SHORT).show();
                }
          }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                MemberUpdateDialog.this.mDialogClickInterface.onClickNegativeButton(dialog, pDialogIdentifier);
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
                mDialogClickInterface.onClickPositiveButton(pDialog, mDialogIdentifier, "");

                break;
            case DialogInterface.BUTTON_NEGATIVE:
                mDialogClickInterface.onClickNegativeButton(pDialog, mDialogIdentifier);
                break;
        }

    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier, String shortNote) {
    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
    }


}