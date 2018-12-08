package by.marpod.cdekapp

import by.marpod.cdekapp.di.DaggerAppComponent
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class CdekApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}