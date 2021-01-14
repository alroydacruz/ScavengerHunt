package com.alroy.viewpager.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alroy.viewpager.R
import com.alroy.viewpager.models.QuestionContentModel

import kotlinx.android.synthetic.main.level_card_view_item.view.*


class ViewPagerAdapter(
    private val questions: MutableList<QuestionContentModel>,
    val clickListener: ((Int, View) -> Unit)
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerVewHolder>() {

    private val INCORRECT: Int = 0
//    private  val CORRECT:Int = 1


    inner class ViewPagerVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: QuestionContentModel, answered: Boolean,postion:Int) {

            itemView.level_header.text = question.lvlHeader
            itemView.level_header.paintFlags =
                itemView.level_header.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            itemView.level_header.setOnClickListener {
                clickListener(postion, itemView.level_header)


            }

            itemView.go_to_next_lvl_btn.setOnClickListener {
                clickListener(postion, itemView.go_to_next_lvl_btn)

            }
            itemView.animation_view_level.setBackgroundColor(Color.parseColor("#99BBBB"))

            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.P) {
                itemView.animation_view_level.setAnimation(question.thumbnail)
            }else{
                itemView.animation_view_level.setAnimation(question.thumbnail)
                itemView.animation_view_level.setMinAndMaxFrame(100,100)

            }

            if (answered) {
                itemView.go_to_next_lvl_btn.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#00FF00"))
                itemView.go_to_next_lvl_btn.setAnimation("next.json")

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerVewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.level_card_view_item, parent, false)
        return ViewPagerVewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewPagerVewHolder, position: Int) {

        val question = getCurrentLevelCommonAttributes(position)
        if (question.answeredCorrectly == INCORRECT) {
            ViewPagerVewHolder(holder.itemView).bind(question, false,position)
        } else {
            ViewPagerVewHolder(holder.itemView).bind(question, true,position)
        }
    }


    override fun getItemCount(): Int {
        var maxNum = 0
        var currentNum: Int

        for (q in questions) {
            currentNum = q.lvl
            if (currentNum > maxNum) {
                maxNum = currentNum
            }
        }
//        Log.i("nigger",maxNum.toString())
        return maxNum + 1 //cus we start from 0
    }


    private fun getCurrentLevelCommonAttributes(lvl: Int): QuestionContentModel {
             Log.i("maria", lvl.toString())

        val allQuestionsForCurrentLevel =
            questions.filter { it.lvl == lvl && it.answeredCorrectly == 1 }
        Log.i("maria", allQuestionsForCurrentLevel.size.toString())
        return if (allQuestionsForCurrentLevel.isEmpty())
            questions.filter { it.lvl == lvl }[0]
        else
            allQuestionsForCurrentLevel[0]
    }

}