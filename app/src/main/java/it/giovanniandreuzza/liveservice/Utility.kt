package it.giovanniandreuzza.liveservice

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.ReplaySubject
import it.giovanniandreuzza.liveservice.RxService.publishOnService
import it.giovanniandreuzza.liveservice.RxService.schedulers
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

fun <T> executeInBackground(action: T): Disposable =
    Single.create<Boolean> {
        action
    }.subscribeOn(schedulers)
        .observeOn(schedulers)
        .subscribe()

fun KFunction<*>.params(data: Any): ParametersData =
    ParametersData(this.javaMethod!!.returnType, this.javaMethod!!.name, data)

fun KFunction<*>.params(): ParametersData = ParametersData(this.javaMethod!!.returnType, this.javaMethod!!.name, null)

fun <T> ParametersData.observe(): Observable<T> {
    val subject = ReplaySubject.create<T>()
    publishOnService(LiveResponse(command, subject, data))
    return subject.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(schedulers)
}

fun ParametersData.observe(consumer: Consumer<Any>): Disposable {
    val subject = ReplaySubject.create<Any>()
    publishOnService(LiveResponse(command, subject, data))
    return subject.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(schedulers)
        .subscribe(consumer)
}