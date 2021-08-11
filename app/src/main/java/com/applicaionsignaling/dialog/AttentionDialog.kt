package com.applicaionsignaling.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AttentionDialog (private val onClick: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Внимание!")
                .setMessage("Для работы приложения необходимо дать разрешение.")
                .setPositiveButton("Ок") { dialog, _ ->
                    dialog.dismiss()
                    onClick.invoke()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity null")
    }
}