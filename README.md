# Car Boot Sound

A small Android app that plays a sound automatically when the device's screen
turns on, and starts itself again after the system boots (intended for use on
a car's Android head unit).

## Project structure

```
CarBootSound/
├── app/                        ← your original app module (unchanged)
│   ├── build.gradle
│   └── src/main/...
├── gradle/wrapper/              ← Gradle Wrapper (lets anyone build without installing Gradle)
├── gradlew / gradlew.bat        ← wrapper launcher scripts (Linux/Mac / Windows)
├── build.gradle                 ← root/top-level Gradle build file
├── settings.gradle               ← declares repositories + includes the app module
├── gradle.properties
├── .gitignore
└── .github/workflows/android-build.yml   ← GitHub Actions CI: builds an APK on every push
```

Only the `app/` folder is yours from before — everything else is the
scaffolding a runnable Android Gradle project needs (this is exactly what
Android Studio generates for you when you click "New Project").

## Build settings used

| Tool | Version |
|---|---|
| Android Gradle Plugin (AGP) | 8.9.2 |
| Gradle | 8.11.1 (via wrapper) |
| JDK | 17 |
| compileSdk / targetSdk | 34 |
| minSdk | 21 |

These were picked to match your `app/build.gradle` (`compileSdk 34`,
`minSdk 21`) and are a current, stable combination as of mid-2026 — one step
behind the newer AGP 9.x line, which requires a mandatory DSL/Gradle 9
migration that isn't necessary for a project this size.

## Building locally

You need JDK 17 and the Android SDK installed (or just open the folder in
Android Studio, which handles both).

```bash
# Debug APK (unsigned, installable directly on a device with "Unknown sources" allowed)
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk

# Release APK (unsigned — you'd need to sign it before it can be installed/published)
./gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release-unsigned.apk
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## Pushing to GitHub

```bash
git init
git add .
git commit -m "Initial commit: Car Boot Sound Android project"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

## Getting the APK from GitHub Actions

The workflow at `.github/workflows/android-build.yml` runs automatically on
every push to `main` (and on pull requests, and manually via the "Run
workflow" button under the Actions tab). It:

1. Checks out the repo
2. Sets up JDK 17 and the Android SDK
3. Runs `./gradlew assembleDebug`
4. Uploads the resulting APK as a build artifact

To download it: open your repo on GitHub → **Actions** tab → click the latest
successful run → scroll to **Artifacts** → download `app-debug-apk`. It's a
zip containing `app-debug.apk`, which you can copy to your phone/car unit and
install (you'll need to allow installs from unknown sources).

If you'd rather have a signed **release** APK produced by CI (needed if you
ever plan to publish it or want it to auto-update cleanly), let me know — that
requires generating a keystore and adding it as a GitHub Actions secret, which
I can walk you through.
