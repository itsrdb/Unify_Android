package com.itsrdb.unify_android

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.Notification
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.google.firebase.database.FirebaseDatabase

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
//            for (msg in exTxtLines) {
//                Log.d("Line $i", (msg as String))
//                i += 1
//            }

            val ex2 = notif.extras.getCharSequence(Notification.FLAG_ONGOING_EVENT.toString()).toString()
            val ex3 = event.eventTime.toString()
            val ex4 = event.eventType.toString()
            Log.e("ex", ex2)
            Log.e("ex", ex3)
            Log.e("ex", ex4)

            val pm : PackageManager = applicationContext.packageManager
            try {
                val icon: Drawable = pm.getApplicationIcon(pkgName)
                val appName = pm.getApplicationInfo( this.getPackageName(), 0);
                //val icon: Drawable = getContext().getPackageManager().getApplicationIcon("com.example.testnotification")
                //imageView.setImageDrawable(icon)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            val n1 = MyNotification(title, pkgName, exTxt)
            //val dbhelper = DAONotif()

            sendData(n1)
        }
    }

    fun sendData(n1 : MyNotification){
        val db = FirebaseDatabase.getInstance()
        val dataNodeRef = db!!.getReference("notifs")
        var id = dataNodeRef.push().key
        dataNodeRef.child("lastMsg").setValue(n1)
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