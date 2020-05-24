# NewsApp

The app display top news from TechCrunch. API and its key generated from https://newsapi.org

Select the news headline -> Read a short description -> tap on description to view complete details in your browser.

This app is made by following only best practices for Android App development. It follows MVVM architecture.

Libraries used:

- OkHttpClient for making network requests.
- Navigation library for fragment navigation.
- Picaso image library for thumbnail image fetching and loading.
- Room persistent library for Data base.
- Dagger android for dependency injection.
- Kotlin coroutines for thread handling.
- Stetho library by Facebook, for debugging/viewing DB in chrome browser in debug mode.
- GSON for converting JSON string into Data object.

NOTE: The app doesn't have complex business logic to write Unit tests. However, it has Dao object which needs to be tested. Such tests can be found at NewsAppTests.kt

