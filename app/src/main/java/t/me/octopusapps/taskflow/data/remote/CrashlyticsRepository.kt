package t.me.octopusapps.taskflow.data.remote

import android.os.Build
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import t.me.octopusapps.taskflow.BuildConfig

class CrashlyticsRepository {

    private var firebaseCrashlytics: FirebaseCrashlytics = Firebase.crashlytics

    fun sendCrashlytics(throwable: Throwable) = firebaseCrashlytics.recordException(
        Throwable(
            "Error_information: \n" +
                    "Device: ${Build.BRAND + " " + Build.MODEL + " " + Build.MANUFACTURER}\n" +
                    "Version_Code: ${BuildConfig.VERSION_CODE}\n" +
                    "Device_SDK_version: ${Build.VERSION.SDK_INT}\n" +
                    "Message: ${throwable.message}\n"
        )
    )

}