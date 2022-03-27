package be.vinci.pae.dal;

import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.utils.Config;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;

public class DalServicesImpl implements DalBackendServices, DalServices {

  private ThreadLocal<Connection> threadLocalValue;
  private BasicDataSource dataSource;
  private String dbUsername;
  private String dbPassword;
  private String url;

  /**
   * Constructor : open the database's connexion.
   */
  public DalServicesImpl() {
    threadLocalValue = new ThreadLocal<>();
    dataSource = new BasicDataSource();
    dbUsername = Config.getProperty("dbUsername");
    dbPassword = Config.getProperty("dbPassword");
    url = Config.getProperty("dbUrl");
    dataSource.setUrl(url);
    dataSource.setUsername(dbUsername);
    dataSource.setPassword(dbPassword);
    try {
      dataSource.setDriverClassName("org.postgresql.Driver");
    } catch (SecurityException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
  }

  /**
   * Return a preparedStatement that can be executed after setting the username if the query is
   * executed, it'll give a member he's username match with your parameter.
   */
  @Override
  public PreparedStatement getPreparedStatement(String query) {
    PreparedStatement statement = null;
    try {
      Connection conn = threadLocalValue.get();
      statement = conn.prepareStatement(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return statement;
  }

  @Override
  public void openConnection() {
    try {
      Connection conn = dataSource.getConnection();
      threadLocalValue.set(conn);
    } catch (SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }

  @Override
  public void startTransaction() {
    try {
      openConnection();
      Connection conn = threadLocalValue.get();
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void commitTransaction() {
    try {
      Connection conn = threadLocalValue.get();
      conn.commit();
//      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void rollbackTransaction() {
    try {
      Connection conn = threadLocalValue.get();
      conn.rollback();
//      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
