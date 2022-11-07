package com.krishnanand.clickrow.repository

import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

class CoroutineDispatchersTest {

    private lateinit var coroutineDispatchers: CoroutineDispatchersImpl

    @Before
    fun setUp() {
        coroutineDispatchers = CoroutineDispatchersImpl()
    }

    @Test
    fun `validate that dispatchers in Main equals Dispatchers Main`() {
        MatcherAssert.assertThat(coroutineDispatchers.main, CoreMatchers.`is`(Dispatchers.Main))
    }

    @Test
    fun `validate that dispatchers in io equals Dispatchers iO`() {
        MatcherAssert.assertThat(coroutineDispatchers.io, CoreMatchers.`is`(Dispatchers.IO))
    }

    @Test
    fun `validate that dispatchers in default equals Dispatchers default`() {
        MatcherAssert.assertThat(coroutineDispatchers.default, CoreMatchers.`is`(Dispatchers.Default))
    }
}