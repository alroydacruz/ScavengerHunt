package com.alroy.viewpager.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alroy.viewpager.R
import com.alroy.viewpager.adapters.RulesAdapter
import com.alroy.viewpager.data.FirebaseFireStore
import com.alroy.viewpager.data.RulesList
import com.alroy.viewpager.models.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main_fragment.*
import kotlinx.android.synthetic.main.rules_fragment.*

class RulesFragment : Fragment(R.layout.rules_fragment) {

    lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        rules_recyclerview.adapter = RulesAdapter(RulesList.getRulesList(),"#FFFFFF") { view: View ->
            nextBtnClicked(view)
        }
        rules_recyclerview.layoutManager = LinearLayoutManager(context)
        rules_recyclerview.setHasFixedSize(true)
    }

    private fun nextBtnClicked(view: View) {
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.let {
            realTimeRetrieval()
            return
        }
                findNavController().navigate(R.id.action_rulesFragment_to_loginFragment)
    }


    private fun realTimeRetrieval() {
        FirebaseFireStore.accountsRegisteredCollectionRef.addSnapshotListener { value, error ->
            error?.let {
                Log.i("nigger", "could not retrieve data")
            }
            if((!value!!.exists())&&auth.currentUser!=null){
                Log.i("error", "in that shit")
                findNavController().navigate(R.id.action_rulesFragment_to_mainFragment)
            }

            value.let {
                val levelAndBranch = it.toObject(FireBaseModel::class.java)
                levelAndBranch?.let {
                    try {
                        Log.i("nigger", "could not retrieve data")
                        if(levelAndBranch.currentBranch == -1 && levelAndBranch.currentLevel == -1){
                            findNavController().navigate(R.id.action_rulesFragment_to_congratulationsFragment)
                        }else{
                            findNavController().navigate(R.id.action_rulesFragment_to_mainFragment)
                        }
                    } catch (e: Exception) {
                        Log.i("nigger", e.message.toString())
                    }
                }
                return@let
            }





        }

    }
}