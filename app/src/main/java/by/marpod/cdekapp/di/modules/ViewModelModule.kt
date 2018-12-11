package by.marpod.cdekapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.marpod.cdekapp.di.annotations.ViewModelKey
import by.marpod.cdekapp.viewmodel.*
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
    @ViewModelKey(CitiesViewModel::class)
    abstract fun bindInputCitiesViewModel(citiesViewModel: CitiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SentRequestsViewModel::class)
    abstract fun bindSentRequestsViewModel(sentRequestsViewModel: SentRequestsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalculatedRequestsViewModel::class)
    abstract fun bindAvailableRoutesViewModel(calculatedRequestsViewModel: CalculatedRequestsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RequestsViewModel::class)
    abstract fun bindRequestsViewModel(requestsViewModel: RequestsViewModel): ViewModel

}