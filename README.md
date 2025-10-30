# Personal Notes App

A modern, cross-platform Personal Notes application built with **Kotlin Multiplatform** and **Compose Multiplatform**, running seamlessly on both Android and iOS.

## 📱 Features

- ✅ **Create Notes** - Add notes with custom titles, HTML content, and dates
- ✅ **List Notes** - View all your notes in a clean, modern interface
- ✅ **Delete Notes** - Remove notes with confirmation dialog
- ✅ **HTML Rendering** - Display rich HTML content in notes with interactive elements
- ✅ **Interactive HTML** - Click buttons and links in HTML content with toast and dialog feedback
- ✅ **PDF Viewer** - View PDF documents from a URL
- ✅ **Local Database** - Persistent storage using SQLDelight
- ✅ **Material Design 3** - Modern, responsive UI with beautiful color palette

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with Clean Architecture principles:

```
PersonalNotesApp/
├── composeApp/
│   ├── commonMain/
│   │   ├── kotlin/com/personal/notes/
│   │   │   ├── data/
│   │   │   │   ├── local/          # Database drivers
│   │   │   │   ├── model/          # Domain models
│   │   │   │   └── repository/     # Repository pattern
│   │   │   ├── di/                 # Dependency injection
│   │   │   ├── ui/
│   │   │   │   ├── addnote/        # Add Note screen & ViewModel
│   │   │   │   ├── notelist/       # Notes List screen & ViewModel
│   │   │   │   ├── notedetail/     # Note Detail screen
│   │   │   │   ├── pdfviewer/      # PDF Viewer screen
│   │   │   │   ├── components/     # Reusable UI components
│   │   │   │   ├── theme/          # App theme & colors
│   │   │   │   └── navigation/     # Navigation routes
│   │   │   └── App.kt              # Main app entry point
│   │   └── sqldelight/             # Database schema
│   ├── androidMain/                # Android-specific code
│   └── iosMain/                    # iOS-specific code
```

### Layers

1. **Presentation Layer** - Compose UI, ViewModels
2. **Domain Layer** - Models, Repository interfaces
3. **Data Layer** - Repository implementations, Database

## 🛠️ Technology Stack

### Core
- **Kotlin Multiplatform** - Shared business logic
- **Compose Multiplatform** - Shared UI
- **Material Design 3** - Modern UI components

### Libraries

#### Database
- **SQLDelight 2.0.1** - Type-safe SQL database
  - Android Driver
  - Native Driver (iOS)
  - Coroutines Extensions

#### Dependency Injection
- **Koin 4.0.3** - Lightweight DI framework
  - Core
  - Compose
  - ViewModel integration

#### Navigation
- **Jetpack Navigation Compose 2.7.0-alpha07** - Type-safe navigation

#### Utilities
- **Kotlinx Coroutines 1.10.2** - Asynchronous programming
- **Kotlinx DateTime 0.6.0** - Date/time handling
- **Kotlinx Serialization 1.8.0** - Data serialization

### Platform-Specific

#### Android
- WebView for HTML & PDF rendering
- Material 3 components
- Target SDK: 36, Min SDK: 24

#### iOS
- WKWebView for HTML & PDF rendering
- Native iOS integration

## 🎨 UI/UX Highlights

- **Modern Color Palette** - Purple-based Material Design 3 theme
- **Responsive Design** - Adapts to different screen sizes
- **Smooth Navigation** - Seamless transitions between screens
- **Empty States** - Friendly messages when no data exists
- **Loading States** - Progress indicators for async operations
- **Error Handling** - Graceful error messages and dialogs
- **Confirmations** - AlertDialog for destructive actions (delete)
- **ModalBottomSheet** - Modern slide-up sheet for HTML click messages

## 📝 How HTML Clicks Work

The app implements a **JavaScript Bridge** to enable communication between HTML content and native code:

### Android Implementation
```kotlin
// WebView with JavaScript interface
webView.addJavascriptInterface(
    object {
        @JavascriptInterface
        fun postMessage(message: String) {
            onMessageReceived(message)
        }
    },
    "JavaScriptBridge"
)
```

### iOS Implementation
```kotlin
// WKWebView with message handler
configuration.userContentController.addScriptMessageHandler(
    messageHandler,
    "JavaScriptBridge"
)
```

### HTML Content
```html
<button onclick="showInfo('Clicked on Button 1')">Click Me 1</button>
<script>
function showInfo(msg) {
    if (window.JavaScriptBridge) {
        window.JavaScriptBridge.postMessage(msg);
    }
}
</script>
```

When a button/link is clicked:
1. JavaScript calls `window.JavaScriptBridge.postMessage(msg)`
2. Native code receives the message
3. A **Toast (Snackbar)** displays the message
4. A **ModalBottomSheet** slides up showing the clicked text with a modern card design

## 📄 How PDF Viewing Works

### Android
- Uses **WebView** with Google Docs viewer
- Loads PDF via: `https://docs.google.com/gview?embedded=true&url={PDF_URL}`
- Supports zoom and navigation

### iOS
- Uses **WKWebView** to load PDF directly
- Native PDF rendering support
- Smooth scrolling and gestures

## 🗄️ Database Schema

```sql
CREATE TABLE NoteEntity (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    body TEXT NOT NULL,
    createdDate INTEGER NOT NULL
);
```

### CRUD Operations
- **Create** - Insert new notes with title, HTML body, and date
- **Read** - Fetch all notes or single note by ID
- **Delete** - Remove notes by ID
- No Update operation (as per requirements)

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- Xcode (for iOS development)
- Kotlin Multiplatform Mobile plugin

### Running the Project

#### Android
1. Open project in Android Studio
2. Sync Gradle
3. Run on emulator or device

#### iOS
1. Open project in Android Studio
2. Run iOS configuration
3. Or open `iosApp/iosApp.xcodeproj` in Xcode

### Building
```bash
# Android
./gradlew assembleDebug

# iOS
# Open iosApp.xcodeproj in Xcode and build
```

## 📦 Dependency Management

All dependencies are managed in `gradle/libs.versions.toml`:
- Centralized version management
- Easy updates
- Consistent versions across modules

## 🎯 Sample HTML

The app includes a "Use Sample HTML" button that inserts this content:

```html
<h2>Welcome to KMP Notes</h2>
<p>This is a <b>sample note</b> with HTML and interactive elements.</p>
<button onclick="showInfo('Clicked on Button 1')">Click Me 1</button>
<a href="#" onclick="showInfo('Link Clicked')">Click This Link</a>
<script>
function showInfo(msg) {
    if (window.JavaScriptBridge) {
        window.JavaScriptBridge.postMessage(msg);
    }
}
</script>
```

## 🔒 Permissions

### Android
- `INTERNET` - For PDF viewing and network operations
- `ACCESS_NETWORK_STATE` - Network connectivity checks

### iOS
- Network permissions handled automatically

## 📱 Screenshots & Walkthrough

### App Flow
1. **Notes List** - View all notes, tap to view details, swipe to delete
2. **Add Note** - Create new note with title, HTML body, and date picker
3. **Note Detail** - View note with rendered HTML, interact with buttons/links
4. **PDF Viewer** - View PDF documents from URL

### User Interactions
- **Tap + Button** - Add new note
- **Tap Note Card** - View note details
- **Tap Delete Icon** - Confirm and delete note
- **Tap PDF Icon** - View sample PDF
- **Click HTML Button** - See toast and dialog

## 🧪 Testing

The project includes:
- Unit test structure in `commonTest`
- Platform-specific tests in `androidTest` and `iosTest`

## 📝 Code Style

- **4 spaces** indentation
- **PascalCase** for classes/composables
- **camelCase** for functions/variables
- **UPPER_SNAKE_CASE** for constants
- Following Kotlin coding conventions

## 🤝 Contributing

This is a demonstration project for Kotlin Multiplatform capabilities. Feel free to explore and learn from it!

## 📄 License

This project is created as a learning example for Kotlin Multiplatform development.

## 🙏 Acknowledgments

- **JetBrains** - Kotlin Multiplatform & Compose Multiplatform
- **Cash App** - SQLDelight
- **Insert Koin** - Dependency Injection
- **Material Design** - UI guidelines and components

---

Built with ❤️ using Kotlin Multiplatform
