package it.giovanniandreuzza.liveservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("ACTIVITY", "START ACTIVITY")
        LiveService.startService(applicationContext, MyService::class.java)

        val d = RxService.getActivitySubject()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({
                Log.d("ACTIVITY", "$it received")
            }, {

            })

        Log.d("ACTIVITY", "Send data")
        RxService.getServiceSubject().onNext(1)
    }
}
