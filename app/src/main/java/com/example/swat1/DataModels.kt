package com.example.swat1.model

data class ExamPaper(
    val id: String = "",
    val subject: String = "",
    val examTitle: String = "",
    val year: Int = 0,
    val description: String = "",
    val fileUrl: String = "",
    val session: String = "",    // Summer, Winter, March
    val type: String = "",       // MS or Paper
    val zone: String = "",       // Zone1, Zone2, etc.
    val paperNumber: Int = 0     // 11, 21, etc.
)

