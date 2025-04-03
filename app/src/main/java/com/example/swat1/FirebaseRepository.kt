package com.example.swat1.firebase

import com.example.swat1.model.ExamPaper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirebaseRepository {

    private fun getFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Fetch unique subjects from "pastPapers" collection
    fun fetchSubjects(
        onResult: (List<String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        getFirestore().collection("pastPapers")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val subjects = querySnapshot.documents.mapNotNull { it.getString("subject") }
                    .distinct()
                onResult(subjects)
            }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    fun fetchPastPapers(
        subject: String,
        onResult: (List<ExamPaper>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = getFirestore().collection("pastPapers")

        db.whereEqualTo("subject", subject)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val papers = mutableListOf<ExamPaper>()

                querySnapshot.documents.forEach { doc ->
                    val paper = doc.toObject(ExamPaper::class.java)?.copy(id = doc.id)
                    paper?.let { papers.add(it) }
                }

                // ✅ Now, filter to ensure all unique combinations of (year, session, type, zone, number)
                val uniquePapers = papers.distinctBy {
                    "${it.year}-${it.session}-${it.type}-${it.zone}-${it.paperNumber}"
                }

                onResult(uniquePapers)
            }
            .addOnFailureListener { exception -> onFailure(exception) }
    }





    // Function to extract session, type, zone, and paper number from filename
    private fun extractMetadataFromFileName(paper: ExamPaper): ExamPaper {
        val fileName = paper.examTitle // Assuming `examTitle` contains the filename

        val session = when {
            fileName.contains("Winter", ignoreCase = true) -> "Winter"
            fileName.contains("Summer", ignoreCase = true) -> "Summer"
            fileName.contains("March", ignoreCase = true) -> "March"
            else -> "Unknown"
        }

        val type = when {
            fileName.contains("ms", ignoreCase = true) -> "MS"
            fileName.contains("paper", ignoreCase = true) -> "Paper"
            else -> "Unknown"
        }

        val zoneMatch = Regex("Zone(\\d+)").find(fileName)
        val zone = zoneMatch?.value ?: "Unknown"

        val paperNumberMatch = Regex("(?:ms|paper)(\\d+)").find(fileName)
        val paperNumber = paperNumberMatch?.groupValues?.get(1)?.toIntOrNull() ?: 0

        return paper.copy(
            session = session,
            type = type,
            zone = zone,
            paperNumber = paperNumber
        )
    }
}
