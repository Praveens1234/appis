package com.appis.android.di

import android.content.Context
import androidx.room.Room
import com.appis.android.data.local.AppisDatabase
import com.appis.android.data.local.ChatDao
import com.appis.android.data.local.ProjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppisDatabase {
        return Room.databaseBuilder(
            context,
            AppisDatabase::class.java,
            "appis.db"
        ).build()
    }

    @Provides
    fun provideProjectDao(db: AppisDatabase): ProjectDao = db.projectDao()

    @Provides
    fun provideChatDao(db: AppisDatabase): ChatDao = db.chatDao()
}
