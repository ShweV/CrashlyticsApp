package com.mindorks.example.crashlytics

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import com.mindorks.example.crashlytics.util.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var tracker: Tracker? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Google Analytics tracker
        val crashlyticsApp = CrashlyticsApp()
        tracker = crashlyticsApp.getDefaultTracker()
        tracker?.setScreenName("LoginActivity")
        tracker?.send(HitBuilders.ScreenViewBuilder().build())

        firebaseAnalytics = Firebase.analytics
        onLoginClick()
    }

    private fun onLoginClick() {
        login_button.setOnClickListener { // Firebase tracker
            tracker?.send(HitBuilders.EventBuilder()
                .setCategory("Login click")
                .setAction("Click")
                .build())

            val bundle :Bundle? = null
            bundle?.putString("LoginClick" , "ClickEvent")
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN , bundle)
            firebaseAnalytics.setAnalyticsCollectionEnabled(true)

            if(Util().isValidEmail(editText_UserName.text.toString()) &&
                Util().isValidPassword(editText_Password.text.toString())) {
                Toast.makeText(applicationContext, "Login Success!!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Login Failed!!", Toast.LENGTH_LONG).show()

                //Enabling crashlytics and adding custom key and log messages
                FirebaseCrashlytics.getInstance().setCustomKey("CrashlyticsApp", "hello crash!!")
                FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
                Crashlytics.getInstance().crash()
                Crashlytics.log("This is a crash")
                throw RuntimeException("This is a crash")
            }
        }
    }
}
