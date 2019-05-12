package by.minsk.vasilyevanatali.auction.listener;

import by.minsk.vasilyevanatali.auction.connection.ConnectionPool;
import by.minsk.vasilyevanatali.auction.connection.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConnectionPoolListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolListener.class);
    private ConnectionPool connectionPool;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
        LOGGER.info("Connection pool init");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        connectionPool.destroyConnectionPool();
        LOGGER.info("Connection pool destroyed");
    }
}
