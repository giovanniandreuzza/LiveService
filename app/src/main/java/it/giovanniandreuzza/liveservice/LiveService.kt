package it.giovanniandreuzza.liveservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.apache.commons.lang.SerializationUtils
import java.io.Serializable

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

    abstract fun <T> onCommandReceived(code: TEST, command: T)

    override fun onCreate() {
        super.onCreate()

        serviceDisposable = RxService.subscribeOnService(Consumer {
            Log.d("SERVICE", "On next received")

            val objDeserialize = SerializationUtils.deserialize(it.responseValue)

            if (objDeserialize::class == it.responseCommand.clazz) {

                val a = object : CommandTest {
                    override fun a() {

                    }

                    override fun b() {

                    }

                    override fun c() {

                    }
                }


                Log.d("TEST", CommandTest::c.name + " ${getCommandList()::class.java}  ${a::class.java}")
                (getCommandList().cast(a::class.java)).a()

                onCommandReceived(
                    it.responseCommand,
                    objDeserialize
                )
            } else {
                throw IllegalArgumentException("\"responseValue\" type doesn't match with the \"responseCommand\" one. It should be \"${it.responseCommand.clazz}\" type")
            }
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

    fun <T> Any.cast(c: Class<T>): T {
        return this as T
    }

    fun <T : Serializable> sendResponse(command: TEST, response: T) {
        RxService.publishOnActivity(LiveResponse(command, SerializationUtils.serialize(response)))
    }

}