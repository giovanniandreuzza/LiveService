package it.giovanniandreuzza.liveservice

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.disposables.Disposable

abstract class LiveService : LifecycleService() {

    private lateinit var serviceDisposable: Disposable

    companion object {
        val dataFromActivity = MutableLiveData<Boolean>()
        val dataToActivity = MutableLiveData<Boolean>()
    }

    @LiveServiceAnnotation.StartResult
    abstract fun getStartValue(): Int

    override fun onCreate() {
        super.onCreate()
        dataFromActivity.observe(this, Observer {
            Log.d("SERVICE", "Data from activity")
        })

        serviceDisposable = executeInBackground(dataToActivity.postValue(true))
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        return getStartValue()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!serviceDisposable.isDisposed) {
            serviceDisposable.dispose()
        }
    }

}