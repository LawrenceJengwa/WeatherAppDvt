package com.lawrence.weatherappdvt.di.module

import com.lawrence.weatherappdvt.di.scope.PerActivity
import com.lawrence.weatherappdvt.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector



@Module
abstract class ActivityModule {

    /**
     * Injects [MainActivity]
     *
     * @return an instance of [MainActivity]
     */

    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    internal abstract fun mainActivity(): MainActivity
}
