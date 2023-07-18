
-dontwarn retrofit2.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
# ButterflyMX
-keep class com.butterflymx.sdk.core.rest.** { *; }
-keep class com.butterflymx.sdk.call.rest.** { *; }
-keep class moe.banana.jsonapi2.** { *; }
# Needed for Twilio
-keep class tvi.webrtc.** { *; }
-keep class com.twilio.video.** { *; }
-keepattributes InnerClasses

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}