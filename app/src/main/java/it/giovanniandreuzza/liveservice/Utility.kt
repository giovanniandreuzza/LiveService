package it.giovanniandreuzza.liveservice

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject
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

fun KFunction<*>.params(data: Any): Triple<Class<*>, String, Any> =
    Triple(this.javaMethod!!.returnType, this.javaMethod!!.name, data)

fun Any.params(): String = this.toString()


fun String.observe(consumer: Consumer<Unit>): Disposable {
    val subject = BehaviorSubject.create<Unit>()

    val disposable = subject.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(schedulers)
        .subscribe(consumer)

    publishOnService(LiveResponse(this, subject, null))

    return disposable
}

fun <T> Triple<Class<*>, String, Any>.observe(): Observable<T> {
    val subject = BehaviorSubject.create<T>()

    val disposable = subject.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(schedulers)

    publishOnService(LiveResponse(second, subject, third))

    return disposable
}

fun Triple<Class<*>, String, Any>.observe(consumer: Consumer<Any>): Disposable {
    val subject = BehaviorSubject.create<Any>()

    val disposable = subject.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(schedulers)
        .subscribe(consumer)

    publishOnService(LiveResponse(second, subject, third))

    return disposable
}