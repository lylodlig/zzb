package com.huania.eew_bid.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.huania.eew_bid.R
import com.jmolsmobile.landscapevideocapture.CLog
import com.jmolsmobile.landscapevideocapture.VideoCaptureActivity
import com.jmolsmobile.landscapevideocapture.VideoFile
import com.jmolsmobile.landscapevideocapture.camera.CameraWrapper
import com.jmolsmobile.landscapevideocapture.camera.NativeCamera
import com.jmolsmobile.landscapevideocapture.configuration.CaptureConfiguration
import com.jmolsmobile.landscapevideocapture.recorder.AlreadyUsedException
import com.jmolsmobile.landscapevideocapture.recorder.VideoRecorder
import com.jmolsmobile.landscapevideocapture.recorder.VideoRecorderInterface
import com.jmolsmobile.landscapevideocapture.view.VideoCaptureView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find

abstract class BaseVideoActivity<VB : ViewDataBinding> : BaseActivity<VB>(),
    VideoRecorderInterface {

    private var mVideoRecorded = false
    private var mVideoFile: VideoFile? = null
    private var mCaptureConfiguration: CaptureConfiguration? = null

    private var mVideoCaptureView: VideoCaptureView? = null
    private var mVideoRecorder: VideoRecorder? = null
    private var isFrontFacingCameraSelected: Boolean = false

    private var observer: Disposable? = null

    override fun initActivity(savedInstanceState: Bundle?) {
        if (!intent.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME).isNullOrBlank()) {
            initializeCaptureConfiguration(savedInstanceState)
            mVideoCaptureView = find(R.id.video_view)
            initializeRecordingUI()
        }
    }

    private fun stopRecord() {
        if (mVideoRecorder == null) {
            return
        }
        mVideoRecorder?.stopRecording(null)
//        releaseAllResources()
//        mVideoRecorder = null
//        val entity = VideoShowEntity().apply {
//            date = TimeUtils.date2String(Date(), SimpleDateFormat("yyyy-MM-dd HH:mm"))
//            path = intent.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME)
//        }
//        AppDataBase.getInstance().videoDao().insert(entity)
    }

    private fun initializeCaptureConfiguration(savedInstanceState: Bundle?) {
        mCaptureConfiguration = generateCaptureConfiguration()
        mVideoRecorded = generateVideoRecorded(savedInstanceState)
        mVideoFile = generateOutputFile(savedInstanceState)
        isFrontFacingCameraSelected = generateIsFrontFacingCameraSelected()
    }

    private fun initializeRecordingUI() {
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        mVideoRecorder = VideoRecorder(
            this,
            mCaptureConfiguration,
            mVideoFile,
            CameraWrapper(NativeCamera(), display.rotation),
            mVideoCaptureView?.previewSurfaceHolder,
            isFrontFacingCameraSelected
        )
        mVideoCaptureView?.setCameraSwitchingEnabled(mCaptureConfiguration!!.allowFrontFacingCamera)
        mVideoCaptureView?.setCameraFacing(isFrontFacingCameraSelected)

        if (mVideoRecorded) {
            mVideoCaptureView?.updateUIRecordingFinished(getVideoThumbnail())
        } else {
            mVideoCaptureView?.updateUINotRecording()
        }
        mVideoCaptureView?.showTimer(mCaptureConfiguration!!.showTimer)
        observer?.dispose()
        observer = Observable.create<Int> { o ->
            Thread.sleep(2 * 1000)
            try {
                mVideoRecorder?.toggleRecording()
            } catch (e: AlreadyUsedException) {
                CLog.d(CLog.ACTIVITY, "Cannot toggle recording after cleaning up all resources")
            }
            o.onNext(1)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            },{it.printStackTrace()})
    }

//    override fun onPause() {
//        if (mVideoRecorder != null) {
//            mVideoRecorder!!.stopRecording(null)
//        }
//        releaseAllResources()
//        super.onPause()
//    }

    override fun onRecordingStarted() {
        runOnUiThread {
            mVideoCaptureView?.updateUIRecordingOngoing()
        }

    }

    override fun onRecordingStopped(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }

        mVideoCaptureView!!.updateUIRecordingFinished(getVideoThumbnail())
        releaseAllResources()
    }

    override fun onRecordingSuccess() {
        mVideoRecorded = true
    }

    override fun onRecordingFailed(message: String) {
        finishError(message)
    }

    private fun finishCompleted() {
        val result = Intent()
        result.putExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME, mVideoFile?.getFullPath())
        this.setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun finishCancelled() {
        this.setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun finishError(message: String) {
        Toast.makeText(applicationContext, "Can't capture video: $message", Toast.LENGTH_LONG)
            .show()

//        val result = Intent()
//        result.putExtra(EXTRA_ERROR_MESSAGE, message)
//        this.setResult(RESULT_ERROR, result)
        finish()
    }

    private fun releaseAllResources() {
        if (mVideoRecorder != null) {
            mVideoRecorder?.releaseAllResources()
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean(VideoCaptureActivity.SAVED_RECORDED_BOOLEAN, mVideoRecorded)
        savedInstanceState.putString(
            VideoCaptureActivity.SAVED_OUTPUT_FILENAME,
            mVideoFile?.getFullPath()
        )
        super.onSaveInstanceState(savedInstanceState)
    }

    protected fun generateCaptureConfiguration(): CaptureConfiguration {
        var returnConfiguration =
            this.intent.getParcelableExtra<CaptureConfiguration>(VideoCaptureActivity.EXTRA_CAPTURE_CONFIGURATION)
        if (returnConfiguration == null) {
            returnConfiguration = CaptureConfiguration.getDefault()
            CLog.d(CLog.ACTIVITY, "No captureconfiguration passed - using default configuration")
        }
        return returnConfiguration!!
    }

    private fun generateVideoRecorded(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState?.getBoolean(VideoCaptureActivity.SAVED_RECORDED_BOOLEAN, false)
            ?: false
    }

    protected fun generateOutputFile(savedInstanceState: Bundle?): VideoFile {
        // TODO: add checks to see if outputfile is writeable
        return if (savedInstanceState != null) {
            VideoFile(savedInstanceState.getString(VideoCaptureActivity.SAVED_OUTPUT_FILENAME))
        } else {
            VideoFile(intent.getStringExtra(VideoCaptureActivity.EXTRA_OUTPUT_FILENAME))
        }
    }

    private fun generateIsFrontFacingCameraSelected(): Boolean {
        return intent.getBooleanExtra(VideoCaptureActivity.EXTRA_FRONTFACINGCAMERASELECTED, false)
    }

    fun getVideoThumbnail(): Bitmap? {
        val thumbnail = ThumbnailUtils.createVideoThumbnail(
            mVideoFile?.fullPath!!,
            MediaStore.Video.Thumbnails.FULL_SCREEN_KIND
        )
        if (thumbnail == null) {
            CLog.d(CLog.ACTIVITY, "Failed to generate video preview")
        }
        return thumbnail
    }

    override fun onDestroy() {
        super.onDestroy()
        observer?.dispose()
        stopRecord()
    }

}