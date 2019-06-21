package it.giovanniandreuzza.liveservice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import it.giovanniandreuzza.liveservice.LiveService.Companion.dataFromActivity
import it.giovanniandreuzza.liveservice.LiveService.Companion.dataToActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = Intent(applicationContext, MyService::class.java)
        applicationContext.startService(service)

        dataToActivity.observe(this, Observer {
            Log.d("ACTIVITY", "Data from service")
            executeInBackground(dataFromActivity.postValue(true))
        })
    }
}
