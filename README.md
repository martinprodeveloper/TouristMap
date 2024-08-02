# Tourist Map App

An Android application that uses the API [link](http://demo7832319.mockable.io/touristplaces). This project implements Clean Architecture and an approach that applies some of the best practices in Android development.

## Techs/Libraries

- [Kotlin](https://developer.android.com/kotlin)
- [Material Components](https://github.com/material-components/material-components-android) - Version 1.12.0
- [AndroidX Core](https://developer.android.com/jetpack/androidx) - Version 1.13.1
- [AndroidX AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat) - Version 1.7.0
- [AndroidX Activity](https://developer.android.com/jetpack/androidx/releases/activity) - Version 1.9.1
- [AndroidX ConstraintLayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout) - Version 2.1.4
- [AndroidX Fragment](https://developer.android.com/jetpack/androidx/releases/fragment) - Version 1.8.2
- [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - Version 2.8.4
- [Hilt](https://dagger.dev/hilt/) - Version 2.48.1
- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) - Version 1.7.3
- [Retrofit](https://square.github.io/retrofit/) - Version 2.10.0
- [Gson](https://github.com/google/gson) - Version 2.10.1
- [Glide](https://github.com/bumptech/glide) - Version 4.15.0
- [Google Maps SDK](https://developers.google.com/maps/documentation/android-sdk) - Version 19.0.0
- [Google Maps Utils](https://developers.google.com/maps/documentation/android-sdk/utility) - Version 2.2.3

## Project Structure

- **Common**: Common utilities and resources shared across modules.
- **Data**: Data sources, repositories, and data models.
- **Di**: Dependency injection setup (Dagger Hilt).
- **Domain**: Business logic, use cases, and domain models.
- **Ui**: UI components, including activities and fragments.

## Activities

- **Splash Activity**: Launches the app and handles initialization tasks.
- **Home Activity**: Displays the main screen of the app.
- **Home Detail Activity**: Shows detailed information for a selected item.
- **Home Map Activity**: Displays a map with relevant locations.

## Plugins

- **Android Application Plugin**: Version 8.5.1
- **Kotlin Android Plugin**: Version 1.9.0
- **Kotlin Kapt Plugin**: Version 1.9.0
- **Dagger Hilt Android Plugin**: Version 2.48.1
- **Google Services Plugin**: Version 4.4.2