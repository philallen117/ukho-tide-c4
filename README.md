# Structurizr for Java starter

This is a simple starting point for using [Structurizr for Java](https://github.com/structurizr/java). To use it:

## 1. Clone the repo

```
git clone https://github.com/structurizr/java-starter.git
```

## 2. Set your workspace ID, API key and secret

Modify the constants at the top of [java-starter/src/main/java/com/mycompany/mysystem/Structurizr.java](https://github.com/structurizr/java-starter/blob/master/src/main/java/com/mycompany/mysystem/Structurizr.java) to reflect your own Structurizr workspace (this information is available on your dashboard, after signing in).

```java
private static final String API_KEY = "key";
private static final String API_SECRET = "secret";
private static final long WORKSPACE_ID = 1234;
```

## 3. Run the program

Finally, run the program, using your IDE or Gradle; for example:

```
cd java-starter
./gradlew run
```

For more information, please see [Structurizr for Java - Getting Started](https://github.com/structurizr/java/blob/master/docs/getting-started.md).