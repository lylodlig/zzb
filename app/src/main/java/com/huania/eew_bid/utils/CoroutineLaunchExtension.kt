package com.huania.eew_bid.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


const val ERROR_CODE_NORM = 0xFF00
const val ERROR_CODE_INIT = 0xFF10
const val ERROR_CODE_MORE = 0xFF11

typealias CoroutineThrowableHandler = (Throwable) -> Unit

private fun coroutineExceptionHandler(
    throwableHandler: CoroutineThrowableHandler?
): CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwableHandler?.invoke(throwable)
}

private fun coroutineExceptionContext(
    throwableHandler: CoroutineThrowableHandler?
): CoroutineContext = coroutineExceptionHandler(throwableHandler) + GlobalScope.coroutineContext

fun CoroutineScope.safeLaunch(
    block: suspend () -> Unit,
    throwableHandler: CoroutineThrowableHandler? = null
): Job = launch(coroutineExceptionContext(throwableHandler)) {
    block()
}

@Suppress("FunctionName")
fun IOScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

fun CoroutineScope.delayLaunch(
    timeMills: Long,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend () -> Unit
): Job = launch(context, start) {
    check(timeMills >= 0) { "timeMills must be positive" }

    delay(timeMills)
    block()
}

fun CoroutineScope.repeatLaunch(
    interval: Long, count: Int = Int.MAX_VALUE,
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend (Int) -> Unit
): Job = launch(context, start) {
    check(interval >= 0) { "interval time must be positive" }
    check(count > 0) { "repeat count must larger than 0" }

    repeat(count) { index ->
        block(index)
        delay(interval)
    }
}