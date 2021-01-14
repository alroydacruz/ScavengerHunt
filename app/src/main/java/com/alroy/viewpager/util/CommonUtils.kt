package com.alroy.viewpager.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.alroy.viewpager.R
import com.alroy.viewpager.models.QuestionContentModel

object CommonUtils {

    fun showLoadingDialog(context: Context) : Dialog{

        val progressDialog = Dialog(context)

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.progress_bar)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
            return it
        }
    }

    fun getRandomBranchFromCurrentLevel(lvl : Int , questions:List<QuestionContentModel>) : QuestionContentModel {
        val allQuestionsForCurrentLevel = questions.filter { it.lvl == lvl }
        val numberOfBranchesForThisLevel=  allQuestionsForCurrentLevel.size
        val randomNumber = (0 until numberOfBranchesForThisLevel).random()
        return allQuestionsForCurrentLevel[randomNumber]
    }


}