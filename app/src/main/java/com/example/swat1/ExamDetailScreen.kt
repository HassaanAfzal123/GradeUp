package com.example.swat1

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.swat1.firebase.FirebaseRepository
import com.example.swat1.model.ExamPaper
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamDetailScreen(subject: String, navController: NavController) {
    val decodedSubject = URLDecoder.decode(subject, "UTF-8")
    var pastPapers by remember { mutableStateOf<List<ExamPaper>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isDarkMode by remember { mutableStateOf(false) } // To track dark mode state

    val context = LocalContext.current

    LaunchedEffect(decodedSubject) {
        FirebaseRepository.fetchPastPapers(decodedSubject,
            onResult = { papers ->
                pastPapers = papers
                isLoading = false
            },
            onFailure = { exception ->
                errorMessage = exception.message ?: "Error fetching data"
                isLoading = false
            }
        )
    }

    // Split the search query into multiple terms based on spaces
    val searchTerms = searchQuery.split("\\s+".toRegex()).filter { it.isNotEmpty() }

    // Filter papers based on multiple search terms
    val filteredPapers = if (searchTerms.isNotEmpty()) {
        pastPapers.filter { paper ->
            searchTerms.all { term ->
                paper.examTitle.contains(term, ignoreCase = true) ||
                        paper.year.toString().contains(term, ignoreCase = true) ||
                        paper.session.contains(term, ignoreCase = true) ||
                        paper.type.contains(term, ignoreCase = true) ||
                        paper.paperNumber.toString().contains(term)
            }
        }
    } else {
        pastPapers
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearch) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search papers",
                                    color = Color.Gray.copy(alpha = 0.7f)
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            "$decodedSubject",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Toggle Search
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Toggle Search",
                            tint = if (isDarkMode) Color.White else Color.Black
                        )
                    }

                    // Dark Mode Toggle
                    IconButton(onClick = { isDarkMode = !isDarkMode }) {
                        Icon(
                            imageVector = Icons.Default.Brightness6,
                            contentDescription = "Toggle Dark Mode",
                            tint = if (isDarkMode) Color.White else Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage.isNotEmpty() -> Text(text = errorMessage)
                else -> LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(filteredPapers) { paper ->
                        PastPaperItem(paper = paper, isDarkMode = isDarkMode) {
                            openPdf(context, paper.fileUrl)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PastPaperItem(paper: ExamPaper, isDarkMode: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isDarkMode) Color.DarkGray else Color.White
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = paper.examTitle ,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDarkMode) Color.White else Color.Black
            )
            Text(text = "${paper.type}  ${paper.paperNumber}", fontSize = 14.sp, color = if (isDarkMode) Color.Gray else Color.Black)
        }
    }
}

fun openPdf(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse(url), "application/pdf")
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent)
}
