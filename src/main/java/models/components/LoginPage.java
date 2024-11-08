package models.components;

// Strongly FORCE IS_A relationship
public abstract class LoginPage {

    protected void login() {
        System.out.println(usernameLocator());
        System.out.println(passwordLocator());
        System.out.println(loginButtonLocator());
    }

    protected abstract String usernameLocator();
    protected abstract String passwordLocator();
    protected abstract String loginButtonLocator();
}
