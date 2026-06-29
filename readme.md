

# GitHub Activity

Small Java utility that fetches and prints recent public GitHub events for a user.

## roadmap.sh
https://roadmap.sh/projects/github-user-activity
## Project Overview

This project queries the GitHub Events API for a given username and prints a compact summary of each event (event type and repository name). It demonstrates a minimal Java app using `HttpURLConnection` and `Gson` to deserialize JSON into Java records.

Key components:
- `src/main/GithubActivity.java` — application entry point.
- `src/main/service/ActivityService.java` — high-level service that requests events for a username.
- `src/main/network/RestClient.java` — performs HTTP GET and parses JSON using `Gson`.
- `src/main/model/Event.java`, `src/main/model/Repo.java` — simple data records that map the GitHub JSON.

## Repository structure

- `src/` — Java source files.
- `bin/` — compiled classes (output directory).
- `lib/` — external libraries (contains `gson` jar expected).
- `data/` — sample JSON files (offline examples).

## Prerequisites

- Java 11 or newer (records require Java 16+, but Java 17+ is recommended).
- `gson` library in `lib/` (project already expects `lib/*`).

## Build & Run

From the repository root, compile then run. Examples below assume the current working directory is the project root.

Windows (PowerShell or CMD):

```powershell
javac -d bin -cp "lib/*" src\main\GithubActivity.java src\main\model\*.java src\main\network\*.java src\main\service\*.java

java -cp "bin;lib/*" main.GithubActivity <github-username>
```

Linux / macOS:

```bash
javac -d bin -cp 'lib/*' src/main/GithubActivity.java src/main/model/*.java src/main/network/*.java src/main/service/*.java

java -cp 'bin:lib/*' main.GithubActivity <github-username>
```

Example (replace `JuanFernandoMartinez` with any username):

```powershell
java -cp "bin;lib/*" main.GithubActivity JuanFernandoMartinez
```

If you prefer a quick helper, `runner.cmd` can be used on Windows to store or run the same command.

## Code snippets

Main entry (`src/main/GithubActivity.java`):

```java
package main;

import main.service.ActivityService;

public class GithubActivity {
	public static void main(String[] args) {
		ActivityService service = new ActivityService();
		var events = service.getEventsForUser(args[0]);
		events.forEach(x -> System.out.printf("%s to %s\n", x.type(), x.repo().name()));
	}
}
```

Service usage (`ActivityService`):

```java
ActivityService service = new ActivityService();
List<Event> events = service.getEventsForUser("someusername");
```

Model records (`Event` and `Repo`):

```java
package main.model;

public record Event(long id, String type, Repo repo) {}

public record Repo(long id, String name, String url) {}
```

HTTP client (`RestClient`) — key part that fetches and parses JSON with Gson:

```java
Gson gson = new Gson();
Type eventListType = new TypeToken<List<Event>>() {}.getType();
List<Event> events = gson.fromJson(jsonString, eventListType);
```

## Sample output

When run, the app prints lines like:

```
PushEvent to some-repo
CreateEvent to other-repo
```

## Notes & Troubleshooting

- Ensure `lib/gson-*.jar` exists; otherwise add Gson to the classpath.
- If GitHub rate limits the API, try using a small sample file from `data/` or add authentication headers in `RestClient`.
- The code uses Java records — compile/run with Java 16+ (Java 17 recommended).

## Files referenced

- [src/main/GithubActivity.java](src/main/GithubActivity.java#L1-L200)
- [src/main/service/ActivityService.java](src/main/service/ActivityService.java#L1-L200)
- [src/main/network/RestClient.java](src/main/network/RestClient.java#L1-L200)
- [src/main/model/Event.java](src/main/model/Event.java#L1-L50)
- [src/main/model/Repo.java](src/main/model/Repo.java#L1-L50)

---

If you'd like, I can also:
- add a Gradle or Maven build file, or
- include optional authenticated requests to increase GitHub rate limits.
