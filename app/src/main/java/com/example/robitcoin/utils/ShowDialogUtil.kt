package com.example.robitcoin.utils

import android.content.Context
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object ShowDialogUtil {

    private fun setupAlertDialogButton(context: Context) {
            MaterialAlertDialogBuilder(context)
                .setTitle("Discard draft?")
                .setMessage("This will permanently delete the current e-mail draft.")
                .setPositiveButton("Discard") { dialog, which ->

                }
                .setNegativeButton("Cancel") { dialog, which ->

                }
                .show()

    }

}