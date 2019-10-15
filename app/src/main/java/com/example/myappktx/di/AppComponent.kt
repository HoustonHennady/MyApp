package com.example.myappktx.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuildersModule::class,
    AppModule::class,
    FireStoreModule::class
    ])

interface AppComponent: AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: BaseApplication): Builder
        fun build(): AppComponent
    }
}