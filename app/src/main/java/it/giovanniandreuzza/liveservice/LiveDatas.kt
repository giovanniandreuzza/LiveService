package it.giovanniandreuzza.liveservice

import io.reactivex.subjects.ReplaySubject

data class LiveResponse<T>(val responseCommand: String, val subject: ReplaySubject<T>, val responseValue: Any?)

data class ParametersData(val clazz: Class<*>, val command: String, val data: Any?)