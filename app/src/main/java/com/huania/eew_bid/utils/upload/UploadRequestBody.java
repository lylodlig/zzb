package com.huania.eew_bid.utils.upload;


import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 文件上传请求body
 * Created by dgg on 2017/9/27.
 */

public class UploadRequestBody extends RequestBody {
    private RequestBody mRequestBody;
    private final OnUploadProgressListener listener;

    public UploadRequestBody(File file, OnUploadProgressListener listener) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    //包装完成的BufferedSink
    private BufferedSink bufferedSink;

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }


    /**
     * 写入，回调进度接口
     *
     * @param sink Sink
     * @return Sink
     */
    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;

                //回调
                if (listener != null)
                    listener.onProgress(bytesWritten, contentLength, bytesWritten >= contentLength);
            }
        };
    }

    public interface OnUploadProgressListener {
        void onProgress(long progress, long max, boolean isFinish);
    }
}
