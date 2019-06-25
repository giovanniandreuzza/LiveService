package it.giovanniandreuzza.liveservice

class MyService : LiveService() {

    override fun getStartValue(): Int = START_STICKY

    override fun initService() {
        // do init stuff here (ex. init bluetooth or what else)
    }

    override fun getCommandList() = object : CommandTest {

        override fun getStatus(x: Int): Int {
            return x * x
        }

    }

}