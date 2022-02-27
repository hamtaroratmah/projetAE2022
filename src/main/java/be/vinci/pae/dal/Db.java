package be.vinci.pae.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

  public Db() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Driver PostgreSQL manquant !");
      System.exit(1);
    }
    String url = "jdbc:postgresql://coursinfo.vinci.be:5432/dbsoulaymane_gharroudi?user=soulaymane_gharroudi";
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, "soulaymane_gharroudi", "base de données");
    } catch (
        SQLException e) {
      System.out.println("Impossible de joindre le server !");
      System.exit(1);
    }
  }
}