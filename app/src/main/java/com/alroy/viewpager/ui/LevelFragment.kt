package com.alroy.viewpager.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alroy.viewpager.R
import com.alroy.viewpager.data.LevelQuestionDb
import com.alroy.viewpager.data.RulesList
import com.alroy.viewpager.models.FireBaseModel
import com.alroy.viewpager.models.QuestionContentModel
import com.alroy.viewpager.models.RuleModel
import kotlinx.android.synthetic.main.level_card_view_item.view.*
import kotlinx.android.synthetic.main.level_fragment.*


class LevelFragment : Fragment(R.layout.level_fragment) {
    lateinit var questions: List<QuestionContentModel>
    lateinit var questionDetails: List<QuestionContentModel>
    private val args by navArgs<LevelFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questions = LevelQuestionDb.getAllQuestions()

        questionDetails =
            questions.filter { it.lvl == args.levelAndBranch.currentLevel && it.branch == args.levelAndBranch.currentBranch }
        setUpLevel()

        lvl_submit_btn.setOnClickListener {
            if (answer_et.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Not quite right !!!", Toast.LENGTH_SHORT).show()
            } else {
                if (questionDetails[0].correctAnswer.toUpperCase() == answer_et.text.toString()
                        .trim().toUpperCase()
                ) {
                    view.let { activity?.hideKeyboard() }

                    val action =
                        LevelFragmentDirections.actionLevelFragmentToMainFragment(true ,
                            FireBaseModel( args.levelAndBranch.currentLevel , args.levelAndBranch.currentBranch)
                        )

//                    Log.i("alboi", args.levelAndBranch.currentLevel.toString())

                    findNavController().navigate(action)



//                    Toast.makeText(requireContext(), "Yayy. Good job, you may proceed", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(requireContext(), "Not quite right !!!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        hintBtn.setOnClickListener {

            if(!questionDetails[0].hints.isNullOrEmpty()){
                val hintList = ArrayList<RuleModel>()
                for(hint in questionDetails[0].hints!!){
                    hintList.add(RuleModel(hint,1))
                }
                val bottonSheetFrag = HintsBottomSheetFragment(hintList)
                bottonSheetFrag.show(parentFragmentManager,"BottomSheetDialog")
        }
        }

        copy_to_clipboard.setOnClickListener {
            copyTextToClipboard()
        }


    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    @SuppressLint("Range")
    private fun setUpLevel() {
        in_lvl_header.text = questionDetails[0].lvlHeader

        if (questionDetails[0].textMessage != "") {
            lvl_text_message.visibility = View.VISIBLE
            lvl_text_message.text = questionDetails[0].textMessage
        }
        else{
            lvl_text_message.visibility = View.GONE
        }
        if (questionDetails[0].link != "") {
            lvl_link.visibility = View.VISIBLE
            lvl_link.text = questionDetails[0].link
        }
        else{
            lvl_link.visibility = View.GONE
        }

        if (questionDetails[0].image != -1) {
            lvl_image.visibility = View.VISIBLE
            lvl_image.setImageResource(questionDetails[0].image)
        }else{
            lvl_image.visibility = View.GONE
        }
        if(!questionDetails[0].hints.isNullOrEmpty()){
            hintBtn.visibility = View.VISIBLE
        }

        lvl_submit_btn.backgroundTintList = ColorStateList.valueOf( Color.parseColor(questionDetails[0].thumbnailBgColor))
        thumbnail.setAnimation( questionDetails[0].thumbnail)
        thumbnail.setMinAndMaxFrame(100,100)
        thumbnail.backgroundTintList = ColorStateList.valueOf( Color.parseColor(questionDetails[0].thumbnailBgColor))

//        Toast.makeText(requireContext(),args.currentLevelAttributes.branch.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun copyTextToClipboard() {
        val textToCopy = questionDetails[0].link
        val clipboardManager =activity?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(requireContext(), "Link copied", Toast.LENGTH_LONG).show()
    }
}