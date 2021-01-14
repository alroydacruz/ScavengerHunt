package com.alroy.viewpager.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alroy.viewpager.R
import kotlinx.android.synthetic.main.level_fragment.*

const val link ="https://chat.whatsapp.com/HXfK1PGuZm61UvpvCEDOwa"

class CongratulationsFragment :Fragment(R.layout.congratulation_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        copy_to_clipboard.setOnClickListener {
            copyTextToClipboard()
        }

        lvl_link.text = link

    }

    private fun copyTextToClipboard() {

        val clipboardManager =activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", link)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "Link copied", Toast.LENGTH_LONG).show()
    }
}