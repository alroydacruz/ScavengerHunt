package com.alroy.viewpager.data

import android.util.Log
import com.alroy.viewpager.models.FireBaseModel
import com.alroy.viewpager.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseFireStore {


    companion object {
        lateinit var response: Deferred<Resource<String>>

        var auth = FirebaseAuth.getInstance()
        val accountsRegisteredCollectionRef =
            Firebase.firestore.collection("accountsRegistered")
                .document(auth.currentUser?.email.toString())


        suspend fun updateCurrentLevelAndBranchToFirebase(currentLevel: Int, currentBranch: Int) {
            try {
                val fb = FireBaseModel(currentLevel, currentBranch)

                accountsRegisteredCollectionRef.set(fb).await()
//                Log.i("nigger", "success")

            } catch (e: Exception) {
                Log.i("nigger", e.message.toString())
            }
        }
    }

}