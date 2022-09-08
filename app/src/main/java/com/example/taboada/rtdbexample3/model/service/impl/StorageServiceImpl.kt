package com.example.taboada.rtdbexample3.model.service.impl

import android.util.Log
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.ChatUser
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(): StorageService {
    private var myChatsReference: DatabaseReference? = null
    private var myChatsValueEventListener: ValueEventListener? = null
    private var userEventListener: ValueEventListener? = null

//    override fun addListener(
//        userId: String,
//        onNewMessage: (Message) -> Unit,
//        onDeletedMessage: (String) -> Unit
//    ) {
//        chatReference = Firebase.database.reference.child("chats")
//        childEventListener = object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                val message = Message(
//                    id = snapshot.key!!,
//                    content = snapshot.child("content").value.toString()
//                )
//                onNewMessage(message)
//            }
//            override fun onChildRemoved(snapshot: DataSnapshot) {
////                Log.w("DATABASE", "Message deleted", snapshot.toString())
//                val message = Message(
//                    content = snapshot.child("content").toString(),
//                    id = snapshot.key!!,
//                )
//                onDeletedMessage(snapshot.key!!)
//            }
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//        }
//        chatReference?.addChildEventListener(childEventListener!!)
//    }

    override fun removeListener() {
        myChatsReference?.removeEventListener(myChatsValueEventListener!!)
    }

    override fun saveMessage(message: Message, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteMessage(id: String) {
        Firebase.database.reference.child("chats").child(id).removeValue()

    }

    override fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }


    override fun getAllMyChats(userId: String, onChatChanged: (ChatDetails) -> Unit) {
        myChatsReference = Firebase.database.reference.child("myChats").child(userId)
        myChatsValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->
                    var newMemebers = mutableMapOf<String, String>()

                    dataSnapshot.children.forEach { dataSnapshot ->
                        newMemebers[dataSnapshot.key!!] = dataSnapshot.value.toString()
                    }
                    val chat = ChatDetails(
                        chatID = dataSnapshot.key!!,
                        members = newMemebers
                    )
                   onChatChanged(chat)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", error.message)
            }

        }
        myChatsReference!!.addListenerForSingleValueEvent(myChatsValueEventListener!!)
    }


    override fun getUsers(onUsersChanged: (ChatUser) -> Unit) {
        userEventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->
                    val user = ChatUser(
                        userID = dataSnapshot.key!!,
                        displayName = dataSnapshot.child("displayName").value as String,
                        email = dataSnapshot.child("email").value as String
                    )
                    onUsersChanged(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("STORAGE_SERVICE", error.message)
            }
        }
        Firebase.database.reference
            .child("users").addListenerForSingleValueEvent(userEventListener!!)
    }

    override fun removeUserListener() {
        Firebase.database.reference.child("users").removeEventListener(userEventListener!!)
    }

    override fun addNewChat(value: ChatDetails, onSuccess: () -> Unit) {
        val database = Firebase.database.reference
        val key = database.child("chats").push().key
        if ( key == null ) {
            Log.w("STORAGE_SERVICE", "Couldn't get push key for chats")
        }

        val chat = mapOf<String,Any>(
            "type" to if(value.isPublic) "public" else "private",
            "members" to value.members
        )

        var childUpdates = (value.members.keys.map { "myChats/$it/$key" to chat }).toMap<String,Any>()
        childUpdates+= ("chats/$key" to chat)

        Firebase.database.reference.updateChildren(childUpdates)
            .addOnCompleteListener { onSuccess() }
            .addOnFailureListener { Log.w("STORAGE_SERVICE", "Error getting data", it) }
    }
}