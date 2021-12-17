package cn.com.gotowhere.proxy;

import cn.com.gotowhere.annotations.After;
import cn.com.gotowhere.annotations.Around;
import cn.com.gotowhere.annotations.Before;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory implements MethodInterceptor,InvocationHandler {

    // 需要代理的目标对象
    private Object targetObject;

    //切面方法类
    private BeforeAndAfter beforeAndAfter;

    public ProxyFactory(BeforeAndAfter beforeAndAfter){
        this.beforeAndAfter = beforeAndAfter;
    }

    public Object newProxy(Object targetObject){
        if( targetObject.getClass().getInterfaces().length > 0 )
            return this.newJDKProxy(targetObject);
        else
            return this.newCGLibProxy(targetObject);
    }

    public Object newJDKProxy(Object targetObject) {
        // 将目标对象传入进行代理
        this.targetObject = targetObject;
        // 返回代理对象
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }


    public Object newCGLibProxy(Object obj) {
        this.targetObject = obj;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        Object proxyObj = enhancer.create();
        return proxyObj;
    }

    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return this.invoke(proxy,method,args);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        // 判断是否方法上存在注解
        boolean aroundAnnotationPresent = method.isAnnotationPresent(Around.class)||targetObject.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes()).isAnnotationPresent(Around.class);
        boolean beforeAnnotationPresent = method.isAnnotationPresent(Before.class)||targetObject.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes()).isAnnotationPresent(Before.class);
        boolean afterAnnotationPresent = method.isAnnotationPresent(After.class)||targetObject.getClass().getDeclaredMethod(method.getName(),method.getParameterTypes()).isAnnotationPresent(After.class);
        if (beforeAnnotationPresent||aroundAnnotationPresent) {
            //before method
            this.beforeAndAfter.beforeMethod(targetObject,method,args);
        }
        obj = method.invoke(targetObject, args);
        if (afterAnnotationPresent||aroundAnnotationPresent) {
            //after method
            this.beforeAndAfter.afterMethod(targetObject,method,args);
        }
        return obj;
    }

}
