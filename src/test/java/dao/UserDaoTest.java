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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;
    User userA;
    User userB;
    UserDao userDao;


    @BeforeEach
    void init() {
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        userA = new User("50", "testName", "1234");
        userB = new User("20", "testName2", "12345678");
        userDao.deleteAll();
    }

    @Test
    @DisplayName("addAndGet")
    void addAndGetTest() {
        userDao.add(userA);

        User byId = userDao.findById("50");
        assertEquals("testName", byId.getName());
    }

    @Test
    @DisplayName("deleteAll")
    void deleteTest() {
        assertEquals(0, userDao.getCount());
    }

    @Test
    @DisplayName("get all 테스트")
    void getAllTest() {
        List<User> all = userDao.getAll();
        assertEquals(0, all.size());
    }

    @Test
    @DisplayName("get all 테스트(2개 추가)")
    void getAllTest2() {

        userDao.add(userA);
        userDao.add(userB);
        List<User> all = userDao.getAll();
        assertEquals(2, all.size());
    }
}