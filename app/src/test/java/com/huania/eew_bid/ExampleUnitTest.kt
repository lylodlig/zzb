package com.huania.eew_bid

import com.huania.eew_bid.ext.format
import com.huania.eew_bid.ext.round
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    fun Double.format(digits: Int) = "%.${digits}f".format(this)

    @Test
    fun addition_isCorrect() {
        println((2/3f).round(2))
        println((2/3f).format(2))
    }
}