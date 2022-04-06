public class DalServicesImpl implements DalServices, DalBackend {

  private ThreadLocal<Connection> connexion;
  private BasicDataSource baseDeDonnees;

  /**
   * La connexion vers la DB.
   */
  public DalServicesImpl() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    this.connexion = new ThreadLocal<>();
    this.baseDeDonnees = initBaseDeDonnees();
  }

  /**
   * Initialisation de la base de données.
   *
   * @return ronvoie la base de données
   */
  private BasicDataSource initBaseDeDonnees() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(Config.obtenirProperty("url"));
    ds.setUsername(Config.obtenirProperty("utilisateur"));
    ds.setPassword(Config.obtenirProperty("motDePasse"));
    return ds;
  }

  /**
   * La méthode reçoit une requête, puis elle gère la préparation de cette requête.
   *
   * @param requete La requête à préparer
   * @return renvoie une requête préparée
   */
  @Override
  public PreparedStatement preparerRequete(String requete) {
    try {
      PreparedStatement stmt;
      stmt = connexion.get().prepareStatement(requete);
      return stmt;
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw new FatalException(exception);
    }
  }

  /**
   * Commencer une transaction.
   *
   * @throws FatalException en cas d'erreur
   */
  public void commencerTransaction() throws FatalException {
    try {
      if (connexion.get() != null) {
        throw new FatalException("Impossible de commencer deux transaction à la fois.");
      }
      Connection conn = baseDeDonnees.getConnection();
      connexion.set(conn);
      conn.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new FatalException(e.getMessage());
    }
  }

  /**
     * Commit une transaction.
     *
     * @throws FatalException en cas d'erreur
     */
    public void commitTransaction() throws FatalException {
      try {
        Connection conn = null;
        if ((conn = connexion.get()) == null) {
          throw new FatalException("Pas de connexion");
        }
        conn.commit();
        conn.close();
        connexion.set(null);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException(e.getMessage());
      }
    }

    /**
     * Roll back une transaction.
     *
     * @throws FatalException en cas d'erreur
     */
    public void rollBackTransaction() throws FatalException {
      try {
        Connection conn = null;
        if ((conn = connexion.get()) == null) {
          throw new FatalException("Pas de transaction");
        }
        conn.rollback();
        conn.close();
        connexion.set(null);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new FatalException(e.getMessage());
      }
    }