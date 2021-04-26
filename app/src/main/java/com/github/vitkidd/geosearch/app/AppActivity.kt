package com.github.vitkidd.geosearch.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.vitkidd.geosearch.R
import com.github.vitkidd.geosearch.feature.MainFragment

class AppActivity : AppCompatActivity(R.layout.act_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_GeoSearch)
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, MainFragment())
            .commitNow()
    }
}
