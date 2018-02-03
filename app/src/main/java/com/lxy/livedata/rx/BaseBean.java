package com.lxy.livedata.rx;

import java.io.Serializable;

/**
 *
 * @author a
 * @date 2018/1/29
 */

public class BaseBean<T> implements Serializable {

    public boolean error;

    public T results;
}
