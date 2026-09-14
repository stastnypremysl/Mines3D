# Mines3D

Minesweeper on an n×m×2 grid for Android. Plain Java, `com.android.support` 28 libraries (no AndroidX),
minSdk 19, targetSdk 36. The architecture is described in `README.md` ("Code description"): game state
lives in the static `LoadedGame`, `MinesView` draws everything on a `Canvas` and the activities are thin.

## Build and test
- There is no `local.properties`; export `ANDROID_HOME` (the SDK with `platforms;android-36`) before
  running Gradle.
- `./gradlew build` compiles, lints (report in `app/build/reports/`) and runs the JVM unit tests;
  `./gradlew testDebugUnitTest` runs the tests alone.
- Unit tests live in `app/src/test/java` and run against the stub `android.jar` with
  `unitTests.returnDefaultValues = true`; Android classes are mocked or spied with Mockito.

## Target SDK 36 decisions
- Edge-to-edge is mandatory since targetSdk 35 and `windowOptOutEdgeToEdgeEnforcement` is ignored at 36.
  `SystemBarsPadding.apply(this)` is called right after `setContentView` in every activity: it pads
  `android.R.id.content` by the system window insets and paints only the strips behind the transparent
  bars black, so the screens look exactly like the black status/navigation bars the app had before.
  The AppCompat sub-decor cannot do this itself because it is flagged "optional fits system windows".
  The compat system window insets include the display cutout, so a notch on the side in landscape is
  padded as well (verified on the API 36 emulator with the emulated cutout overlay).
- The theme keeps `android:navigationBarColor` black, which still tints the three-button navigation
  scrim on Android 15+ (the attribute is ignored for gesture navigation).
- Predictive back is on by default at targetSdk 36; the app never intercepts back, so nothing else is
  needed. Do not add `enableOnBackInvokedCallback="false"`.
