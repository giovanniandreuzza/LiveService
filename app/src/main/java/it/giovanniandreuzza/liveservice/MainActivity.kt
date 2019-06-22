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
            when (it.responseCommand) {
                TEST.A -> {
                    Log.d("ACTIVITY", "${SerializationUtils.deserialize(it.responseValue)} received")
                    RxService.publishOnService(LiveResponse(TEST.A, SerializationUtils.serialize(0)))
                    RxService.publishOnService(LiveResponse(TEST.A, SerializationUtils.serialize(1)))
                    RxService.publishOnService(LiveResponse(TEST.A, SerializationUtils.serialize(2)))
                    RxService.publishOnService(LiveResponse(TEST.A, SerializationUtils.serialize(3)))
                }
                TEST.B -> {
                    Log.d("ACTIVITY", "${SerializationUtils.deserialize(it.responseValue)} received")
                }
            }
        })

        Log.d("ACTIVITY", "Send data")
        RxService.publishOnService(LiveResponse(TEST.B, SerializationUtils.serialize("a")))
    }


}

