package by.marpod.cdekapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.marpod.cdekapp.di.annotations.ViewModelKey
import by.marpod.cdekapp.viewmodel.AuthViewModel
import by.marpod.cdekapp.viewmodel.CdekViewModelFactory
import by.marpod.cdekapp.viewmodel.InputCitiesViewModel
import by.marpod.cdekapp.viewmodel.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(cdekViewModelFactory: CdekViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun bindRegistrationViewModel(registrationViewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InputCitiesViewModel::class)
    abstract fun bindInputCitiesViewModel(inputCitiesViewModel: InputCitiesViewModel): ViewModel
}