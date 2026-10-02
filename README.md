# Sanuli 🇫🇮

Sanuli is a Finnish word-guessing game inspired by Wordle. The goal is to guess a **5-letter Finnish word** within **6 attempts**.

All words in the game are Finnish words.

## 🎮 How to Play

1. Enter a **5-letter Finnish word** using the on-screen keyboard.
2. Press **Tarkista** to check your guess.
3. Use the colors to figure out which letters are correct.
4. Keep guessing until you find the correct word.
5. You have a maximum of **6 attempts**.

### 🎨 Color Guide

- 🟩 **Green** — The letter is in the word and in the correct position.
- 🟨 **Yellow** — The letter is in the word but in the wrong position.
- ⬜ **Gray** — The letter is not in the word.
- 🟦 **Blue** — The letter has not been used yet.

## 🛠️ Technologies

Sanuli is built using:

- **Kotlin** — Main programming language
- **Jetpack Compose** — User interface
- **JSON** — Stores the Finnish word list
- **Android Studio** — Development environment

## 📁 Project Structure

The project is an Android application built with Kotlin and Jetpack Compose.

The Finnish words are stored in a JSON file, which the application reads when starting the game.

## 📋 Requirements

To build and run Sanuli, you need:

- **Android Studio**
- **Kotlin** — Kotlin support is normally included with Android Studio
- An **Android device** or **Android Emulator**

## 🚀 Installation

### 1. Clone the repository

bash:
``` git clone <repository-url>```

### 2. Open the project
Open the cloned project in Android Studio.

### 3. Sync the project
Allow Android Studio to download the required dependencies and finish the Gradle sync.

### 4. Run the application
Connect an Android device or start an Android Emulator.

### Then press:

Run ▶

The Sanuli application should now launch on your device or emulator.

## 🔤 Word List
The game uses a JSON file to store the Finnish words used by Sanuli.

The application separates the words into commonly used words and other words when loading the JSON data.

## ✨ Features
- Finnish word guessing game

- 5-letter words

- 6 attempts per game

- On-screen Finnish keyboard

- Color-coded letter feedback

- Random word selection

- Finnish word list stored in JSON

- Built entirely with Kotlin and Jetpack Compose

## 📄 License
This project is open source.

Feel free to study, modify, and improve the project.
