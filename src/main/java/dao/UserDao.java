package dao;

import dao.connection.AwsConnectionMaker;
import dao.connection.ConnectionMaker;
import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) {
        try {

            Connection c = connectionMaker.makeConnection();

            PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

            ps.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {

        User user = null;
        Connection c;
        try {
            c = connectionMaker.makeConnection();

            PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            ps.close();
            c.close();

            if (user == null) {
                throw new EmptyResultDataAccessException(1);
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll() throws SQLException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("delete from users");

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;

    }
}
