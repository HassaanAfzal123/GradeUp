# GradeUp – Cambridge A-Level Exam App

GradeUp is an Android app that provides students with access to Cambridge A-Level past papers. The app allows users to view past exam papers by subject, download them in PDF format, and view detailed information about each paper.

## Features
- View A-Level exam papers categorized by subject (e.g., Chemistry, Mathematics, Physics).
- Search for specific papers using various filters like year, subject, session, and paper type.
- Dark mode support for improved reading experience.
- Integrated with Firebase Firestore for real-time paper retrieval.
- Optimized PDF hosting with Firebase Storage for secure file access.

## Screenshots
![Home](https://github.com/user-attachments/assets/e7b35f9a-4a86-467e-844a-917bb7639857)
![Light Mode](https://github.com/user-attachments/assets/b2d64cbb-a35f-416b-a2a6-d240f0a35716)
![Dark Mode Search](https://github.com/user-attachments/assets/ed35690c-b154-4e58-8f3e-6d1dd6df6797)


## Technologies Used
- **Kotlin**: The primary programming language for Android development.
- **Jetpack Compose**: A modern UI toolkit for building native UIs.
- **Firebase**:
  - **Firestore**: Used to store metadata of past papers.
  - **Storage**: Used to store and serve PDF files.
- **Material Design**: For UI components and dark mode support.

## Installation Instructions

### Prerequisites:
- **Android Studio** (Recommended version: Arctic Fox or above)
- **JDK 8 or above** installed on your machine.
- Firebase account with Firestore and Firebase Storage enabled.

### Steps to Run Locally:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/username/GradeUp.git
