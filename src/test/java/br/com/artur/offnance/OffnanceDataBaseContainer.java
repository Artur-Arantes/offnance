package br.com.artur.offnance;

import org.testcontainers.containers.MySQLContainer;

public class OffnanceDataBaseContainer extends MySQLContainer<OffnanceDataBaseContainer> {

  public static final String MYSQL_VERSION = "mysql:5.7";
  public static final String APP_NAME = "offnance";

  public static OffnanceDataBaseContainer container;

  public static OffnanceDataBaseContainer getInstance() {
    if (container == null) {
      container = new OffnanceDataBaseContainer();
    }
    return container;
  }

  public OffnanceDataBaseContainer() {
    super(MYSQL_VERSION);
    this.withUsername(APP_NAME).withDatabaseName(APP_NAME).withPassword(APP_NAME)
        .withReuse(false);
  }
  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USR", container.getUsername());
    System.setProperty("DB_PASS", container.getPassword());
  }

  @Override
  public void stop() {
  }
}
