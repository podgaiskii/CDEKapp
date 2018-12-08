package by.marpod.cdekapp.di.modules

import by.marpod.cdekapp.ui.activity.AuthActivity
import by.marpod.cdekapp.ui.activity.MainActivity
import by.marpod.cdekapp.ui.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppActivitiesModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun authActivityInjector(): AuthActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun splashActivityInjector(): SplashActivity
}