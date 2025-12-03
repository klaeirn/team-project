# Quiz Application

A Java Swing desktop application for creating, taking, and sharing quizzes with leaderboard tracking.

## Overview

This quiz application allows users to create custom quizzes, take auto-generated quizzes from the Open Trivia Database API, share quizzes with friends, and compete on leaderboards. The application follows Clean Architecture principles with clear separation between entities, use cases, interface adapters, and the UI layer.

## Features

- **User Authentication**: Login system with username and password management
- **Create Custom Quizzes**: Build your own quizzes with exactly 10 questions, each with multiple choice options
- **Quickstart Quizzes**: Generate random quizzes from the Open Trivia Database API with customizable category, difficulty, and question type
- **Take Quizzes**: Navigate through questions, save answers, and submit for scoring
- **Share Quizzes**: Generate shareable hash codes to let friends take your quizzes
- **Take Shared Quizzes**: Enter a hash code to access quizzes shared by other users
- **View Results**: See your score, percentage, and performance after completing a quiz
- **Leaderboards**: View rankings for each quiz, sorted by highest score
- **Preview Quizzes**: Preview quizzes before saving or taking them
- **Change Username**: Update your username while maintaining your quiz history

## Application Flow

### 1. Login
- Users log in with username and password
- Credentials are stored in `users.csv`
- Upon successful login, users are taken to the main menu

### 2. Main Menu (Logged In View)
Users can choose to:
- **Take Quiz**: Navigate to the quiz menu
- **Create Quiz**: Build a new custom quiz
- **Change Username**: Update account username

### 3. Quiz Menu
Three options for taking quizzes:
- **Quickstart**: Generate a random quiz from the Open Trivia Database API
  - Select category, difficulty (Easy/Medium/Hard), and question type (Multiple Choice/True-False)
  - Quiz is fetched and displayed immediately
- **Select Existing Quiz**: Choose from your previously created quizzes
- **Take Shared Quiz**: Enter a hash code to access a quiz shared by another user

### 4. Taking a Quiz
- Navigate through questions using Previous/Next buttons
- Select answers from multiple choice options
- Answers are saved as you progress
- Submit when finished to view results

### 5. Creating a Quiz
- Enter quiz name and category
- Add exactly 10 questions, each with:
  - A question title
  - 4 answer options (must be unique and non-empty)
  - A selected correct answer
- Validate questions before adding
- Preview quiz before saving
- Save quiz to your account

### 6. Viewing Results
After completing a quiz:
- See your score (correct answers / total questions)
- View percentage score
- Option to view the leaderboard for that quiz
- Results are automatically saved to leaderboards (except for System-generated quickstart quizzes)

### 7. Leaderboards
- View rankings for any quiz
- Results sorted by highest score first
- Shows username, score, and percentage for each participant
- Tracks performance across all users who took the quiz

### 8. Sharing Quizzes
- Generate a unique hash code for any quiz you created
- Share the hash code with friends
- Friends can enter the hash in "Take Shared Quiz" to access your quiz

## Technical Architecture

### Architecture Pattern
The application follows **Clean Architecture** with four main layers:

1. **Entities**: Core business objects (User, Quiz, Question, QuizResult, Leaderboard)
2. **Use Cases**: Business logic and application rules
3. **Interface Adapters**: Controllers, Presenters, and ViewModels
4. **Frameworks & Drivers**: Swing UI views and data access objects

### Data Storage

- **Users**: Stored in `users.csv` (CSV format)
- **Quizzes**: Stored in `quizzes.json` (JSON format)
- **Leaderboards**: Stored in `leaderboards.json` (JSON format)
- **Shared Quiz Mappings**: Stored in `hashtoquiz.json` (JSON format)

### External API

The application integrates with the **Open Trivia Database API** for generating quickstart quizzes:
- API Documentation: http://opentdb.com/api_config.php
- Supports 24+ categories, 3 difficulty levels, and multiple question types
- Questions are fetched in real-time and cached during the session

### Key Components

- **ViewManager**: Manages view navigation using CardLayout
- **ViewModels**: Hold state and notify views of changes via PropertyChangeListener pattern
- **Data Access Objects**: Handle persistence for users, quizzes, leaderboards, and shared quiz mappings
- **Factories**: Create entity instances (UserFactory, QuizFactory, QuestionFactory)

## Getting Started

1. Compile the project by running main: `Main`
3. Login with existing credentials 
4. Start creating or taking quizzes!

## Solid principles:

1. Our first exemplary example of how we implemented SOLID is our ValidateQuestionInteractor, which shows the SRP. Rather than being used for the entire use case including things such as UI rendering, state management or view navigation, it is only responsible for validating the question and updating the DAO
2. Our second exemplary example of how we implemented SOLID is our ShareQuizInteractor's DataAccessInterface field. Rather than having a high level module depend directly on a DAO increasing coupling, we have both of them depend on high-level rules in a data access interface, showing DIP

=======
In this project, we use the Open Trivia Database Api. The link can be found here:
http://opentdb.com/api_config.php

Here's how to use all the different features in our app:

