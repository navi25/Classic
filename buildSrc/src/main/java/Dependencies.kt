

object Version{
    const val kotlin = "1.3.11"
    const val compileSdk = 28
    const val minSdk = 21
    const val targetSdk = 27
    const val androidx = "1.0.2"
    const val androidx_legacy = "1.0.0"
    const val constraint_layout = "2.0.0-alpha3"
    const val glide = "4.8.0"
    const val work = "1.0.0-beta05"
}

object Deps{

    val kotlin_stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Version.kotlin}"
    val app_compat = "androidx.appcompat:appcompat:${Version.androidx}"
    val app_compat_legacy = "androidx.legacy:legacy-support-v4:${Version.androidx_legacy}"
    val constraint_layout = "androidx.constraintlayout:constraintlayout:${Version.constraint_layout}"
    val work_manager = "android.arch.work:work-runtime-ktx:${Version.work}"
    val glide = "com.github.bumptech.glide:glide:${Version.glide}"


}

