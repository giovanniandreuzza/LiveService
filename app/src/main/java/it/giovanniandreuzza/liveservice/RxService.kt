package it.giovanniandreuzza.liveservice

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject


object RxService {

    private val replaySubjectService = ReplaySubject.create<LiveResponse<Any>>()

    val schedulers = Schedulers.io()

    fun subscribeOnService(action: Consumer<LiveResponse<Any>>): Disposable = replaySubjectService
        .subscribeOn(schedulers)
        .observeOn(schedulers)
        .subscribe(action)


    fun <T> publishOnService(message: LiveResponse<T>) = replaySubjectService.onNext(message as LiveResponse<Any>)

}
