package com.example.myappktx.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FireStoreModule {

    @Provides
    fun getFireBaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun injectFireBase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}