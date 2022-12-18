package com.example.aidlclient

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.Toast
import com.example.aidlserver.IAidlInterface

class MainActivity : AppCompatActivity() {

    lateinit var cnt: Button
    lateinit var cal: Button

    var iInputService: IAidlInterface? = null

    var mcon = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iInputService = IAidlInterface.Stub.asInterface(service)
            Toast.makeText(applicationContext, "Service is Connected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            iInputService = null
            Toast.makeText(applicationContext, "Service is Disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cnt = findViewById(R.id.connect)
        cal = findViewById(R.id.calculate)

        cnt.setOnClickListener {
            var i = Intent("com.example.server.AIDL")
            i.setPackage("com.example.aidlserver")
            bindService(i,mcon, BIND_AUTO_CREATE)

            cal.isEnabled = true
        }

        cal.setOnClickListener {
            var ans = iInputService!!.addition(15)
            Toast.makeText(applicationContext, ans.toString(), Toast.LENGTH_SHORT).show()
        }

    }
}