package com.krishnanand.clickrow.repository

import android.content.Context
import com.krishnanand.clickrow.data.ClickRow
import com.krishnanand.clickrow.data.ClickRowHolder
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import javax.inject.Inject


class ClickRowRepositoryImpl @Inject constructor(
    private val moshiBuilder: Moshi.Builder,
    private val coroutineDispatchers: CoroutineDispatchers,
    private val context: Context
): ClickRowRepository {

    override suspend fun fetchClickRows(): List<ClickRow> = withContext(coroutineDispatchers.io) {
        context.assets.open(FILE_NAME).use {
            val jsonAdapter = moshiBuilder.build().adapter(ClickRowHolder::class.java)
            return@withContext jsonAdapter.fromJson(it.source().buffer())?.clickRows ?: listOf()
        }
    }

    companion object {
        private const val FILE_NAME = "click_rows.json"
    }
}