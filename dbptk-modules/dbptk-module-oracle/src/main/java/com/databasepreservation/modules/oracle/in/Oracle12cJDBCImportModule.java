package com.databasepreservation.modules.oracle.in;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.databasepreservation.model.structure.SchemaStructure;
import com.databasepreservation.modules.jdbc.in.JDBCImportModule;
import com.databasepreservation.modules.oracle.OracleHelper;

/**
 * Microsoft SQL Server JDBC import module.
 *
 * @author Luis Faria <lfaria@keep.pt>
 */
public class Oracle12cJDBCImportModule extends JDBCImportModule {
  private static final Logger LOGGER = LoggerFactory.getLogger(Oracle12cJDBCImportModule.class);

  private final String schema;

  /**
   * Create a new Oracle12c import module
   *
   * @param serverName
   *          the name (host name) of the server
   * @param database
   *          the name of the database we'll be accessing
   * @param username
   *          the name of the user to use in the connection
   * @param password
   *          the password of the user to use in the connection
   */
  public Oracle12cJDBCImportModule(String serverName, int port, String database, String username, String password, String schema) {

    super("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:" + username + "/" + password + "@//" + serverName + ":"
      + port + "/" + database, new OracleHelper(), new Oracle12cJDBCDatatypeImporter());

    this.schema = schema;

    LOGGER.debug("jdbc:oracle:thin:<username>/<password>@//" + serverName + ":" + port + "/" + database);
  }

  @Override
  public Connection getConnection() throws SQLException {
    Connection connection = super.getConnection();
    connection.createStatement().executeUpdate("ALTER SESSION SET CURRENT_SCHEMA=" + schema);
    return connection;
  }

  @Override
  protected Statement getStatement() throws SQLException {
    if (statement == null) {
      Connection connection = getConnection();
      connection.createStatement().executeUpdate("ALTER SESSION SET CURRENT_SCHEMA=" + schema);
      statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
        ResultSet.HOLD_CURSORS_OVER_COMMIT);
    }
    return statement;
  }

  @Override
  protected String getDbName() throws SQLException {
    return getMetadata().getUserName();
  }

  @Override
  protected List<SchemaStructure> getSchemas() throws SQLException {
    List<SchemaStructure> schemas = new ArrayList<SchemaStructure>();
    schemas.add(getSchemaStructure(schema, 1));
    return schemas;
  }

  @Override
  protected String processActionTime(String string) {
    String[] parts = string.split("\\s+");
    String res = parts[0];
    if ("INSTEAD".equalsIgnoreCase(res)) {
      res += " OF";
    }
    return res;
  }
}
