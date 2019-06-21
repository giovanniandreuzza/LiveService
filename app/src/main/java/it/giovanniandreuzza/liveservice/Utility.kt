package it.giovanniandreuzza.liveservice

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> executeInBackground(action: T): Disposable {
    return Single.create<Boolean> {
        action
    }.subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe()
}