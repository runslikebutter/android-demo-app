# Using the ButterflyMX SDK

The ButterflyMX SDK enables third-party partners to integrate ButterflyMX's Smart Intercom Solution in their app. You can create fully customized, branded apps that enable a voice or video chat with visitors for seamless guests access using ButterflyMX touch screen.

## Build requirements

- **Gradle JDK**: Use **JDK 17** for the Gradle build (Android Studio: *File → Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK*). With JDK 21, the build can fail during `compileDebugJavaWithJavac` (JdkImageTransform/jlink issue with compileSdk 35). JDK 17 avoids that and still meets Google Play’s target API 35 requirement.