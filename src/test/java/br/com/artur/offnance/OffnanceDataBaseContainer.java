package br.com.artur.offnance;

import org.testcontainers.containers.MySQLContainer;

public class OffnanceDataBaseContainer extends MySQLContainer<OffnanceDataBaseContainer> {

    public static final String MYSQL_VERSION = "mysql:5.7";
    public static final String APP_NAME = "offnance";

    private boolean isActive;

    public static OffnanceDataBaseContainer container;

    public static OffnanceDataBaseContainer getInstance() {
        return getInstance(true);
    }

    public static OffnanceDataBaseContainer getInstance(final boolean isActive) {
        if (container == null) {
            container = new OffnanceDataBaseContainer(isActive);
        }
        return container;
    }

    public OffnanceDataBaseContainer(final boolean isActive) {
        super(MYSQL_VERSION);
        this.withUsername(APP_NAME).withDatabaseName(APP_NAME).withPassword(APP_NAME)
                .isActive(isActive).withReuse(false);
    }

    private OffnanceDataBaseContainer isActive(boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    @Override
    public void start() {
        if (this.isActive) {
            super.start();
            System.setProperty("DB_URL", container.getJdbcUrl());
            System.setProperty("DB_USR", container.getUsername());
            System.setProperty("DB_PASS", container.getPassword());
        }
    }

    @Override
    public void stop() {
    }
}
