package it.giovanniandreuzza.liveservice

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> executeInBackground(action: T): Disposable {
    return Single.create<Boolean> {
        Log.d("ASD", "REPEAT COMMAND")
        action
    }.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe()
}

fun <T> executeInBackground(action: T, interval: Long): Disposable {
    return Observable.interval(interval, TimeUnit.MILLISECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe {
            action
        }
}