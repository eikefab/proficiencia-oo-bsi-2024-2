package br.edu.ifal.eikefab.account;

import br.edu.ifal.eikefab.account.exception.AccountBalanceException;
import br.edu.ifal.eikefab.account.exception.AccountNegativeTransactionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

        Assertions.assertEquals(account.balanceAfter(3), expectedBalanceValue);
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

        Assertions.assertEquals(account.getMaintenancePrice(3), expectedPrice);
    }

}
