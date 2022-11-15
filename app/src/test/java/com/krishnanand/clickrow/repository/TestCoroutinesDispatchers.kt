package com.krishnanand.clickrow.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Test implementations.
 */
class TestCoroutinesDispatchers: CoroutineDispatchers {

    override val io: CoroutineDispatcher = Dispatchers.Unconfined

    override val main: CoroutineDispatcher = Dispatchers.Unconfined

    override val default: CoroutineDispatcher = Dispatchers.Unconfined
}