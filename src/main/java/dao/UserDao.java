package dao;

import domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user){
        this.jdbcTemplate.update("INSERT INTO users(id,name,password) values(?,?,?)",
                user.getId(),
                user.getName(),
                user.getPassword()
        );
    }

    public User findById(String id) {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }

    public List<User> getAll() {

        String sql = "select * from users order by id";

        RowMapper<User> rowMapper = new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs,int rowNum) throws SQLException {
                User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.query(sql,rowMapper);
    }


}
