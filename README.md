# Dual Music App

A simple Android app that allows you to play Spotify podcasts and YouTube videos simultaneously with independent volume controls.

**Latest Update**: Fixed build issues by removing Spotify App Remote dependency.

## Features

- **YouTube Integration**: Embedded WebView for playing YouTube videos as background music
- **Spotify Integration**: Full Spotify SDK integration for podcast playback
- **Dual Audio Playback**: Play both sources simultaneously
- **Independent Volume Controls**: Control volume for each source separately
- **Global Controls**: Play/pause/stop both sources with single buttons
- **Modern UI**: Material Design interface with cards and intuitive controls

## Setup Instructions

### Prerequisites

1. **Android Studio**: Make sure you have Android Studio installed
2. **Spotify Developer Account**: You'll need to create a Spotify app to get a Client ID

### Spotify Setup

1. Go to [Spotify Developer Dashboard](https://developer.spotify.com/dashboard)
2. Create a new app
3. Add `dualmusicapp://callback` to your app's Redirect URIs
4. Copy your Client ID
5. Replace `YOUR_SPOTIFY_CLIENT_ID` in `MainActivity.kt` with your actual Client ID

### Building the App

1. Open the project in Android Studio
2. Sync Gradle files
3. Connect an Android device or start an emulator
4. Build and run the app

## Usage

### First Time Setup

1. **Connect to Spotify**: Tap "Connect to Spotify" and authorize the app
2. **YouTube Video**: The app loads a default YouTube video (Rick Roll) - you can modify this in the code

### Basic Controls

- **YouTube Section**:
  - Play/Pause buttons control the YouTube video
  - Volume slider (note: WebView volume control is limited)
  
- **Spotify Section**:
  - Connect button to authenticate with Spotify
  - Play/Pause buttons for podcast control
  - Volume slider for Spotify volume control
  
- **Global Controls**:
  - "Play All": Start both YouTube and Spotify
  - "Pause All": Pause both sources
  - "Stop All": Stop both and reset YouTube player

## Code Structure

```
app/
├── src/main/
│   ├── java/com/example/dualmusicapp/
│   │   └── MainActivity.kt          # Main activity with all logic
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml    # UI layout
│   │   ├── values/
│   │   │   ├── strings.xml          # String resources
│   │   │   ├── colors.xml           # Color definitions
│   │   │   └── themes.xml           # App theme
│   └── AndroidManifest.xml          # App permissions and activities
└── build.gradle                     # Dependencies and build config
```

## Dependencies

- **Spotify SDK**: For Spotify authentication and playback control
- **WebView**: For YouTube video embedding
- **Material Design**: For modern UI components
- **AndroidX**: For core Android functionality

## Customization

### Changing the YouTube Video

Edit the `youtubeVideoId` variable in `MainActivity.kt`:

```kotlin
private val youtubeVideoId = "YOUR_VIDEO_ID" // Replace with desired video ID
```

### Changing the Spotify Podcast

Edit the `podcastUri` in the `playSpotifyPodcast()` method:

```kotlin
val podcastUri = "spotify:show:YOUR_PODCAST_ID" // Replace with actual podcast URI
```

## Limitations

1. **YouTube Volume Control**: WebView volume control is limited due to browser security restrictions
2. **Background Playback**: YouTube may pause when the app goes to background
3. **Spotify Premium**: Some features may require Spotify Premium account
4. **Device Compatibility**: Requires Android API level 24+ (Android 7.0+)

## Troubleshooting

### Spotify Connection Issues

1. Make sure your Client ID is correct
2. Verify the redirect URI matches exactly
3. Check that you have a Spotify account and the Spotify app installed

### YouTube Playback Issues

1. Check internet connection
2. Try a different video ID
3. Ensure JavaScript is enabled in WebView

### Build Issues

1. Sync Gradle files
2. Clean and rebuild project
3. Check that all dependencies are properly resolved

## Future Enhancements

- [ ] Add search functionality for both YouTube and Spotify
- [ ] Implement playlist support
- [ ] Add audio equalizer controls
- [ ] Support for more audio sources
- [ ] Background service for continuous playback
- [ ] Widget support for quick controls

## License

This is a personal project for private use only.

## Disclaimer

This app is for personal use only. Please respect YouTube's and Spotify's terms of service. The app uses official APIs and SDKs where available. 