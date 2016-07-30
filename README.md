[ ![Logo](docs/logo.png) ](https://play.google.com/store/apps/details?id=com.albandroid.filmoteca&hl=es)

This application allows you to know comfortly the "Filmoteca de Albacete" dates and movies.

**Download** | **[Filmoteca de Albacete](https://play.google.com/store/apps/details?id=com.albandroid.filmoteca&hl=es)**

---

### About the project

This project is in a constant evolution in order to reach a right implementation of the most recommended practices in Android development.

This project represents a [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) implementation structured in three packages: data, presentation and view. This layers are strongly delimited following the dependency rule.



![Clean Architecture](docs/clean_architecture.png)

###Features
The requests are implemented using [Retrofit 2](http://square.github.io/retrofit/) + [RxJava](https://github.com/ReactiveX/RxJava) to get a clean implementation for the parallel and sequencial operations. Its usage of the http cache allows the offline usage for the stored responses.

In order to follow the dependency inversion principle, the project is using [Dagger 2](https://github.com/google/dagger) to implements the dependency inyection. This logic is located in the package internal/di.

Continous integration and play store delivery are implemented using [Travis CI](https://travis-ci.org)
