package com.example.tomasNewsApp

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun rx1() {
        val observable = Observable.create<String> { emitter ->
            Thread.sleep(1000)
//            emitter.onNext("next")
//            Thread.sleep(1000)
//            emitter.onNext("next1")
//            Thread.sleep(1000)
//            emitter.onNext("next2")
            emitter.onComplete()
//            emitter.onError(Exception())
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.trampoline())
            .debounce(2, TimeUnit.SECONDS)
            .map { it + "value" }
            .filter { true }
            .subscribe({ println(it) }, { it is Exception }, { -> print("complete") })
        println("post")
        println(observable)

//        Completable
//        Single
//        Maybe

        Thread.sleep(4000)
    }
}
