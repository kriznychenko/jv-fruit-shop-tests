package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private final OperationHandler balanceOperation = new BalanceOperation();

    @AfterEach
    void cleanUp() {
        Storage.clearStorage();
    }

    @Test
    void testBalanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "orange", 20);
        assertDoesNotThrow(() -> balanceOperation.performOperation(fruitTransaction));
        assertEquals(20, Storage.getAmount("orange"));
    }

    @Test
    void testNegativeBalanceOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", -40);
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.performOperation(fruitTransaction));
    }

    @Test
    void testZeroBalanceOperation_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 0);
        assertDoesNotThrow(() -> balanceOperation.performOperation(fruitTransaction));
        assertEquals(0, Storage.getAmount("apple"));
    }
}
