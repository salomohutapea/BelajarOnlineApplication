package com.example.belajaronlineapplication.models

class ChatMessage(val id:String, val text: String, val toId: String, val fromId: String, val timeStamp: Long) {
    constructor(): this("", "", "", "", -1)
}