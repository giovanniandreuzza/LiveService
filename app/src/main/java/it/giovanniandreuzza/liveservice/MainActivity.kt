package it.giovanniandreuzza.liveservice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer


class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("ACTIVITY", "START ACTIVITY")
        LiveService.startService(applicationContext, MyService::class.java)

        Log.d("ACTIVITY", "Send data")
        compositeDisposable.addAll(
            CommandTest::getStatus.params(1).observe(Consumer {
                Log.d("Test", "A $it")
            }), CommandTest::ciao.params().observe(Consumer {
                Log.d("Test", "B $it")
            }), CommandTest::getStatus.params(3).observe(Consumer {
                Log.d("Test", "C $it")
            }), CommandTest::ciao.params().observe(Consumer {
                Log.d("Test", "D $it")
            })
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
