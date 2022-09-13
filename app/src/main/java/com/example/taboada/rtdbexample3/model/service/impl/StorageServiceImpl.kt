package com.example.taboada.rtdbexample3.model.service.impl

import android.util.Log
import com.example.taboada.rtdbexample3.model.Message
import com.example.taboada.rtdbexample3.model.service.StorageService
import com.example.taboada.rtdbexample3.model.ChatDetails
import com.example.taboada.rtdbexample3.model.ChatUser
import com.example.taboada.rtdbexample3.screens.sign_up.SignUpUiState
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class StorageServiceImpl @Inject constructor() : StorageService {
    private var database = Firebase.database
    private var myChatsReference: DatabaseReference? = null
    private var myChatsValueEventListener: ValueEventListener? = null
    private var userEventListener: ValueEventListener? = null
    private var myChatsConversationChildEventListenersMap: MutableMap<String, ChildEventListener> =
        mutableMapOf()

    private fun dataSnapshot2Message(currentUser: String, dataSnapshot: DataSnapshot): Message {
        val userId = dataSnapshot.child("userId").value as String

        return Message(
            id = dataSnapshot.key!!,
            content = dataSnapshot.child("content").value as String,
            priority = dataSnapshot.child("priority").value as String,
            timestamp = dataSnapshot.child("timestamp").value as Long,
            flag = if (userId == currentUser) "me" else "notMe",
            userId = userId,
        )
    }

    override fun useEmulator(host: String, port: Int) {
        database.useEmulator(host, port)
    }

    override fun removeListener() {
        myChatsReference?.removeEventListener(myChatsValueEventListener!!)
    }

    override fun saveMessage(message: Message, onResult: (Throwable?) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun deleteMessage(id: String) {
        database.reference.child("chats").child(id).removeValue()

    }

    override fun updateUserId(
        oldUserId: String,
        newUserId: String,
        onResult: (Throwable?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllMyChats(userId: String, onChatChanged: (ChatDetails) -> Unit) {
        myChatsReference = database.reference.child("myChats").child(userId)
        myChatsValueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val chatDetails = ChatDetails(
                        members = (it.child("members").children
                            .map { m -> (m.key!! to m.value as String) })
                            .toMap(),
                        isPublic = it.child("type").value as String == "public",
                        chatID = it.key!!
                    )
                    onChatChanged(chatDetails)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ERROR", error.message)
            }

        }
        myChatsReference!!.addListenerForSingleValueEvent(myChatsValueEventListener!!)
    }

    override fun getUsers(onUsersChanged: (ChatUser) -> Unit) {
        userEventListener = object : ValueEventListener {
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
        database.reference.child("users").addListenerForSingleValueEvent(userEventListener!!)
    }

    override fun removeUserListener() {
        database.reference.child("users").removeEventListener(userEventListener!!)
    }

    override fun addNewChat(value: ChatDetails, onSuccess: () -> Unit) {
        val key = database.reference.child("chats").push()
//        Log.w("TAG", key.key!!)
        if (key == null) {
            Log.w("STORAGE_SERVICE", "Couldn't get push key for chats")
        }

        val chat = mapOf<String, Any>(
            "type" to if (value.isPublic) "public" else "private",
            "members" to value.members
        )

        var childUpdates =
            (value.members.keys.map { "/myChats/$it/${key.key!!}" to chat }).toMap<String, Any>()
        childUpdates += ("/chats/${key.key!!}" to chat)

        database.reference.updateChildren(childUpdates)
            .addOnCompleteListener { onSuccess() }
            .addOnFailureListener { Log.w("STORAGE_SERVICE", "Error getting data", it) }
    }

    override fun getConversation(
        chat: String,
        currentUser: String,
        onMessageAdded: (Message) -> Unit,
        onMessageUpdated: (Message) -> Unit,
        onMessageDeleted: (Message) -> Unit
    ) {
        val chatReference = database.reference.child("chats")
            .child(chat)
            .child("messages")

        myChatsConversationChildEventListenersMap[chat] = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                onMessageAdded(dataSnapshot2Message(currentUser, dataSnapshot = snapshot))
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                onMessageUpdated(dataSnapshot2Message(currentUser, snapshot))
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                onMessageDeleted(dataSnapshot2Message(currentUser, snapshot))
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("STORAGE_SERVICE", error.message)
            }

        }
        myChatsConversationChildEventListenersMap[chat]?.let {
            chatReference
                .limitToFirst(20)
                .orderByChild("timestamp")
                .addChildEventListener(it)
        }

    }

    override fun sendMessage(chat: String, userId: String, msgContent: String) {
        val chat = database.reference
            .child("chats")
            .child(chat)
            .child("messages")
            .push()
        val messageSnapshot = hashMapOf<String, Any>(
            "content" to msgContent,
            "priority" to "C",
            "timestamp" to ServerValue.TIMESTAMP,
            "userId" to userId
        )
        chat.setValue(messageSnapshot)
    }

    override fun removeChatListener(chat: String) {
        myChatsConversationChildEventListenersMap[chat]?.let {
            database.reference.child("chats").child(chat)
                .removeEventListener(it)
        }
    }

    override fun updateUserProfile(userId: String, user: SignUpUiState) {
        Log.w("STORAGE_SERVICE", user.displayName)
        database.reference.child("users").child(userId).child("displayName")
            .setValue(user.displayName)
    }
}