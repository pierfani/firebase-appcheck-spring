# Firebase App Check Spring Boot Starter

[English version below](#english-version)

## Versione Italiana

Questa libreria fornisce un'integrazione semplice di [Firebase App Check](https://firebase.google.com/docs/app-check) per applicazioni Spring Boot. Permette di proteggere facilmente gli endpoint REST utilizzando un'annotazione personalizzata.

### Caratteristiche

- Annotazione `@FirebaseAppCheck` per proteggere gli endpoint
- Verifica automatica del token Firebase App Check
- Facile integrazione con progetti Spring Boot esistenti

### Installazione

Per utilizzare questa libreria nel tuo progetto, aggiungi le seguenti configurazioni al tuo `pom.xml`:

1. Aggiungi il repository GitHub Packages:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/pierfani/firebase-appcheck-spring</url>
    </repository>
</repositories>
```

2. Aggiungi la dipendenza:

```xml
<dependency>
    <groupId>it.pierfani</groupId>
    <artifactId>firebase-appcheck-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Configurazione

Aggiungi le seguenti proprietà al tuo `application.properties` o `application.yml`:

```properties
it.pierfani.firebaseappcheck.project-number=YOUR_FIREBASE_PROJECT_NUMBER
it.pierfani.firebaseappcheck.jwks-url=https://firebaseappcheck.googleapis.com/v1/jwks
it.pierfani.firebaseappcheck.enabled=true
```

### Utilizzo

Per proteggere un endpoint con Firebase App Check, aggiungi semplicemente l'annotazione `@FirebaseAppCheck` al metodo del controller:

```java
import it.pierfani.firebaseappcheck.FirebaseAppCheck;

@RestController
public class ExampleController {

    @GetMapping("/protected-endpoint")
    @FirebaseAppCheck
    public String protectedEndpoint() {
        return "Questo endpoint è protetto da Firebase App Check";
    }
}
```

### Gestione degli errori

La libreria lancia `FirebaseAppCheckException` in caso di errori durante la verifica del token. Puoi gestire questa eccezione nel tuo controller o utilizzando un gestore globale delle eccezioni.

### Contribuire

I contributi sono benvenuti! Per favore, apri una issue o una pull request per suggerimenti, bug o miglioramenti.

### Licenza

Questo progetto è licenziato sotto la licenza MIT. Vedi il file `LICENSE` per i dettagli.

---

## English Version

This library provides a simple integration of [Firebase App Check](https://firebase.google.com/docs/app-check) for Spring Boot applications. It allows you to easily protect REST endpoints using a custom annotation.

### Features

- `@FirebaseAppCheck` annotation to protect endpoints
- Automatic verification of Firebase App Check token
- Easy integration with existing Spring Boot projects

### Installation

To use this library in your project, add the following configurations to your `pom.xml`:

1. Add the GitHub Packages repository:

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/pierfani/firebase-appcheck-spring</url>
    </repository>
</repositories>
```

2. Add the dependency:

```xml
<dependency>
    <groupId>it.pierfani</groupId>
    <artifactId>firebase-appcheck-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Configuration

Add the following properties to your `application.properties` or `application.yml`:

```properties
it.pierfani.firebaseappcheck.project-number=YOUR_FIREBASE_PROJECT_NUMBER
it.pierfani.firebaseappcheck.jwks-url=https://firebaseappcheck.googleapis.com/v1/jwks
it.pierfani.firebaseappcheck.enabled=true
```

### Usage

To protect an endpoint with Firebase App Check, simply add the `@FirebaseAppCheck` annotation to the controller method:

```java
import it.pierfani.firebaseappcheck.FirebaseAppCheck;

@RestController
public class ExampleController {

    @GetMapping("/protected-endpoint")
    @FirebaseAppCheck
    public String protectedEndpoint() {
        return "This endpoint is protected by Firebase App Check";
    }
}
```

### Error Handling

The library throws `FirebaseAppCheckException` in case of errors during token verification. You can handle this exception in your controller or using a global exception handler.

### Contributing

Contributions are welcome! Please open an issue or submit a pull request for any suggestions, bugs, or improvements.

### License

This project is licensed under the MIT License. See the `LICENSE` file for details.
