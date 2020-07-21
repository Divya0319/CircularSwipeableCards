package com.assignment.infinitelyswipablecards.di

import com.assignment.infinitelyswipablecards.MainActivity
import com.assignment.infinitelyswipablecards.network.Repository
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Divya Gupta.
 */

/**
 * Dagger App component class created to define interface methods taking relevant class as parameter where
 * dependencies are to be injected
 */
@Component(modules = [AppModule::class, UtilsModule::class])
@Singleton
interface AppComponent {
    fun doInjection(repository: Repository)
    fun doInjection(mainActivity: MainActivity)

}
