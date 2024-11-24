public interface User {

    void signUp(String email, String name, String password);

    void login(String email, String password);
}
