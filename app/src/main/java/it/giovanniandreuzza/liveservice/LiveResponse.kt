package it.giovanniandreuzza.liveservice

import io.reactivex.subjects.BehaviorSubject

data class LiveResponse<T>(val responseCommand: String, val subject: BehaviorSubject<T>, val responseValue: Any?)