package com.huania.eew_bid.utils

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.os.Environment
import com.huania.eew_bid.utils.log.logD
import com.huania.eew_bid.utils.log.logE
import com.iflytek.cloud.*
import com.iflytek.cloud.util.ResourceUtil
import io.reactivex.disposables.Disposable
import java.math.BigDecimal

/**
 *   created by lzy
 *   on 2020/6/1
 */
private var observer: Disposable? = null
private var observerFault: Disposable? = null
private var observerPlay: Disposable? = null


fun configVolume(context: Context, vol: Int, isSettingTts: Boolean = false) {
    var audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    var current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    // 设置最大音量
    val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    val maxCall = audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF)

    val vol2 = vol
    val v = BigDecimal(vol2 * max).divide(BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP)
    val callV = if (isSettingTts)
        BigDecimal(vol2 * maxCall).divide(BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP)
            .toInt()
    else null

    logD("最大:$max,  call最大:${maxCall}   设置为:$vol   实际值：$v   $callV")
    setVolume(context, v.toInt())
}

fun setVolume(context: Context, volume: Int?) {
    if (volume == null)
        return
    var audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND)
}

fun setParam(mTts: SpeechSynthesizer, context: Context) {
    // 清空参数
    mTts?.apply {
        setParameter(SpeechConstant.PARAMS, null)
        //设置合成
        //设置使用本地引擎
        setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
        //设置发音人资源路径
        setParameter(ResourceUtil.TTS_RES_PATH, getResourcePath(context))
        //设置发音人
        setParameter(SpeechConstant.VOICE_NAME, "xiaoyan")
        //mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY,"1");//支持实时音频流抛出，仅在synthesizeToUri条件下支持
        //设置合成语速
        setParameter(SpeechConstant.SPEED, "50")
        //设置合成音调
        setParameter(SpeechConstant.PITCH, "50")
        //设置合成音量
        setParameter(SpeechConstant.VOLUME, "100")
        //设置播放器音频流类型
        setParameter(SpeechConstant.STREAM_TYPE, AudioManager.STREAM_DTMF.toString())
        // 设置播放合成音频打断音乐播放，默认为true
        setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false")
        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        setParameter(
            SpeechConstant.TTS_AUDIO_PATH,
            Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav"
        )
    }
}

//获取发音人资源路径
private fun getResourcePath(context: Context): String {
    val tempBuffer = StringBuffer()
    var type = "tts"
    //合成通用资源
    tempBuffer.append(
        ResourceUtil.generateResourcePath(
            context, ResourceUtil.RESOURCE_TYPE.assets,
            "$type/common.jet"
        )
    )
    tempBuffer.append(";")
    //发音人资源
    tempBuffer.append(
        ResourceUtil.generateResourcePath(
            context,
            ResourceUtil.RESOURCE_TYPE.assets,
            "$type/xiaoyan.jet"
        )
    )
    return tempBuffer.toString()
}

/**
 * 合成回调监听。
 */
class TtsListener(
    private val complete: (() -> Unit)? = null,
    private val start: (() -> Unit)? = null
) : SynthesizerListener {
    override fun onSpeakBegin() {
        //showTip("开始播放");
        logD("开始播放：" + System.currentTimeMillis())
        start?.invoke()
    }

    override fun onSpeakPaused() {
        logD("暂停播放")
    }

    override fun onSpeakResumed() {
        logD("继续播放")
    }

    override fun onBufferProgress(
        percent: Int, beginPos: Int, endPos: Int,
        info: String
    ) {
//        logD("合成进度：${percent}")
    }

    override fun onSpeakProgress(percent: Int, beginPos: Int, endPos: Int) {
        // 播放进度
//        logD("合成进度：${percent}")
    }

    override fun onCompleted(error: SpeechError?) {
        if (error == null) {
            logD("播放完成")
            complete?.invoke()
        } else if (error != null) {
            logE("播放出错,${error.getPlainDescription(true)}")
        }
    }

    //20001
    override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
        obj?.let {
            if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                val sid = obj.getString(SpeechEvent.KEY_EVENT_AUDIO_URL)
                logD("session id =" + sid!!)
            }
        }
    }

}