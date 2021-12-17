package proxy;

public interface UserManager {

    public User addUser(String id, String password);

    public void delUser(String id);

}
