# Keep Jakarta Mail classes
-keep class jakarta.mail.** { *; }
-keep class com.sun.mail.** { *; }
-keep class org.eclipse.angus.** { *; }

# Prevent warnings related to Jakarta Mail and Angus Activation
-dontwarn jakarta.mail.**
-dontwarn com.sun.mail.**
-dontwarn org.eclipse.angus.**

# Keep class members for reflection support
-keepclassmembers class jakarta.mail.** { *; }
-keepclassmembers class com.sun.mail.** { *; }
-keepclassmembers class org.eclipse.angus.** { *; }

# If you are using reflection in these libraries, prevent stripping of those methods
-keepattributes Signature
-keepattributes Exceptions

# Keep methods annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Prevent obfuscation of Parcelable classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
