package com.krishnanand.clickrow.repository

import com.krishnanand.clickrow.data.ClickRow

/**
 * Returns the list of click row repository.
 */
interface ClickRowRepository {

    suspend fun fetchClickRows(): List<ClickRow>
}