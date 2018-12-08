package by.marpod.cdekapp.di.modules

import android.preference.PreferenceManager
import by.marpod.cdekapp.CdekApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(app: CdekApp) = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(app: CdekApp) = PreferenceManager.getDefaultSharedPreferences(app)
}