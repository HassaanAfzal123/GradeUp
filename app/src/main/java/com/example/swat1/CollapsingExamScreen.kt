package com.example.swat1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.swat1.firebase.FirebaseRepository
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingExamScreen(navController: NavController) {
    var subjects by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    // For search functionality
    var showSearch by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    // Fetch subjects dynamically from Firestore
    LaunchedEffect(Unit) {
        FirebaseRepository.fetchSubjects(
            onResult = { fetchedSubjects ->
                subjects = fetchedSubjects
                isLoading = false
            },
            onFailure = { exception ->
                errorMessage = exception.message ?: "Error loading subjects"
                isLoading = false
            }
        )
    }

    // Filter subjects based on the search query
    val filteredSubjects = if (searchQuery.isNotBlank()) {
        subjects.filter { it.contains(searchQuery, ignoreCase = true) }
    } else {
        subjects
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (showSearch) {
                        // Custom TextField with Text composable to apply custom colors
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Search papers",
                                    color = Color.Gray.copy(alpha = 0.7f)  // Custom placeholder color
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
                            "Cambridge Past Papers",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Toggle Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage.isNotEmpty() -> Text(text = errorMessage, color = Color.Red, fontSize = 18.sp)
                else -> LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    items(filteredSubjects) { subject ->
                        // URL encode the subject for safe navigation
                        val encodedSubject = URLEncoder.encode(subject, "UTF-8")
                        SubjectItem(subject = subject) {
                            navController.navigate("exam_detail/$encodedSubject")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectItem(subject: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = subject,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF90EE90)
            )
        }
    }
}
