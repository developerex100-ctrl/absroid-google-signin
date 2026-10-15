# Absroid Google Sign-In demo

This repo contains a minimal Android app (Kotlin) that demonstrates Google Sign-In with an account chooser. The app forces the Google account picker to appear by calling `googleSignInClient.signOut()` before launching the sign-in intent.

How to use

- The repository includes a GitHub Actions workflow that builds a debug APK on push.
- After the workflow completes, download the artifact `app-debug-apk` and install the APK on your Android device (enable "Install from unknown sources").

Notes

- The debug APK is signed with a debug key. For production or Firebase integration you will need to configure OAuth client IDs and SHA-1 fingerprints.
