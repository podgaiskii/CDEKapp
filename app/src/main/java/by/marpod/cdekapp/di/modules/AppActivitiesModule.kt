package by.marpod.cdekapp.di.modules

import by.marpod.cdekapp.ui.activity.*
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppActivitiesModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun splashActivityInjector(): SplashActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun authActivityInjector(): AuthActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun newRequestActivityInjector(): NewRequestActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun moderActivityInjector(): ModerActivity

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun handleRequestActivityInjector(): HandleRequestActivity
}