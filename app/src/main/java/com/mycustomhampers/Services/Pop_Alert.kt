package com.mycustomhampers.Services

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.gdacciaro.iOSDialog.iOSDialogBuilder

class Pop_Alert(context: Context, activity: Activity) {

    val context: Context = context
    val activity: Activity = activity

    fun showAlert(title: String, msg: String, redirect: Boolean, intent: Intent?){
        iOSDialogBuilder(context)
            .setTitle(title)
            .setSubtitle(msg)
            .setBoldPositiveLabel(true)
            .setCancelable(false)
            .setPositiveListener("Okey") { dialog ->
                dialog.dismiss()
                if(redirect) {
                    context.startActivity(intent);
                    activity.finish()
                }
            }
            .build().show()
    }
}