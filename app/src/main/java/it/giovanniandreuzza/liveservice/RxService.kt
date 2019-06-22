package it.giovanniandreuzza.liveservice

import android.util.Log
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject


object RxService {

    private val behaviorSubjectActivity = BehaviorSubject.create<LiveResponse>()

    fun subscribeOnActivity(action: Consumer<LiveResponse>): Disposable =
        behaviorSubjectActivity.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(action)

    fun publishOnActivity(message: LiveResponse) = behaviorSubjectActivity.onNext(message)


    private val behaviorSubjectService = BehaviorSubject.create<LiveResponse>()

    fun subscribeOnService(action: Consumer<LiveResponse>): Disposable =
        behaviorSubjectService.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(action, Consumer {
                Log.d("ERROR", "Error -> ${it.message}")
            })

    fun publishOnService(message: LiveResponse) {
        behaviorSubjectService.onNext(message)
    }
}