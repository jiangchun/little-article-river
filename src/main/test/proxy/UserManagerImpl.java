package proxy;

import cn.com.gotowhere.annotations.Around;
import cn.com.gotowhere.annotations.Before;

public class UserManagerImpl implements UserManager {

    @Before
    public User addUser(String id, String password) {
        User user = new User();
        user.id = id;
        user.password = password;
        System.out.println("调用了UserManagerImpl.addUser()方法！");
        return user;
    }

    @Around
    public void delUser(String id) {
        System.out.println("调用了UserManagerImpl.delUser()方法！");
    }

}
