package by.marpod.cdekapp.di.modules

import by.marpod.cdekapp.ui.fragment.SplashFragment
import by.marpod.cdekapp.ui.fragment.auth.AuthFragment
import by.marpod.cdekapp.ui.fragment.auth.RegistrationFragment
import by.marpod.cdekapp.ui.fragment.user.AvailableRoutesFragment
import by.marpod.cdekapp.ui.fragment.user.InputCitiesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidSupportInjectionModule::class])
interface AppFragmentsModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun splashFragmentInjector(): SplashFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun authFragmentInjector(): AuthFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun registrationFragmentInjector(): RegistrationFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun availableRoutesFragmentInjector(): AvailableRoutesFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun inputCitiesFragmentInjector(): InputCitiesFragment
}