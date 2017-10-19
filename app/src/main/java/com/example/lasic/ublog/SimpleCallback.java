package com.example.lasic.ublog;


/**
 * Created by lasic on 18.10.2017..
 */

public interface SimpleCallback <D, E>{
    void onSuccess(D data);
    void onError(E error);
}
