package net.jose.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import net.jose.domain.users.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class UserDao extends JdbcDaoSupport {
  private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

  @PostConstruct
  public void initialize() {
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("edu.sql"));
    DatabasePopulatorUtils.execute(populator, getDataSource());
    logger.info("Database initialize success");
  }

  public User findById(String userId) {
    String sql = "select * from users where userId = ?";
    RowMapper<User> rowMapper = new RowMapper<User>() {
      @Override
      public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
            rs.getString("userId")
            , rs.getString("password")
            , rs.getString("name")
            , rs.getString("email"));
      }
    };
    return getJdbcTemplate().queryForObject(sql, rowMapper, userId);
  }

  public void create(User user) {
    String sql = "insert into users values (?, ?, ?, ?)";
    getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
  }

}
