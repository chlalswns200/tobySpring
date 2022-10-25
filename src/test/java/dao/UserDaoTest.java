package dao;

import domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoTest {

    User userA;

    @Test
    @DisplayName("addAndGet")
    void addAndGetTest() {

        UserDao userDao = new UserDaoFactory().awsUserDao();
        userA = new User("50","testName","1234");

        userDao.add(userA);

        User byId = userDao.findById("50");
        assertEquals("testName",byId.getName());
    }

}