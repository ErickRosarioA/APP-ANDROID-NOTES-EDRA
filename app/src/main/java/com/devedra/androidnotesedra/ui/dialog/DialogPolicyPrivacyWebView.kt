package com.devedra.androidnotesedra.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.webkit.WebView
import androidx.core.app.DialogCompat
import androidx.fragment.app.DialogFragment
import com.devedra.androidnotesedra.R
import com.devedra.androidnotesedra.databinding.DialogPolicyPrivacyWebviewBinding

class DialogPolicyPrivacyWebView : DialogFragment() {
    private lateinit var webView: WebView
    private lateinit var binding: DialogPolicyPrivacyWebviewBinding

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogPolicyPrivacyWebviewBinding.inflate(inflater)
        webView = binding.root.findViewById(R.id.webView)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val html =
            resources.openRawResource(R.raw.politica_privacy).bufferedReader().use { it.readText() }
        webView.loadData(html, "text/html", "utf-8")
        binding.closeBtn.setOnClickListener{
            dialog?.dismiss()
        }
    }

}