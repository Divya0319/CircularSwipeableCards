package com.assignment.infinitelyswipablecards.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Divya Gupta.
 */

/**
 * Dagger App module class created to provide app-level dependencies
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return context
    }
}