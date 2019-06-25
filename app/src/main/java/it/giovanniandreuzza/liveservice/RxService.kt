package it.giovanniandreuzza.liveservice

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


object RxService {

    private val behaviorSubjectService = BehaviorSubject.create<LiveResponse<Any>>()

    val schedulers = Schedulers.io()

    fun subscribeOnService(action: Consumer<LiveResponse<Any>>): Disposable {
        return behaviorSubjectService.subscribeOn(schedulers)
            .observeOn(schedulers)
            .subscribe(action)
    }

    fun <T> publishOnService(message: LiveResponse<T>) {
        behaviorSubjectService.onNext(message as LiveResponse<Any>)
    }

}
