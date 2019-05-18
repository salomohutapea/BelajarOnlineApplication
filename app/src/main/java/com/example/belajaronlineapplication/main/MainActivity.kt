package com.example.belajaronlineapplication.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.example.belajaronlineapplication.R
import com.google.firebase.messaging.FirebaseMessaging
import com.pusher.pushnotifications.PushNotifications
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_akun -> {
                replaceFragment(AkunFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifikasi -> {
                replaceFragment((NotificationsFragment()))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_lainnya -> {
                replaceFragment(LainnyaFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        replaceFragment(HomeFragment())
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}
