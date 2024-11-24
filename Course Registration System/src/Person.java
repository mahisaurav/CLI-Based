public abstract class Person {
    private String email;
    private String name;
    private String password;

    public Person(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {

        return email;
    }

    public String getName() {

        return name;
    }

    public boolean validatePassword(String password) {

        return this.password.equals(password);
    }

}
