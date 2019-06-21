package it.giovanniandreuzza.liveservice

import android.app.Service

class MyService : LiveService() {


    override fun getStartValue(): Int = Service.START_STICKY


}