# BookMe Application

## Tech stack used

The application is written with MVP architecture design pattern and the language used was Kotlin and RxKotlin framework.

Libraries used were:

* Dagger2 - DI library
* Retrofit2 - Networking library
* Glide - image utility library
* Moshi - serializer/deserializer
* PathView - cool splash screen animation
* ThreeTenAbp - handling dates

## Deployment and execution

Clone the repository and set the build variant to `stagingDebug` and run the application. The `release` buildType won't work because I keep the keystore information stored locally in `local.properties` file and it never gets pushed to a remote repository.

## Application usage

App lets the user type in a book name, searches the google books api and displays the results to the user. Optionally a user can click on a book in order to view book details.