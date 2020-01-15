-keep class com.butterflymx.sdk.core.rest.** { *; }
-keep class com.butterflymx.sdk.call.rest.** { *; }

-dontwarn retrofit2.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-keep class moe.banana.jsonapi2.** { *; }
-keep class org.pjsip.** { *; }

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}