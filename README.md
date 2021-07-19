# Haryo Music Player Android App

<img src="https://github.com/CumiTerbang/HaryoMusicPlayer/blob/master/readme_assets/screenshot_1.jpg" width="200" height="355,56"> <img src="https://github.com/CumiTerbang/HaryoMusicPlayer/blob/master/readme_assets/screenshot_2.jpg" width="200" height="355,56"> <img src="https://github.com/CumiTerbang/HaryoMusicPlayer/blob/master/readme_assets/screenshot_3.jpg" width="200" height="355,56">

an android app demo to search your favorite music tracks, artists and albums using API from  [iTunes affiliate API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api)

this is my approach for Telkomsel's [Fita](https://www.myfita.com/)/[Kuncie](https://www.kuncie.com/) Technical Test by making an android application based on the requirement as the solution

Click [here](https://drive.google.com/file/d/17NFtjAtwm5G8Pc26ZhVRboN2BwoAMX0C/view?usp=sharing) to download the app


## Features
1. Search your favorite music from [iTunes affiliate API](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api)
2. Play the available music

## Supported Device
- Android device with minimum API 16 **(Jelly Bean)**

## Build App requirements
- Recomended using Android Studio 4.2.2
- Using Kotlin 1.5.21

## Instructions
1. Clone from this repository
    - Copy repository url
    - Open your Android Studio
    - New -> Project from Version Control..
    - Paste the url, click OK
2. Prepare the Android Virtual Device or real device
3. Build and deploy the app module

## Code Design & Structure
This project is using MVVM design pattern. The project directory consist of 4 directories:
1. **data**: The M (Model) in MVVM. Where we perform data operations.
2. **di**: Dependency Injection directory with the help of [Hilt](https://dagger.dev/hilt/) v2.35.
3. **ui**: User Interface directory for Activities, Fragments and ViewModels helping to display data to the user.
4. **utils**: Urilities directory for helper classes and functions.

Library that used to play the audio file: [ExoPlayer](https://github.com/google/ExoPlayer) v2.14.1
