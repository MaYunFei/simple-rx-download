package io.github.mayunfei.simple_download;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mayunfei on 17-3-21.
 */

public interface DownloadApi {
  @Streaming @GET Observable<Response<ResponseBody>> RxDownload(@Url String url);
}
