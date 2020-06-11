package com.mindorks.example.crashlytics

import android.app.Application
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker
import com.google.firebase.FirebaseApp

class CrashlyticsApp : Application() {
    private var googleAnalytics : GoogleAnalytics? = null
    private var tracker : Tracker? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        googleAnalytics = GoogleAnalytics.getInstance(this)
    }

    @Synchronized fun getDefaultTracker(): Tracker? {
        if(tracker == null)
            tracker = googleAnalytics?.newTracker(R.xml.global_tracker)
        return tracker
    }
}