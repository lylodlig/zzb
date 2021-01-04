package com.huania.eew_bid.utils.upload

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.SystemClock
import androidx.core.content.FileProvider
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ZipUtils
import com.huania.eew_bid.app.App
import com.huania.eew_bid.utils.log.LoganParser
import com.huania.eew_bid.utils.log.logD
import com.huania.eew_bid.utils.log.logE
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 *   created by lzy
 *   on 2020/8/18
 */
@SuppressLint("CheckResult")
fun uploadLog(context: Context) {
    val files =
        FileUtils.listFilesInDir("${context.getExternalFilesDir("Log")?.absolutePath}/logan_v1")
    var p =
        LoganParser("0123456789012345".toByteArray(), "0123456789012345".toByteArray())
    Observable.create<File> { e ->
        FileUtils.deleteAllInDir("${context.getExternalFilesDir("Upload")?.absolutePath}")
        files.forEach { e.onNext(it) }
        e.onComplete()
    }.observeOn(Schedulers.io()).subscribeOn(Schedulers.io())
        .subscribe({
            val fos = FileOutputStream(
                File("${context.getExternalFilesDir("Upload")?.absolutePath}/${it.name}.txt")
            )
            val fis = FileInputStream(it)
            p.parse(fis, fos)
            fis.close()
            fos.close()
        }, {
            it.printStackTrace()
            logE("解密错误:${it.message}")
        }, {
            val target = File(
                "${context.getExternalFilesDir("Upload")?.absolutePath}/log.zip"
            )
            ZipUtils.zipFiles(
                FileUtils.listFilesInDir(context.getExternalFilesDir("Upload")?.absolutePath),
                target
            )
            var files = mutableListOf<FileData>()
            files.add(FileData(target.name, target.absolutePath))
            var fileManager = FileUploadManager.with(files)
            fileManager.startUpload()
        })
}
