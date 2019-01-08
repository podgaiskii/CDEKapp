package by.marpod.cdekapp.di.modules

import by.marpod.cdekapp.ui.fragment.SplashFragment
import by.marpod.cdekapp.ui.fragment.auth.AuthFragment
import by.marpod.cdekapp.ui.fragment.auth.RegistrationFragment
import by.marpod.cdekapp.ui.fragment.moder.HandledRequestsFragment
import by.marpod.cdekapp.ui.fragment.moder.IncomeRequestsFragment
import by.marpod.cdekapp.ui.fragment.moder.ModerMainFragment
import by.marpod.cdekapp.ui.fragment.user.CalculatedRequestsFragment
import by.marpod.cdekapp.ui.fragment.user.RequestsFragment
import by.marpod.cdekapp.ui.fragment.user.SentRequestsFragment
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
    fun availableRoutesFragmentInjector(): CalculatedRequestsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun sentRequestsFragmentInjector(): SentRequestsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun requestsFragmentInjector(): RequestsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun handledRequestsFragmentInjector(): HandledRequestsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun incomeRequestsFragmentInjector(): IncomeRequestsFragment

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun moderMainFragmentInjector(): ModerMainFragment
}