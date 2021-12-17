package cn.com.gotowhere.proxy;

import java.lang.reflect.Method;

public abstract class BeforeAndAfter {

    public abstract void before(Object proxy);

    public abstract void after(Object proxy);

    public void beforeMethod(Object proxy, Method method, Object[] args){
        this.before(proxy);
    }

    public void afterMethod(Object proxy, Method method, Object[] args){
        this.after(proxy);
    }

}
