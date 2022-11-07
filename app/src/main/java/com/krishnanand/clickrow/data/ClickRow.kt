package com.krishnanand.clickrow.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ClickRow(
    val message: String,
    val expiresOn: String
)

@JsonClass(generateAdapter = true)
data class ClickRowHolder(
    val clickRows: List<ClickRow>
)
