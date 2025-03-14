package com.gklyphon.gameinsight.models

data class Achievement (
    val id: Long,
    val name: String,
    val description: String,
    val image: String,
    val percent: String
)