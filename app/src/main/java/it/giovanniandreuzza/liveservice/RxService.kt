package it.giovanniandreuzza.liveservice

import io.reactivex.subjects.BehaviorSubject

object RxService {

    private val behaviorSubjectActivity: BehaviorSubject<Any> = BehaviorSubject.create()

    fun getActivitySubject(): BehaviorSubject<Any> = behaviorSubjectActivity

    private val behaviorSubjectService: BehaviorSubject<Any> = BehaviorSubject.create()

    fun getServiceSubject(): BehaviorSubject<Any> = behaviorSubjectService

}