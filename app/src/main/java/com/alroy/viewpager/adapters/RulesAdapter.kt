package com.alroy.viewpager.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alroy.viewpager.R
import com.alroy.viewpager.models.RuleModel
import kotlinx.android.synthetic.main.rule_header.view.*
import kotlinx.android.synthetic.main.rules_item.view.*


class RulesAdapter(private val ruleList: List<RuleModel>,val textColor:String, private val clickListener:((View)->Unit)): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private  val HEADER:Int = 0
    private  val LIST:Int = 1



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == HEADER) {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.rule_header,
                parent, false
            )
            RuleViewHolderHeader(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.rules_item,
                parent, false
            )
            RulesViewHolderItem(itemView)
        }
    }


    override fun getItemCount() = ruleList.size


   inner class RulesViewHolderItem(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(rule: RuleModel){
            itemView.rule_id.setTextColor(parseColor(textColor))
            itemView.rule_id.text = rule.text

        }
    }

        class RuleViewHolderHeader(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(ruleHeader: RuleModel){
            itemView.header_rules.text = ruleHeader.text
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(getItemViewType(position)==HEADER){
            holder.itemView.go_to_login_btn.setOnClickListener {
                clickListener(holder.itemView.go_to_login_btn)
            }
            (holder as RuleViewHolderHeader).bind(ruleList[position])
        }else{
            (holder as RulesViewHolderItem).bind(ruleList[position])
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if(ruleList[position].mtypeOfView == 0)
            HEADER
        else
            LIST
    }
}
