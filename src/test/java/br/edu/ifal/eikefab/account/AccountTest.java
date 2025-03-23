package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.exceptions.AccountBalanceException;
import br.edu.ifal.eikefab.account.exceptions.AccountInvalidMonthException;
import br.edu.ifal.eikefab.account.exceptions.AccountNegativeTransactionException;
import br.edu.ifal.eikefab.account.impl.CheckingAccount;
import br.edu.ifal.eikefab.account.impl.SavingsAccount;
import br.edu.ifal.eikefab.transaction.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountTest {

    private static final UUID RANDOM_UNIQUE_ID = UUID.randomUUID();
    private static Account ACCOUNT;

    @BeforeAll
    public static void invalidCreation() {
        Assertions.assertThrows(
                NullPointerException.class, () ->
                        Account.builder()
                                .uniqueId(null)
                                .checking()
        );

        Assertions.assertThrows(
                NullPointerException.class, () ->
                        Account.builder()
                                .name(null)
                                .checking()
        );

        Assertions.assertThrows(
                NullPointerException.class, () ->
                        Account.builder()
                                .email(null)
                                .checking()
        );

        Assertions.assertThrows(
                NullPointerException.class, () ->
                        Account.builder()
                                .transactions(null)
                                .checking()
        );

        Assertions.assertThrows(
                NullPointerException.class, () ->
                        Account.builder()
                                .balance(-1)
                                .checking()
        );

        final AccountBuilder accountBuilder = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(1)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>());

        Assertions.assertDoesNotThrow(() -> ACCOUNT = accountBuilder.savings());
        Assertions.assertDoesNotThrow(() -> ACCOUNT = accountBuilder.checking());
    }

    @Test
    public void negativeWithdraw() {
        Assertions.assertThrows(AccountNegativeTransactionException.class, () -> ACCOUNT.withdraw(-1));
    }

    @Test
    public void negativeBalanceAfterWithdraw() {
        Assertions.assertThrows(AccountBalanceException.class, () -> ACCOUNT.withdraw(2));
    }

    @Test
    public void savingAccountsCalculate() {
        final SavingsAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .savings();

        final double expectedBalanceValue = SavingsAccount.MONTH_GOV_INTEREST_RATE * 3 * account.getBalance();

        Assertions.assertEquals(expectedBalanceValue, account.balanceAfter(3));
    }

    @Test
    public void checkingAccountCalculate() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        final double expectedPrice = CheckingAccount.CHECKING_ACCOUNT_MONTH_MAINTENANCE_PRICE * 3;

        Assertions.assertEquals(expectedPrice, account.getMaintenancePrice(3));
    }

    @Test
    public void localeShouldBePtBr() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(1500.43)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        final String expectedLocale = "1.500,43";

        Assertions.assertEquals(expectedLocale, account.getLocaleBalance());
    }

    @Test
    public void shouldThrowInvalidMonthsForMaintenancePrice() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        Assertions.assertThrows(AccountInvalidMonthException.class, () -> account.getMaintenancePrice(0));
    }

    @Test
    public void typeShouldBeChecking() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        Assertions.assertEquals(AccountType.CHECKING, account.getAccountType());
    }

    @Test
    public void typeShouldBeSavings() {
        final SavingsAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .savings();

        Assertions.assertEquals(AccountType.SAVINGS, account.getAccountType());
    }

    @Test
    public void shouldThrowInvalidMonthsForSaving() {
        final SavingsAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .savings();

        Assertions.assertThrows(AccountInvalidMonthException.class, () -> account.balanceAfter(0));
    }

    @Test
    public void withdraw() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        Assertions.assertThrows(AccountNegativeTransactionException.class, () -> account.withdraw(-1));
        Assertions.assertThrows(AccountBalanceException.class, () -> account.withdraw(20));
        Assertions.assertDoesNotThrow(() -> account.withdraw(15));
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void deposit() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        Assertions.assertThrows(AccountNegativeTransactionException.class, () -> account.deposit(-1));
        Assertions.assertDoesNotThrow(() -> account.deposit(15));
        Assertions.assertEquals(30, account.getBalance());
    }

    @Test
    public void getters() {
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(new ArrayList<>())
                .checking();

        Assertions.assertEquals(RANDOM_UNIQUE_ID, account.getUniqueId());
        Assertions.assertEquals("Eike", account.getName());
        Assertions.assertEquals("eike@email.com", account.getEmail());
        Assertions.assertEquals(15, account.getBalance());
        Assertions.assertTrue(account.getTransactions().isEmpty());
    }

    @Test
    public void manipulateTransactions() {
        final List<Transaction> transactions = new ArrayList<>();
        final CheckingAccount account = Account
                .builder()
                .uniqueId(RANDOM_UNIQUE_ID)
                .balance(15)
                .email("eike@email.com")
                .name("Eike")
                .transactions(transactions)
                .checking();

        final List<Transaction> accountTransaction = account.getTransactions();

        final Transaction dummy = new Transaction(
                UUID.randomUUID(),
                account,
                account,
                System.currentTimeMillis(),
                true,
                10
        );

        accountTransaction.add(dummy);

        Assertions.assertNotEquals(transactions.size(), accountTransaction.size());
        Assertions.assertTrue(account.getTransactions().isEmpty());

        account.addTransaction(dummy);

        Assertions.assertThrows(NullPointerException.class, () -> account.addTransaction(null));
        Assertions.assertFalse(account.getTransactions().isEmpty());
    }

}
