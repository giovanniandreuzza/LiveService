package it.giovanniandreuzza.liveservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("ACTIVITY", "START ACTIVITY")
        LiveService.startService(applicationContext, MyService::class.java)

        Log.d("ACTIVITY", "Send data")
        val disposable = CommandTest::getStatus.params(4).observe(Consumer {
            Log.d("Test", "$it")
        })

        val disposable1 = CommandTest::ciao.params().observe(Consumer {
            Log.d("Test", "$it")
        })
    }
}
