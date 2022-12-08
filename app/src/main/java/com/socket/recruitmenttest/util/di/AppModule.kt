package com.socket.recruitmenttest.util.di

import android.content.Context
import androidx.room.Room
import com.socket.recruitmenttest.data.local.SocketDao
import com.socket.recruitmenttest.data.local.SocketDb
import com.socket.recruitmenttest.data.remote.SocketInstance
import com.socket.recruitmenttest.data.repo.SocketRepository
import com.socket.recruitmenttest.util.utils.Constant.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        SocketDb::class.java,
        DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        socketDb: SocketDb
    ) = socketDb.getSocketDao()

    @Singleton
    @Provides
    fun provideRepository(
        dao: SocketDao,
        socketInstance: SocketInstance
    ) = SocketRepository(dao, socketInstance)

    @Singleton
    @Provides
    fun provideSocketInstance() = SocketInstance
}