package com.example.taboada.rtdbexample3.model.service.impl

import android.util.Log
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(): StorageService {
    private var chatReference: DatabaseReference? = null
    private var childEventListener: ChildEventListener? = null

    override fun addListener(
        userId: String,
        onNewMessage: (Message) -> Unit,
        onDeletedMessage: (String, Message) -> Unit
    ) {
        chatReference = Firebase.database.reference.child("chats")
        childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = Message(
                    id = snapshot.key!!,
                    content = snapshot.child("content").value.toString()
                )
                onNewMessage(message)
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
//                Log.w("DATABASE", "Message deleted", snapshot.toString())
                val message = Message(
                    content = snapshot.child("content").toString(),
                    id = snapshot.key!!,
                )
                onDeletedMessage(snapshot.key!!, message)
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
        }
        chatReference?.addChildEventListener(childEventListener!!)
    }

    override fun removeListener() {
        chatReference?.removeEventListener(childEventListener!!)
    }

    override fun saveMessage(message: Message, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteMessage(id: String) {
        Firebase.database.reference.child("chats").child(id).removeValue()

    }
}