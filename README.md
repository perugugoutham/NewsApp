# NewsApp

This app is made by following only best practices for Andorid App developement. It follows MVVM architecture.

Libraries used:
- OkHttpClient for making network requests.
- Navigation libary for fragement navigation.
- Picaso image library for thumbnail image fetching and loading.
- Room persistant library for Data base.
- Dagger andorid for dependency injection.
- Kotlin coroutines for thread handling.
- Stetho library by Facebook, for debugging/viewing DB in chrome browser in debug mode.
- GSON for converting JSON string into Data object. 
  

NOTE: The app doesn't have complex bussiness logic to write Unit tests. However, it has Dao object which needs to be tested.
Such tests can be found at NewsAppTests.kt
