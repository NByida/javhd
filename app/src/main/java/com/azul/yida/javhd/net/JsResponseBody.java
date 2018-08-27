package com.azul.yida.javhd.net;

import com.azul.yida.javhd.common.Mlog;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by xuyimin on 2018/8/17.
 * E-mail codingyida@qq.com
 */

public class JsResponseBody extends ResponseBody {

    private ResponseBody responseBody;

    private JsDownloadListener downloadListener;

    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private BufferedSource bufferedSource;

    public JsResponseBody(ResponseBody responseBody, JsDownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            long time=0l;
            long speed=0l;
            long bytesOnePiece=0l;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (null != downloadListener) {
                    if (bytesRead != -1) {
                        if(System.currentTimeMillis()-time>=1000){
                            speed=bytesOnePiece/(System.currentTimeMillis()-time);
                            bytesOnePiece=0;
                            time=System.currentTimeMillis();
                            Mlog.t("speed is"+speed+"kb");
                        }else bytesOnePiece+=bytesRead != -1 ? bytesRead : 0;
                        downloadListener.onProgress((int) (totalBytesRead * 100 / responseBody.contentLength()));
                    }else downloadListener.onFinishDownload();
                }
                return bytesRead;
            }
        };
    }
}