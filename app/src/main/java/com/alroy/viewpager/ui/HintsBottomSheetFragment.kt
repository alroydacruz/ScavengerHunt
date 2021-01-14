package com.alroy.viewpager.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alroy.viewpager.R
import com.alroy.viewpager.adapters.RulesAdapter
import com.alroy.viewpager.data.RulesList
import com.alroy.viewpager.models.RuleModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.hint_botton_sheet_frag.*


class HintsBottomSheetFragment(private val hints:ArrayList<RuleModel>)  :BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hint_botton_sheet_frag,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        hintRecyclerView.adapter = RulesAdapter(hints,"#999999") {}
        hintRecyclerView.layoutManager = LinearLayoutManager(context)
        hintRecyclerView.setHasFixedSize(true)
    }
}