package com.castingmob.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import com.castingmob.ui.view.ProgressView;

import java.util.ArrayList;

/**
 * This DialogFactory class is used return objects of different Custom Dialogs
 */
public class DialogFactory {

    /**
     * This method return Instance of CustomProgressDialog.
     * @param context
     * @param message
     * @return
     */
    public static ProgressView createProgressDialog(Context context,
                                                      String message) {
        final ProgressView customProgressDialog = new ProgressView(context,message);
        return customProgressDialog;
    }

    /**
     * This method returns AlertDialog with a neutral button
     * */
    public static AlertDialog createAlertDialogWithNeutralButton(Context context, String titleText, String messageText,
                 String neutralButtonText, DialogInterface.OnClickListener neutralButtonClickListner){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set dialog message
        alertDialogBuilder
                .setTitle(titleText)
                .setMessage(messageText)
                .setCancelable(true)
                .setNeutralButton(neutralButtonText, neutralButtonClickListner);

        return  alertDialogBuilder.create();
    }

    /**
     * This method returns AlertDialog with a Positive and Negative buttons
     *
     */
    public static AlertDialog createAlertDialogWithButtons(Context context, String titleText, String messageText,
                       String positiveButtonText, String negativeButtonText, DialogInterface.OnClickListener positiveButtonClickListner,
                       DialogInterface.OnClickListener negativeButtonClickListner){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set dialog message
        alertDialogBuilder
                .setTitle(titleText)
                .setMessage(messageText)
                .setCancelable(true)
                .setPositiveButton(positiveButtonText, positiveButtonClickListner)
                .setNegativeButton(negativeButtonText, negativeButtonClickListner);

        return  alertDialogBuilder.create();
    }

    public static AlertDialog createListDialog(Context context, String title,
                                               String[] items,
                                               DialogInterface.OnClickListener itemClickListner){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1);

        for (int i = 0; i < items.length; i++) {
            arrayAdapter.add(items[i]);
        }

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);
        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter, itemClickListner);
        builderSingle.show();

        return builderSingle.create();
    }

    public static AlertDialog createListDialog(Context context, String title,
                                               ArrayList<?> items,
                                               DialogInterface.OnClickListener itemClickListner){
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1);

        for (int i = 0; i < items.size(); i++) {
            arrayAdapter.add(String.valueOf(items.get(i)));
        }

        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);
        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter, itemClickListner);
        builderSingle.show();

        return builderSingle.create();
    }

}
