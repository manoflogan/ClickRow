package com.krishnanand.clickrow.repository

import dagger.Binds
import dagger.Module

@Module
interface CoroutineDispatcherModule {

    @Binds
    fun bindCoroutineDispatchers(instance: CoroutineDispatchersImpl): CoroutineDispatchers
}