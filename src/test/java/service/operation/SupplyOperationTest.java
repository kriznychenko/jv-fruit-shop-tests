package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private final OperationHandler supplyOperation = new SupplyOperation();

    @Test
    void testSupplyOperation_ok() {
        Storage.add("apple", 25);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 13);
        assertDoesNotThrow(() -> supplyOperation.performOperation(fruitTransaction));
        assertEquals(38, Storage.getAmount("apple"));
    }

    @Test
    void testNegativeSupplyOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", -39);
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.performOperation(fruitTransaction));
    }

    @Test
    void testZeroSupplyOperation_ok() {
        Storage.add("pineapple", 45);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "pineapple", 0);
        assertDoesNotThrow(() -> supplyOperation.performOperation(fruitTransaction));
        assertEquals(45, Storage.getAmount("pineapple"));
    }
}
