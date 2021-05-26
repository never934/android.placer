package com.placer.client.interfaces

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.placer.client.R

interface DeleteDialog {
    fun showDeleteDialog()
    fun showDeleteDialog(activity: Activity){
        AlertDialog.Builder(activity)
            .setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_body)
            .setCancelable(true)
            .setPositiveButton(R.string.common_delete) { dialog, _ -> dialog.cancel() }
            .setNegativeButton(R.string.common_cancel){ dialog, _ -> dialog.cancel() }
            .show()
    }
    interface OnDelete {
        fun deleteFromDialogClicked()
    }
}