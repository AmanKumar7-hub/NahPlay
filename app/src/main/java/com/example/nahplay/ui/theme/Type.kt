package com.example.nahplay.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


val NahTypography  = Typography(
    headlineLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize  =24.sp,
        letterSpacing = (0.5).sp,
        color = White
    ),

    //Recently Played
    labelSmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp,
        color = TextSecondary
    ),

    //Song Title(List)
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        color = White
    ),

    //Artits/ album name
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = TextSecondary
    ),

    //Current Playing Song
    titleLarge =  TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        color = White
    ),

    //Body Small Text

    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize =  11.sp,
        color = TextMuted
    )
)
