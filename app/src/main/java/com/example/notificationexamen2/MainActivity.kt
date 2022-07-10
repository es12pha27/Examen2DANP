package com.example.notificationexamen2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful){
                    Log.e("FCM Notify", "ERROR REGISTRANDO TOKEN", task.exception)
                    return@OnCompleteListener
                }
                val token: String? = task.result
                Log.e("FCM Notify", token, task.exception)
                Toast.makeText(this, token, Toast.LENGTH_SHORT).show()

            })
        Firebase.messaging.subscribeToTopic("exam2")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d("MainActivity", msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            }




    }
}