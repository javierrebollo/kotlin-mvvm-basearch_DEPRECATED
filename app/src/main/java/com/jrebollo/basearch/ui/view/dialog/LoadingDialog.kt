package com.jrebollo.basearch.ui.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.jrebollo.basearch.R

class LoadingDialog : DialogFragment() {
    private var mPbLoading: ProgressBar? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.Loading)
        val inflater = requireActivity().layoutInflater

        val view = inflater.inflate(R.layout.loading_dialog, null)
        builder.setView(view)
        mPbLoading = view.findViewById(R.id.pbLoading)

        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false

        return super.onCreateView(inflater, container, savedInstanceState)

    }
}