package it.giovanniandreuzza.liveservice

import android.util.Log

class MyService : LiveService() {

    override fun getStartValue(): Int = START_STICKY

    override fun getCommandList() = object : CommandTest {
        override fun c() {

        }

        override fun a() {
            Log.d("TEST", "Funziona")
        }

        override fun b() {

        }

    }

    override fun initService() {
        // do init stuff here (ex. init bluetooth or what else)
    }

    override fun <T> onCommandReceived(code: TEST, command: T) {
        // handle here your commands
        when (code) {
            TEST.A -> {
                Log.d("SERVICE", "Receive command $command")
                //sendResponse(TEST.A, "Ciao to B")
            }
            TEST.B -> {
                Log.d("SERVICE", "Receive command $command")
                sendResponse(TEST.A, 5)
            }
        }
    }

}