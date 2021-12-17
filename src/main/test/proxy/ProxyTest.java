package proxy;

import cn.com.gotowhere.proxy.BeforeAndAfter;
import cn.com.gotowhere.proxy.ProxyFactory;
import com.alibaba.fastjson.JSON;

public class ProxyTest {

    public static void main(String[] args) {
        UserManager userManager = (UserManager)new ProxyFactory(new BeforeAndAfter() {
            @Override
            public void before(Object obj) {
                System.out.println("CGLib before");
                System.out.println(JSON.toJSONString(obj));
            }

            @Override
            public void after(Object obj) {
                System.out.println("CGLib after");
                System.out.println(JSON.toJSONString(obj));
            }
        }).newCGLibProxy(new UserManagerImpl());
        System.out.println("CGLibProxy：");
        User user=null;
        System.out.println("JSON:"+JSON.toJSONString(user));
        user = userManager.addUser("tom", "root");
        System.out.println("JSON:"+JSON.toJSONString(user));
        System.out.println("JDKProxy：");
        UserManager userManagerJDK = (UserManager)new ProxyFactory(new BeforeAndAfter() {
            @Override
            public void before(Object obj) {
                System.out.println("JDK before");
                System.out.println(JSON.toJSONString(obj));
            }

            @Override
            public void after(Object obj) {
                System.out.println("JDK after");
                System.out.println(JSON.toJSONString(obj));
            }
        }).newJDKProxy(new UserManagerImpl());
//        userManagerJDK.addUser("tom", "root");
        userManagerJDK.delUser("tom");
    }

}
