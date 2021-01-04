package com.huania.eew_bid

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huania.xlib.strategy.*
import org.jetbrains.anko.runOnUiThread

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.huania.eew", appContext.packageName)
        appContext.runOnUiThread {
            val o = MutableLiveData<Any>()
            o.observeForever {
                println("!!!!!!!===" + it)
            }
            val obj = Object()
            o.value = obj
            o.value = obj
        }

    }

    /**
     * 测试单目标 单震
     */
    @Test
    fun use2() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 7.1f, "测试1", 106.1f, 30.1f, 100f, 1606356683, 1606356683, 1, true
            )
            val event2 = Event(
                1, 2, 7.1f, "测试1", 106.1f, 30.1f, 100f, 1606356683, 1606356683, 1, true
            )
            val event3 = Event(//
                1, 3, 7.1f, "测试1", 106.1f, 30.1f, 100f, 1606356683, 1606356683, 1, true
            )
            val event4 = Event(//
                1, 4, 7.1f, "测试1", 106.1f, 30.1f, 100f, 1606356683, 1606356683, 1, true
            )
            val quakeSeqContainer = QuakeSeqContainer(SingleComparator(30f, 104f))
            quakeSeqContainer.observable.observeForever {
                println("心态发生变化：$it")
            }
            val handler = Handler(appContext.mainLooper)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event1)
            }, 1000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 2000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event3)
            }, 3000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event4)
            }, 4000)
        }
        Thread.sleep(5000)

    }

    /**
     * 测试单目标 多震, 地震2 优先级较高, 两个大于5.0
     */
    @Test
    fun use3() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 5.1f, "测试1", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event2 = Event(
                1, 2, 5.1f, "测试1", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event3 = Event(//
                2, 3, 7.1f, "测试2", 106.0f, 30.0f, 100f, 1606356683, 1606356683, 1, true
            )
            val event4 = Event(//
                2, 4, 7.1f, "测试2", 106.2f, 30.2f, 100f, 1606356683, 1606356683, 1, true
            )
            val quakeSeqContainer = QuakeSeqContainer(SingleComparator(30f, 104f))
            quakeSeqContainer.observable.observeForever {
                println("心态发生变化：$it")
            }
            val handler = Handler(appContext.mainLooper)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event1)
            }, 1000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 2000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event3)
            }, 3000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event4)
            }, 4000)
        }
        Thread.sleep(5000)

    }


    /**
     * 测试单目标 多震,
     */
    @Test
    fun use5() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 3f, "测试1", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event2 = Event(
                2, 2, 2f, "测试2", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event3 = Event(//
                3, 3, 4f, "测试3", 106.0f, 30.0f, 100f, 1606356683, 1606356683, 1, true
            )
            val event4 = Event(//
                4, 4, 1f, "测试4", 106.2f, 30.2f, 100f, 1606356683, 1606356683, 1, true
            )
            val quakeSeqContainer = QuakeSeqContainer(SingleComparator(30f, 104f))
            quakeSeqContainer.observable.observeForever {
                println("心态发生变化：$it")
            }
            val handler = Handler(appContext.mainLooper)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event1)
            }, 1000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 2000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event3)
            }, 3000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event4)
            }, 4000)
        }
        Thread.sleep(5000)

    }

    /**
     * 测试单目标 多震,  测试移除
     */
    @Test
    fun useaaa() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 3f, "测试1", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event2 = Event(
                2, 2, 2f, "测试2", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event3 = Event(//
                3, 3, 4f, "测试3", 106.0f, 30.0f, 100f, 1606356683, 1606356683, 1, true
            )

            val quakeSeqContainer = QuakeSeqContainer(SingleComparator(30f, 104f))
            quakeSeqContainer.observable.observeForever {
                println("心态发生变化：$it")
                if (it.event_id == 3L) {
                    quakeSeqContainer.remove(it)
                }
            }
            val handler = Handler(appContext.mainLooper)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event1)
            }, 1000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 2000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event3)
            }, 3000)

        }
        Thread.sleep(5000)

    }


    /**
     * 测试多目标 多震
     */
    @Test
    fun useMulti1() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 3f, "测试1", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event2 = Event(
                2, 2, 2f, "测试2", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event3 = Event(//
                3, 3, 4f, "测试3", 106.0f, 30.0f, 100f, 1606356683, 1606356683, 1, true
            )

            val quakeSeqContainer = QuakeSeqContainer(MultiComparator())
            quakeSeqContainer.observable.observeForever {
                println("心态发生变化：$it")
                quakeSeqContainer.remove(it)
            }
            val handler = Handler(appContext.mainLooper)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event1)
            }, 1000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 2000)
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event3)
            }, 3000)

        }
        Thread.sleep(5000)

    }


    @Test
    fun useFilter() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.runOnUiThread {
            val event1 = Event(
                1, 1, 5f, "5级地震", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )
            val event2 = Event(
                2, 2, 6f, "6级地震", 106.1f, 30.1f, 100f, 1606356682, 1606356683, 1, true
            )


            val quakeSeqContainer = QuakeSeqContainer(MultiComparator())
            val processor = DebugProcessor(quakeSeqContainer)
            val filter = object : BaseFilter() {
                override fun accept(sequence: QuakeSequence) {
                    processor.handle(sequence)
                }

                override fun reject(sequence: QuakeSequence) {
                    quakeSeqContainer.remove(sequence)
                }
            }
            quakeSeqContainer.observable.observeForever(filter)
            val handler = Handler(appContext.mainLooper)
            quakeSeqContainer.update(event1)
            println("6级地震一秒后发生")
            handler.postDelayed(Runnable {
                quakeSeqContainer.update(event2)
            }, 100)

        }
        Thread.sleep(5000)

    }
}