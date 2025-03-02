package br.edu.ifal.eikefab.account;

import java.sql.*;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {

    private static final AccountRepositoryImpl INSTANCE = new AccountRepositoryImpl();

    private Connection connection;

    private AccountRepositoryImpl() {
        try {
            connect();
            init();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");

        this.connection = DriverManager.getConnection("jdbc:h2:mem:");
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new IllegalStateException("A conexão não está estabelecida.");
        }

        connection.close();
    }

    @Override
    public boolean isConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception exception) {
            return false;
        }
    }

    @Override
    public void init() {
        final String query =
                "create table `accounts` (" +
                "`unique_id` char(36) not null," +
                "`name` varchar(255) not null," +
                "`email` varchar(255) not null," +
                "`balance` double precision not null," +
                "`savings` boolean not null," +
                "constraint `pk_accounts` primary key (`unique_id`)" +
                ");";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Set<Account> findAllAccounts() {
        final Set<Account> accounts = new HashSet<>();
        final String query = "select `unique_id`, `name`, `email`, `balance`, `savings` from `accounts`;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final AccountBuilder builder = Account.builder()
                            .uniqueId(UUID.fromString(resultSet.getString("unique_id")))
                            .name(resultSet.getString("name"))
                            .email(resultSet.getString("email"))
                            .balance(resultSet.getDouble("balance"))
                            .transactions(new ArrayList<>());

                    // TODO: impl transactions

                    final Account account;

                    if (resultSet.getBoolean("savings")) {
                        account = builder.savings();
                    } else {
                        account = builder.checking();
                    }

                    accounts.add(account);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Set<Account> findAllAccountsByName(String name) {
        final Set<Account> accounts = new HashSet<>();
        final String query = "select `unique_id`, `name`, `email`, `balance`, `savings` from `accounts` where `name` = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final AccountBuilder builder = Account.builder()
                            .uniqueId(UUID.fromString(resultSet.getString("unique_id")))
                            .name(resultSet.getString("name"))
                            .email(resultSet.getString("email"))
                            .balance(resultSet.getDouble("balance"))
                            .transactions(new ArrayList<>());

                    // TODO: impl transactions

                    final Account account;

                    if (resultSet.getBoolean("savings")) {
                        account = builder.savings();
                    } else {
                        account = builder.checking();
                    }

                    accounts.add(account);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Optional<Account> findAccountByUniqueId(UUID uuid) {
        final String query = "select `unique_id`, `name`, `email`, `balance`, `savings` from `accounts` where `unique_id` = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uuid.toString());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    final AccountBuilder builder = Account.builder()
                            .uniqueId(UUID.fromString(resultSet.getString("unique_id")))
                            .name(resultSet.getString("name"))
                            .email(resultSet.getString("email"))
                            .balance(resultSet.getDouble("balance"))
                            .transactions(new ArrayList<>());

                    // TODO: implement transactions!!!

                    final Account account;

                    if (resultSet.getBoolean("savings")) {
                        account = builder.savings();
                    } else {
                        account = builder.checking();
                    }

                    return Optional.of(account);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void createAccount(Account account) {
        final String query =
                "insert into `accounts` (`unique_id`, `name`, `email`, `balance`, `savings`) " +
                "values (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account.getUniqueId().toString());
            statement.setString(2, account.getName());
            statement.setString(3, account.getEmail());
            statement.setDouble(4, account.getBalance());
            statement.setBoolean(5, account.getAccountType() == AccountType.SAVINGS);

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        final String query = "update `accounts` set `balance` = ? where `unique_id` = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, account.getBalance());
            statement.setString(2, account.getUniqueId().toString());

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(UUID uniqueId) {
        final String query = "delete from `accounts` where `unique_id` = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, uniqueId.toString());

            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static AccountRepositoryImpl getInstance() {
        return INSTANCE;
    }

}
