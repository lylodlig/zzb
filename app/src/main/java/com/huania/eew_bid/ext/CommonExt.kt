package com.huania.eew_bid.ext

import android.content.Intent
import android.os.Build
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.huania.eew_bid.app.App
import com.huania.eew_bid.data.bean.Config
import com.huania.eew_bid.utils.SP
import com.huania.eew_bid.utils.log.logD
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import java.util.zip.Adler32
import kotlin.math.pow


fun Int.westernNumber(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}

fun Double.format(digits: Int): String {
    return "%.${digits}f".format(this)
}

fun Float.format(digits: Int): String {
    return "%.${digits}f".format(this)
}

fun Float.dp(): Int {
    val scale: Float = App.instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Float.px2dp(): Int {
    val scale: Float = App.instance.resources.displayMetrics.density
    logD("density:$scale")
    return (this / scale + 0.5f).toInt()
}

fun Int.dp(): Int {
    val scale: Float = App.instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

inline fun <reified T> String.toBeanList() =
    Gson().fromJson<T>(this, object : TypeToken<List<T>>() {}.type)

fun Double.mul(d2: Double) = BigDecimal(this).multiply(BigDecimal(d2))
fun Double.mul(d2: Float) = BigDecimal(this).multiply(BigDecimal(d2.toDouble()))
fun Float.mul(d2: Float) = BigDecimal(this.toDouble()).multiply(BigDecimal(d2.toDouble()))
fun Float.mul(d2: Double) = BigDecimal(this.toDouble()).multiply(BigDecimal(d2))

fun Double.div(d2: Double, point: Int) = BigDecimal(this).divide(
    BigDecimal(d2), point,
    BigDecimal.ROUND_HALF_DOWN
)

fun Double.div(d2: Float, point: Int) = BigDecimal(this).divide(
    BigDecimal(d2.toDouble()), point,
    BigDecimal.ROUND_HALF_DOWN
)

fun Float.div(d2: Double, point: Int) = BigDecimal(this.toDouble()).divide(
    BigDecimal(d2), point,
    BigDecimal.ROUND_HALF_DOWN
)

fun Float.div(d2: Float, point: Int) = BigDecimal(this.toDouble()).divide(
    BigDecimal(d2.toDouble()), point,
    BigDecimal.ROUND_HALF_DOWN
)

fun Float.round(scale: Int): String {
    var f = this + 0.000001f
    var bigDecimal = BigDecimal(f.toString())
    var s = bigDecimal.setScale(scale + 3, BigDecimal.ROUND_DOWN).toString()
    var s1 = "${s.split(".")[1]}"

    var s2 = s1.substring(scale, s1.length - 1)
    var t = 10.0.pow(-scale.toDouble())
    return if (s2.toInt() >= 50) {
        BigDecimal(f + t).setScale(scale, BigDecimal.ROUND_DOWN).toString()
    } else {
        BigDecimal(f.toString()).setScale(scale, BigDecimal.ROUND_DOWN).toString()
    }
}

fun getConfig(): Config {
    val settingData = SP.getString(spConfig)
    return GsonUtils.fromJson(settingData, Config::class.java)
}

fun Intent.startService() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        App.instance.startForegroundService(this)
    } else {
        App.instance.startService(this)
    }
}


//0038 13 64656c697665722e4175746852657175657374 0a1162363a35653a33363a64613a32633a636310c3cf241a0731323334353637 d419102b
fun toByteArray(name: ByteArray, data: ByteArray): ByteBuf {
    //校验部分
    val totalSize = name.size + data.size + 1
    val buffer = Unpooled.buffer(totalSize)
    buffer.writeByte(name.size)
    buffer.writeBytes(name)
    buffer.writeBytes(data)
    val adler32 = Adler32()
    adler32.update(buffer.array())

    val buffer2 = Unpooled.buffer(totalSize + 6)
    buffer2.writeShort(totalSize + 4)
    buffer2.writeBytes(buffer)
    buffer2.writeInt(adler32.value.toInt())
    return buffer2
}

fun toByteArray(name: ByteArray): ByteBuf {
    val totalSize = name.size + 1
    val buffer = Unpooled.buffer(totalSize)
    buffer.writeByte(name.size)
    buffer.writeBytes(name)
    val adler32 = Adler32()
    adler32.update(buffer.array())

    val buffer2 = Unpooled.buffer(totalSize + 6)
    buffer2.writeShort(totalSize + 4)
    buffer2.writeBytes(buffer)
    buffer2.writeInt(adler32.value.toInt())
    return buffer2
}