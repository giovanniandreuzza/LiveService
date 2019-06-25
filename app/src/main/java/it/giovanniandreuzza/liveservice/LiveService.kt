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
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.jvm.kotlinFunction
import kotlin.reflect.jvm.reflect

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

            // println("${create(CommandTest::class.java).getStatus(8)}")

            getCommandList()::class.java.methods.forEach { method ->
                if (method.name == it.responseCommand) {
                    Log.d("SERVICE", "${method.kotlinFunction?.call(getCommandList(), it.responseValue)}")
                }
            }


            //  println("${create(CommandTest::class.java).getStatus(8)}")


            /*val objDeserialize = SerializationUtils.deserialize(it.responseValue)

            if (objDeserialize::class == it.responseCommand.clazz) {

                getCommandList().javaClass.methods.forEach { method ->
                    if (method.name == CommandTest::a.name) {
                        method.kotlinFunction?.call(getCommandList())
                    }
                }

                Log.d("TEST", "${getCommandList()::class.java}  ${a::class.java}")
                (getCommandList().cast(a::class.java)).a()

                onCommandReceived(
                    it.responseCommand,
                    objDeserialize
                )
            } else {
                throw IllegalArgumentException("\"responseValue\" type doesn't match with the \"responseCommand\" one. It should be \"${it.responseCommand.clazz}\" type")
            }*/
        })

        initService()
    }

    fun <T> create(service: Class<T>): T =
        Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service), object : InvocationHandler {
            private val emptyArgs = arrayOfNulls<Any>(0)

            @Throws(Throwable::class)
            override operator fun invoke(proxy: Any, method: Method, vararg args: Any?): T {
                return method.kotlinFunction?.call(getCommandList(), args[0] ?: emptyArgs) as T
            }
        }) as T


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

    fun sendResponse(command: String, response: Any) {
        RxService.publishOnActivity(LiveResponse(command, response))
    }

}
