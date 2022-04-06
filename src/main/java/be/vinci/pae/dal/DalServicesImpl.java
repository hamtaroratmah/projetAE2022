package be.vinci.pae.dal;

import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;


public class DalServicesImpl implements DalBackendServices, DalServices {

  private final ThreadLocal<Connection> threadLocalValue;
  private final BasicDataSource dataSource;

  /**
   * Constructor : open the database's connexion.
   */
  public DalServicesImpl() {
    this.threadLocalValue = new ThreadLocal<>();
    this.dataSource = new BasicDataSource();
    String dbUsername = Config.getProperty("dbUsername");
    String dbPassword = Config.getProperty("dbPassword");
    String url = Config.getProperty("dbUrl");
    dataSource.setUrl(url);
    dataSource.setUsername(dbUsername);
    dataSource.setPassword(dbPassword);
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("PostgresSQL's Driver missing !");
      System.exit(1);
    }
  }

  /**
   * Return a preparedStatement that can be executed after setting the username if the query is
   * executed, it'll give a member he's username match with your parameter.
   */
  @Override
  public PreparedStatement getPreparedStatement(String query) {
    PreparedStatement statement;
    try {
      Connection conn = threadLocalValue.get();
      statement = conn.prepareStatement(query);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
    return statement;
  }

  @Override
  public void startTransaction() {
    try {
      if (threadLocalValue.get() != null) {
        throw new FatalException("Cannot have two transactions at the same time\n");
      }
      Connection conn = dataSource.getConnection();
      conn.setAutoCommit(false);
      threadLocalValue.set(conn);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public void commitTransaction() {
    try (Connection conn = threadLocalValue.get()) {
      if (conn == null) {
        throw new FatalException("No connection available\n");
      }
      conn.commit();
      conn.setAutoCommit(true);
      threadLocalValue.set(null);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

  @Override
  public void rollbackTransaction() {
    try (Connection conn = threadLocalValue.get()) {
      if (conn == null) {
        throw new FatalException("No connection available\n");
      }
      conn.rollback();
      threadLocalValue.set(null);
    } catch (SQLException e) {
      throw new FatalException(e.getMessage());
    }
  }

}
