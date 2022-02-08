package com.itsrdb.unify_android

import com.google.firebase.database.*

class DAONotif {

    val db = FirebaseDatabase.getInstance()


    fun sendNoti(noti : MyNotification){
        val dataNodeRef = db!!.getReference("notifs")
//        dataNodeRef!!.addValueEventListener(object : ValueEventListener {
//            val dataNodeRef = db!!.getReference("data")
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) noti = "Value is : " + dataSnapshot.value
//            }
//            override fun onCancelled(databaseError: DatabaseError) {}
//        })
        dataNodeRef.setValue(noti)
    }

}