package dao;

import domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    User userA;
    UserDao userDao;


    @BeforeEach
    void init() throws SQLException {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
    }

    @Test
    @DisplayName("addAndGet")
    void addAndGetTest() throws SQLException {
        userA = new User("50", "testName", "1234");
        userDao.add(userA);

        User byId = userDao.findById("50");
        assertEquals("testName", byId.getName());
    }

    @Test
    @DisplayName("deleteAll")
    void deleteTest() throws SQLException {
        assertEquals(0, userDao.getCount());
    }
}