package by.marpod.cdekapp.di.modules

import by.marpod.cdekapp.constants.FirebaseTables
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class FirebaseDatabaseModule {

    @Provides
    @Singleton
    @Named(FirebaseTables.ROOT)
    fun provideRootDatabaseReference() = FirebaseDatabase.getInstance().reference

    @Provides
    @Singleton
    @Named(FirebaseTables.USERS)
    fun provideUsersDatabaseReference() = provideRootDatabaseReference().child(FirebaseTables.USERS)

    @Provides
    @Singleton
    @Named(FirebaseTables.CITIES)
    fun provideCitiesDatabaseReference() = provideRootDatabaseReference().child(FirebaseTables.CITIES)

    @Provides
    @Singleton
    @Named(FirebaseTables.ROUTES)
    fun provideRoutesDatabaseReference() = provideRootDatabaseReference().child(FirebaseTables.ROUTES)

    @Provides
    @Singleton
    @Named(FirebaseTables.REQUESTS)
    fun provideRequestsDatabaseReference() = provideRootDatabaseReference().child(FirebaseTables.REQUESTS)
}