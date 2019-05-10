# OVER-HEAR

## What the App does?
Over-Hear is an android mobile app that was invented at Def-Hacks 2019 as an effort to increase the accessibility by providing hearing-impaired people a means to interact normally with other people without the need to learn sign languages or lip-reading techniques. The app notifies the user when anyone in the vicinity calls their name out. As soon as the app registers the user's name being called out, it vibrates the smartphone and pops-up a notification. The user can click on this notification to go to the app, where the app converts speech-to-text in real time for the user to comprehend and reply to. All the user has to do is enter their preferred name when they use the app for the first time.

It's simple. It's convenient. It's intuitive. It's accessible.

## How we built it
The app was build ground-up using Android Studio with the help of Java for the backend and XML for the frontend. We used Google's Speech-to-Text API to convert speech from the people nearby to plain text that our user can comprehend in real time. Finally, we also implemented the concept of a 'wake word' (i.e. the user's name) which would trigger the app to create a notification and vibrate the phone in order to aware the user that someone is calling him out. This wake word concept was implemented with the help of an open sourcec API - Porcupine API.

## What's next for Over-Hear
* Adding user-customizable names as wake-words to our app. The Porcupine API we currently use is a limited, open source version, with the full version requiring a paid license.
* We would like to add the functionality of storing conversation messages on the app for the users to review at any convenient time.
* Update real time Multi-lingual Speech-to-Text conversation to accommodate more people and thereby increase the app's accessibility.
* Furthermore, we aim to publish our app to multiple mobile platforms, including iOS.

## Running the Beta Version 
1. Clone the repo locally in your machine
2. Run the app in Android Studio
