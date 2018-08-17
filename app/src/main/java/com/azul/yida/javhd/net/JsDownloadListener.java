package com.azul.yida.javhd.net;

/**
 * Created by xuyimin on 2018/8/17.
 * E-mail codingyida@qq.com
 */

public interface JsDownloadListener {

    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);

}
