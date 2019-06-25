package it.giovanniandreuzza.liveservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
import kotlin.reflect.jvm.kotlinFunction

abstract class LiveService : Service() {

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

    abstract fun getCommandList(): Any

    abstract fun initService()

    override fun onCreate() {
        super.onCreate()

        serviceDisposable = RxService.subscribeOnService(Consumer {
            Log.d("SERVICE", "On next received")
            prepareRequest(it.responseCommand, it.subject, it.responseValue)
        })

        initService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
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

    private fun prepareRequest(command: String, behaviorSubject: BehaviorSubject<Any>, data: Any?) {
        getCommandList().javaClass.methods.forEach { method ->
            var commandString = command.replaceBefore(method.name, "")
            commandString = commandString.replaceAfter(method.name, "")

            if (method.name == commandString) {
                data?.let {
                    behaviorSubject.onNext(method.kotlinFunction?.call(getCommandList(), it)!!)
                } ?: behaviorSubject.onNext(method.kotlinFunction?.call(getCommandList())!!)
            }
        }
    }

}
