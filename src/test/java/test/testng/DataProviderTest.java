package test.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderTest {

    @Test(dataProvider = "usernameData")
    public void testDataProvider(User user) {
        System.out.println(user);
    }

    @DataProvider(name = "usernameData")
    public User[] getUsername() {
        User teo = new User("teo", 18);
        User ti = new User("ti", 19);
        User tun = new User("tun", 20);
        return new User[] {teo, ti , tun};
    }

    public static class User {
        private String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
