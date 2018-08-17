package com.azul.yida.javhd.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AndroidUtils {

    /**
     * 保存照片并通知图库更新
     */
    public static Observable<Uri> saveImageAndGetPathObservable(final Context context,
                                                                final String url, final String title) {
        return Observable.create(e -> {
                    Bitmap bitmap = null;

                    try {
                        bitmap = ImgLoader.getInstance().getBitmap(context, url);
                    } catch (Exception exception) {
                        e.onError(exception);
                    }

                    if (bitmap == null) {
                        e.onError(new Exception("无法下载到图片"));
                    }
                    e.onNext(bitmap);
                    e.onComplete();
                }

        ).filter((b)-> Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                .flatMap(
                        bitmap -> {
                            File appDir = new File(Environment.getExternalStorageDirectory(), Contants.ROOT_DIR);
                            if (!appDir.exists()) {
                                boolean mkdir = appDir.mkdir();
                                if (!mkdir) return Observable.just(Uri.EMPTY);
                            }

                            String fileName = title.replace('/', '-') + ".jpg";
                            File file = new File(appDir, fileName);
                            try {
                                FileOutputStream outputStream = new FileOutputStream(file);
                                assert bitmap != null;
                                ((Bitmap)bitmap).compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 保存后要扫描一下文件，及时更新到系统目录（一定要加绝对路径，这样才能更新）
                            MediaScannerConnection.scanFile(context, new String[] {
                                    Environment.getExternalStorageDirectory()
                                            + File.separator
                                            + Contants.ROOT_DIR
                                            + File.separator
                                            + fileName
                            }, null, null);

                            Uri uri = Uri.fromFile(file);
                            return Observable.just(uri);
                        }
                ).subscribeOn(Schedulers.io());
    }

}

