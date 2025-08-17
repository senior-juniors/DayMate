package com.example.daymate.Features

import androidx.compose.ui.graphics.Color

data class Feature(
    val title: String,
    val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color,
    val onClick: () -> Unit = {}
)