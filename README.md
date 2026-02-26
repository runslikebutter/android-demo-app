# ButterflyMX Android SDK — Demo App

This demo app shows how to integrate the ButterflyMX Smart Intercom SDK into an Android app. It covers authentication, door/unit selection, and video call handling.

---

## Requirements

| Tool | Version |
|------|---------|
| Android Studio | Hedgehog or newer |
| Gradle JDK | **17** (not 21 — see note below) |
| Min SDK | 26 |
| Target SDK | 35 |

> **JDK note:** Use JDK 17 for the Gradle build (*File → Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK*). JDK 21 can fail during `compileDebugJavaWithJavac` with compileSdk 35.

---

## Setup

### 1. Add SDK credentials

In `build.gradle` (root), replace the placeholders with your GitHub credentials to access the ButterflyMX Maven registry:

```groovy
credentials {
    username = "YOUR_GITHUB_USERNAME"
    password = "YOUR_GITHUB_ACCESS_TOKEN"
}
```

The token needs `read:packages` scope on GitHub.

### 2. Add your client IDs

In `app/src/main/res/values/strings.xml`, fill in the client IDs you received from ButterflyMX:

```xml
<string name="sandbox_client_id">YOUR_SANDBOX_CLIENT_ID</string>
<string name="staging_client_id">YOUR_STAGING_CLIENT_ID</string>
<string name="prod_client_id">YOUR_PROD_CLIENT_ID</string>
```

### 3. Set your redirect URI

The redirect URI must match what is registered in the ButterflyMX system for your app:

```xml
<!-- strings.xml -->
<string name="redirect_uri">yourscheme://callback</string>
```

```groovy
// app/build.gradle — must match the scheme part of the redirect URI
manifestPlaceholders = ['appAuthRedirectScheme': 'yourscheme']
```

---

## Authentication

The app uses **PKCE OAuth** (recommended) — no client secret required. This is controlled by a flag in `ButterflyMxConfigBuilder.kt`:

```kotlin
// Set to false to use the legacy client-secret flow instead
private const val USE_PKCE_FLOW = true
```

**PKCE flow** — only a client ID is needed:
```kotlin
BMXConfig.Builder()
    .setClientID(clientId)
    .setEndpointType(endpointType)
    .build()
```

**Legacy flow** — requires both client ID and secret:
```kotlin
BMXConfig.Builder()
    .setAuthConfig(clientId, secretId)
    .setEndpointType(endpointType)
    .build()
```

---

## SDK Dependencies

```groovy
implementation 'com.butterflymx:sdk-core:1.1.26'
implementation 'com.butterflymx:sdk-call:1.2.2'
```

---

## Project Structure

```
app/src/main/java/.../
├── App.kt                          # Application class, SDK init
├── signinsignup/login/
│   └── LoginFragment.kt            # OAuth login, handles redirect callback
├── utils/
│   ├── ButterflyMxConfigBuilder.kt # Builds BMXConfig (PKCE or legacy)
│   └── ButterflyMxLogger.kt        # Custom logger implementation
└── main/                           # Post-login screens (units, panels, calls)
```
