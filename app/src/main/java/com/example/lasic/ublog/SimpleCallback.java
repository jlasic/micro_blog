package com.example.lasic.ublog;


/**
 * Created by lasic on 18.10.2017..
 */

public interface SimpleCallback <T>{
    void onSuccess(T data);
    void onError(String error);
}
