package com.castingmob.ui.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * This is progress dialog which can be created
 * with spinner view or horizontal progress.
 */
public class ProgressView extends ProgressDialog
{
    private Context mContext;

    //create the progress dialog with spinner style
    public ProgressView(Context context, String message) {
        super(context);
        this.mContext = context;
        this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.setMessage(message);
        this.setCancelable(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    //create the progress dialog with horizontal style
    public ProgressView(Context context, int value,String message) {
        super(context);
        this.mContext = context;
        this.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.setMessage(message);
        this.setProgress(value);
        this.setMax(100);
        this.setCancelable(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    // close the dialog.
    public void closeDialog()
    {
        this.cancel();
        this.dismiss();
    }

    // show dialog
    public void showDialog()
    {
        if (!((Activity) mContext).isFinishing())
        {
            this.show();
        }
    }

}
