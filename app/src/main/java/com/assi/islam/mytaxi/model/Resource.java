package com.assi.islam.mytaxi.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic class that contains data and status about loading this data. Also, error object in case of error.
 * @param <S> data object type
 * @param <E> error object type
 */
public class Resource<S, E extends ApiError> {
    @NonNull
    public final Status status;

    @Nullable
    public final S data;

    @Nullable
    public final E errorObject;

    public boolean isNewRequest = true;

    private Resource(@NonNull Status status, @Nullable S data,
            @Nullable E errorObject) {
        this.status = status;
        this.data = data;
        this.errorObject = errorObject;
    }

    private Resource(@NonNull Status status, @Nullable S data, @Nullable E errorObject, boolean isNewRequest) {
        this.status = status;
        this.data = data;
        this.errorObject = errorObject;
        this.isNewRequest = isNewRequest;
    }

    /**
     *
     * @return a success resource containing {@link T} object.
     * this resource should be used when the data finish loading successfully
     */
    public static <T, E extends ApiError> Resource<T, E> success(@NonNull T data) {
        return success(data, false);
    }

    public static <T, E extends ApiError> Resource<T, E> success(@NonNull T data, boolean isNewRequest) {
        return new Resource<>(Status.SUCCESS, data, null, isNewRequest);
    }

    /**
     *
     * @return an error resource containing {@link E} object.
     * this resource should be used when an error occurred through loading the data
     */
    public static <T, E extends ApiError> Resource<T, E> error(E errorObject) {
        return new Resource<>(Status.ERROR, null, errorObject);
    }

    /**
     *
     * @return a loading resource containing {@link E} object.
     * this resource should be used when data is still loading and not yet finished.
     * if the data is lazyly loaded, this resource can carry the new loaded data
     */
    public static <T, E extends ApiError> Resource<T, E> loading(@Nullable T data, boolean isNewRequest) {
        return new Resource<>(Status.LOADING, data, null, isNewRequest);
    }

    public enum Status { SUCCESS, ERROR, LOADING }
}