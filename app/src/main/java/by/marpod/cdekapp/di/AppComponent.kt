package by.marpod.cdekapp.di

import by.marpod.cdekapp.CdekApp
import by.marpod.cdekapp.di.modules.*
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            ActivityModule::class,
            ApiModule::class,
            AppActivitiesModule::class,
            AppFragmentsModule::class,
            ApplicationModule::class,
            FirebaseDatabaseModule::class,
            FragmentModule::class,
            ViewModelModule::class
        ]
)
interface AppComponent : AndroidInjector<CdekApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CdekApp>()
}