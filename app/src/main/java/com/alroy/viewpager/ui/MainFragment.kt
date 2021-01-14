package com.alroy.viewpager.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.alroy.viewpager.HorizontalMarginItemDecoration
import com.alroy.viewpager.R
import com.alroy.viewpager.adapters.ViewPagerAdapter
import com.alroy.viewpager.data.FirebaseFireStore
import com.alroy.viewpager.data.LevelQuestionDb
import com.alroy.viewpager.models.FireBaseModel
import com.alroy.viewpager.models.QuestionContentModel
import com.alroy.viewpager.util.CommonUtils
import com.alroy.viewpager.util.checkForInternetConnection
import com.alroy.viewpager.viewmodels.MainFragmentViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.toObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main_fragment.*
import kotlinx.android.synthetic.main.level_card_view_item.*
import kotlinx.android.synthetic.main.level_card_view_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.activity_main_fragment) {

    lateinit var questions: ArrayList<QuestionContentModel>
    lateinit var auth: FirebaseAuth
    var mCorrectanswer = true

     val args by navArgs<MainFragmentArgs>()
    private val viewModel: MainFragmentViewModel by viewModels()

companion object {
        var mCurrentLevel = 0
        var mCurrentBranch = 0
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_frag_bg.setAnimation("purple_lottie_bg.json")
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.P) {
//            main_frag_bg.frame = 100
            main_frag_bg.pauseAnimation()
        }


        auth = FirebaseAuth.getInstance()
        questions = LevelQuestionDb.getAllQuestions()

        realTimeRetrieval()
        setUpLevelAdapter(questions)
        subscribeToObservers()


    }

    private fun subscribeToObservers() {

        viewModel.currentBranch.observe(viewLifecycleOwner, { currentBranch ->
//            Log.i("nigger",currentBranch.toString() )
            mCurrentBranch = currentBranch
        })

        viewModel.currentLevel.observe(viewLifecycleOwner, { currentLevel ->
            mCurrentLevel = currentLevel
//            setUpViewPager(currentLevel)
//            Log.i("nigger", "in")

        })
    }

    private fun realTimeRetrieval() {

        FirebaseFireStore.accountsRegisteredCollectionRef.addSnapshotListener { value, error ->
            error?.let {
                Log.i("nigger", "could not retrieve data")

            }
            value?.let {
                val levelAndBranch = it.toObject(FireBaseModel::class.java)
                levelAndBranch?.let {
                    try {
                        view_pager?.let {
                            setUpViewPager(levelAndBranch.currentLevel)
//                            Toast.makeText(requireContext(), levelAndBranch.currentLevel.toString()+"l", Toast.LENGTH_LONG).show()
                        }

                        mCurrentLevel = levelAndBranch.currentLevel
                        mCurrentBranch = levelAndBranch.currentBranch

//                        Toast.makeText(requireContext(), mCurrentBranch.toString(), Toast.LENGTH_SHORT).show()

                        viewModel.updateCurrentLevel(levelAndBranch.currentLevel)
                        viewModel.updateCurrentBranch(levelAndBranch.currentBranch)
                    } catch (e: Exception) {
                        Log.i("maria", e.message.toString())
                    }
                }
            }

        }

    }

    private fun setUpViewPager(i: Int) {

//        val rv = view_pager.getChildAt(0) as ViewPager2
//
//        rv.apply {
//            val ic = adapter?.itemCount?:0
//            if(ic >=i){
//                viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
//                    override fun onGlobalLayout() {
//                        viewTreeObserver.removeOnGlobalLayoutListener(this)
//
//                       view_pager.setCurrentItem(i,false)
//                    }
//
//                })
//            }
//        }

        view_pager.post {
            view_pager.setCurrentItem(i,false)
        }

    }

    private fun getItemCount(questions: List<QuestionContentModel>): Int {
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


    private fun levelClicked(position: Int, view: View) {

        when (view.id) {
            R.id.go_to_next_lvl_btn -> {

                if (getItemCount(questions) - 1 > view_pager.currentItem) {

                    if (checkForInternetConnection(requireContext())) {
                        if (mCorrectanswer && args.answerCorrect) {

                            mCorrectanswer = false

                            val currentLevelDetails =
                                CommonUtils.getRandomBranchFromCurrentLevel(
                                    position + 1,
                                    questions
                                )
                            mCurrentLevel = position + 1
                            mCurrentBranch = currentLevelDetails.branch


                            view_pager.currentItem++

//                            Toast.makeText(
//                                requireContext(),
//                                "level: ${view_pager.currentItem}  branch : ${mCurrentBranch} ",
//                                Toast.LENGTH_SHORT
//                            ).show()
                            viewModel.proceedToNextLevel(view_pager.currentItem, mCurrentBranch)
                        } else {
                            Snackbar.make(requireView(), "Level not completed", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(resources.getColor(R.color.grey1))
                                .setTextColor(resources.getColor(R.color.mainBgTeal))
                                .show()
                        }
                    } else {
                        Snackbar.make(requireView(), "No internet connection", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(resources.getColor(R.color.grey1))
                            .setTextColor(resources.getColor(R.color.mainBgTeal))
                            .show()
                    }
                } else {
                    //done with all levels
                    if (checkForInternetConnection(requireContext()) && mCorrectanswer && args.answerCorrect) {
                        findNavController().navigate(R.id.action_mainFragment_to_congratulationsFragment)
                        viewModel.proceedToNextLevel(-1, -1)
                    }
                }

            }
            (R.id.level_header) -> {
                val action =
                    MainFragmentDirections.actionMainFragmentToLevelFragment(
                        FireBaseModel(
                            mCurrentLevel,
                            mCurrentBranch
                        )
                    )
                findNavController().navigate(action)
            }

        }
    }


    private fun setUpLevelAdapter(questions: MutableList<QuestionContentModel>) {

        args.currentLevelAndBranch?.currentBranch?.let {

            if (mCorrectanswer && args.answerCorrect) {
                val q1 =
                    questions.filter { (it.branch == args.currentLevelAndBranch?.currentBranch) && (it.lvl == args.currentLevelAndBranch?.currentLevel) }[0]


                val q2 = q1.copy()

                q2.answeredCorrectly = 1

                val index = questions.indexOf(q1)
                questions.removeAt(questions.indexOf(q1))
                questions.add(index, q2)

            }
        }

//        args.currentLevelAndBranch?.currentLevel?.let { setUpViewPager(it) }


        val adapter = ViewPagerAdapter(questions) { currentPosition: Int, view: View ->
            levelClicked(
                currentPosition,
                view
            )
        }

        view_pager.adapter = adapter
        view_pager.isUserInputEnabled = false



// You need to retain one page on each side so that the next and previous items are visible
        view_pager.offscreenPageLimit = 1


// Add a PageTransformer that translates the next and previous items horizontally
// towards the center of the screen, which makes them visible
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        view_pager.setPageTransformer(pageTransformer)

// The ItemDecoration gives the current (centered) item horizontal margin so that
// it doesn't occupy the whole screen width. Without it the items overlap
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        view_pager.addItemDecoration(itemDecoration)



    }
}