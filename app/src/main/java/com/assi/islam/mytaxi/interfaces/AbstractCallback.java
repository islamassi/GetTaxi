package com.assi.islam.mytaxi.interfaces;

import java.io.Serializable;

/**
 * Created by islam assi
 *
 * generic callback
 * @param <T> type of the result object
 */
public interface AbstractCallback<T> extends Serializable {
    void onResult(boolean isSuccess, T result);
}
