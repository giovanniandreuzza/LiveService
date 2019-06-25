package it.giovanniandreuzza.liveservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang.SerializationUtils
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("ACTIVITY", "START ACTIVITY")
        LiveService.startService(applicationContext, MyService::class.java)

        val d = RxService.subscribeOnActivity(Consumer {

        })

        Log.d("ACTIVITY", "Send data")
        RxService.publishOnService(LiveResponse(CommandTest::getStatus.name, 9))
    }


}

