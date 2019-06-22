package it.giovanniandreuzza.liveservice

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class LiveService : LifecycleService() {

    private lateinit var serviceDisposable: Disposable

    companion object {
        fun startService(applicationContext: Context, service: Class<out LiveService>) {
            val intent = Intent(applicationContext, service)
            applicationContext.startService(intent)
        }

        fun stopService(applicationContext: Context, service: Class<out LiveService>) {
            val intent = Intent(applicationContext, service)
            applicationContext.stopService(intent)
        }

    }

    @LiveServiceAnnotation.StartResult
    abstract fun getStartValue(): Int

    abstract fun initService()

    abstract fun <T> onCommandReceived(command: T)

    override fun onCreate() {
        super.onCreate()

        Log.d("SERVICE", "CREATED")

        serviceDisposable =
            RxService.getServiceSubject()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    Log.d("SERVICE", "$it received")
                    RxService.getActivitySubject().onNext("Ciao")
                }, {

                })

        initService()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return getStartValue()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SERVICE", "Service stopped")
        if (!serviceDisposable.isDisposed) {
            serviceDisposable.dispose()
        }
    }

}