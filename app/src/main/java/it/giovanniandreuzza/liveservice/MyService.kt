package it.giovanniandreuzza.liveservice

import android.app.Service
import android.util.Log

class MyService : LiveService() {

    override fun getStartValue(): Int = Service.START_STICKY

    override fun initService() {
        // do init stuff here (ex. init bluetooth or what else)
        onCommandReceived(0)
    }

    override fun <T> onCommandReceived(command: T) {
        // handle here your commands
        when(command) {
            is Int -> {
                Log.d("SERVICE", "Receive command $command")
            }
            else -> {

            }
        }
    }

}