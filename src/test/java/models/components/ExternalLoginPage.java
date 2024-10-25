package models.components;

public class ExternalLoginPage extends LoginPage{

    @Override
    protected String usernameLocator() {
        return "ExternalLoginPage | usernameLocator";
    }

    @Override
    protected String passwordLocator() {
        return "ExternalLoginPage | passwordLocator";
    }

    @Override
    protected String loginButtonLocator() {
        return "ExternalLoginPage | loginButtonLocator";
    }
}
