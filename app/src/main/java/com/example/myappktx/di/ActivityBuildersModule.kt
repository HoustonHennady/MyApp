package com.example.myappktx.di

import com.example.myappktx.Repository.MyFireStore
import com.example.myappktx.View.view.activity.MainActivity
import com.example.myappktx.View.view.fragments.BasketFragment
import com.example.myappktx.View.view.fragments.CategoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun injectMainActivity(): MainActivity
    @ContributesAndroidInjector
    abstract fun injectcategoryFargment(): CategoryFragment
    @ContributesAndroidInjector
    abstract fun ingectbalnkfragment2(): BasketFragment
    @ContributesAndroidInjector
    abstract fun injectMyFireStore(): MyFireStore
}