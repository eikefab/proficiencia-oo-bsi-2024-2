package br.edu.ifal.eikefab.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTypeTest {

    @Test
    public void mustBePoupanca() {
        Assertions.assertEquals("POUPANÇA", AccountType.SAVINGS.getFancyName());
    }

    @Test
    public void mustBeCorrente() {
        Assertions.assertEquals("CORRENTE", AccountType.CHECKING.getFancyName());
    }

}
