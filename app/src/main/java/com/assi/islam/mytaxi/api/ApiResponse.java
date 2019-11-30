package com.assi.islam.mytaxi.api;

import android.util.Log;

import com.assi.islam.mytaxi.model.ApiError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
/**
 * Created by islam assi
 *
 * handle api responses. In case of generic stuff needs to be applied on every response.
 * Also, parse errors to {@link E} in case server is returning a structured error
 */

public abstract class ApiResponse<S,E extends ApiError> extends DisposableObserver<Response<S>> {

    private static final String TAG = ApiResponse.class.getSimpleName();

    private Class<E> errorClass;

    public ApiResponse(Class errorClass) {
        this.errorClass = errorClass;
    }

    public ApiResponse() { }

    protected abstract void onSuccess(S s);

    protected <D extends ApiError> void onFailure(D error) { }

    @Override
    public void onNext(Response<S> t) {

        onResponse(t);
    }

    private void onResponse(Response<S> responseModel) {

        if (responseModel.isSuccessful()) {

            onSuccess(responseModel.body());

        } else {

            ResponseBody errorBody = responseModel.errorBody();

            E parsedError = null;

            if(errorBody != null && errorClass != null) {

                parsedError = parseError(responseModel, errorClass);
            }

            onFailure(parsedError != null? parsedError : new ApiError());
        }

        if (responseModel.errorBody() != null){

            responseModel.errorBody().close();
        }

    }

    @Override
    public void onError(Throwable e) {

        Log.e(TAG, "onError: ", e);

        E apiError = null;

        try {
            apiError = errorClass.newInstance();

            apiError.setThrowable(e);

            onFailure(apiError);

            return;

        } catch (Exception exception) {

            exception.printStackTrace();
        }

        onFailure(new ApiError());
    }

    @Override
    public void onComplete() {

    }


    /**
     * Try to parse error response to {@link E}
     * @param response
     * @param apiErrorClass
     * @return
     */
    public E parseError(Response<?> response, Class<E> apiErrorClass) {

        E error = null;

        Converter<ResponseBody, E> converter = new Retrofit.Builder().build().responseBodyConverter(apiErrorClass, new Annotation[0]);
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {

            Log.e(TAG, "Error parsing error response", e);
        }

        return error;
    }
}