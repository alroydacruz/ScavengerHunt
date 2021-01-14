package com.alroy.viewpager.ui

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.alroy.viewpager.util.CommonUtils
import com.alroy.viewpager.R
import com.alroy.viewpager.data.FirebaseFireStore
import com.alroy.viewpager.models.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LoginFragment : Fragment(R.layout.login_fragment) {

    lateinit var auth: FirebaseAuth
    private var loadingDialog: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()


        login_btn.setOnClickListener {
            if(!answer_et.text.isNullOrEmpty()) {
                if(answer_et.text.toString().startsWith("?!?",true)){
                    Toast.makeText(requireContext(), answer_et.text.toString().substringAfter("?!?"), Toast.LENGTH_SHORT).show()
                    registerCustom( answer_et.text.toString().substringAfter("?!?").trim())
                }else {
                    loginUser()
                }
            }
            else{
                Toast.makeText(requireContext(), "Email missing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerCustom(emails: String) {
        val emailList = emails.split(" ")
        for(email in emailList){
            registerUser(email)
            Log.i("nigger",email)
        }
    }


    private fun loginUser() {

        loadingDialog = CommonUtils.showLoadingDialog(requireContext())

        view.let { activity?.hideKeyboard() }


        val email = answer_et.text.toString()
        val password = "asdasdasd"
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {

                        loadingDialog?.let { if (it.isShowing) it.cancel() }
                        checkLoginState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        loadingDialog?.let { if (it.isShowing) it.cancel() }
                        showToast(e.message)
                    }
                }
            }
        }

    }


    private fun checkLoginState() {

        if (auth.currentUser == null) {
            showToast("error")
        } else {

            showToast("success")
//            val navOptions = NavOptions.Builder()
//                .setPopUpTo(R.id.loginFragment, true)
//                .build()

            auth.currentUser?.let {
                realTimeRetrieval()
                return
            }
//            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }
    }

    private fun showToast(text: String?) {
        Toast.makeText(requireContext(), text.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun Context.hideKeyboard(view: View) {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun registerUser(email:String) {
        val password = "asdasdasd"
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkRegisterState()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        showToast(e.message)
                    }
                }
            }
        }

    }


    private fun checkRegisterState() {
        if (auth.currentUser == null) {
            showToast("error")
        }
    }


    private fun realTimeRetrieval() {

        FirebaseFireStore.accountsRegisteredCollectionRef.addSnapshotListener { value, error ->
            error?.let {
                Log.i("nigger", "could not retrieve data")
            }

            if((!value!!.exists())&&auth.currentUser!=null){
                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
            }
            value?.let {
                val levelAndBranch = it.toObject(FireBaseModel::class.java)
                levelAndBranch?.let {
                    try {
                        view.let { activity?.hideKeyboard() }
                        if(levelAndBranch.currentBranch == -1 && levelAndBranch.currentLevel == -1){
                            showToast("you have already completed the game")
                            findNavController().navigate(R.id.action_loginFragment_to_congratulationsFragment)
                        }else{
                            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                        }
                    } catch (e: Exception) {
                        Log.i("nigger", e.message.toString())
                    }
                }
            }

        }

    }

}
