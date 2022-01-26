package com.itsrdb.unify_android

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Notification
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.google.firebase.ktx.Firebase

class AccessibleActivity : AccessibilityService() {
    override fun onInterrupt() {}
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(event!!.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED){
            val notif : Notification = event.parcelableData as Notification

            val title = notif.extras.getCharSequence(Notification.EXTRA_TITLE).toString()
            val exTxt = notif.extras.getCharSequence(Notification.EXTRA_TEXT).toString()
            val exTxtLines = notif.extras.getCharSequence(Notification.EXTRA_TEXT_LINES).toString()
            val pkgName = event.packageName.toString()
            Log.e("title", title)
            Log.e("pkg", pkgName)
            Log.e("extxt", exTxt)
            var i = 0
            for (msg in exTxtLines) {
                Log.d("Line $i", (msg as String))
                i += 1
            }

            val ex2 = notif.extras.getCharSequence(Notification.FLAG_ONGOING_EVENT.toString()).toString()
            val ex3 = event.eventTime.toString()
            val ex4 = event.eventType.toString()
            Log.e("ex", ex2)
            Log.e("ex", ex3)
            Log.e("ex", ex4)

            Firebase
        }
    }

    override fun onServiceConnected() {
        val info = AccessibilityServiceInfo()
        info.apply {
            eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
            packageNames = null
            feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK
            notificationTimeout = 100
        }
        this.serviceInfo = info
    }
}