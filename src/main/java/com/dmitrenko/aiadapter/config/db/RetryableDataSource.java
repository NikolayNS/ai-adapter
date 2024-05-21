package com.dmitrenko.aiadapter.config.db;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequiredArgsConstructor
public class RetryableDataSource extends AbstractDataSource {

    private final DataSource delegate;

    @Override
    @Retryable(retryFor = Throwable.class, maxAttempts = 99999999, backoff = @Backoff(delay = 3000))
    public Connection getConnection() throws SQLException {
      return delegate.getConnection();
    }

    @Override
    @Retryable(retryFor = Throwable.class, maxAttempts = 99999999, backoff = @Backoff(delay = 3000))
    public Connection getConnection(String username, String password) throws SQLException {
      return delegate.getConnection(username, password);
    }
}
