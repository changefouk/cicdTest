package com.appsynthassignment.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appsynthassignment.R
import com.appsynthassignment.presentation.profile.ProfileInformationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(frameLayout_main_contentContainer.id, ProfileInformationFragment.newInstance())
                .commit()
        }
    }
}