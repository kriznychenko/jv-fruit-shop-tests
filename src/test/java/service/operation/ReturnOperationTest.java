package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private final OperationHandler returnOperation = new ReturnOperation();

    @Test
    void testReturnOperation_ok() {
        Storage.add("strawberry", 5);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "strawberry", 30);
        assertDoesNotThrow(() -> returnOperation.performOperation(fruitTransaction));
        assertEquals(35, Storage.getAmount("strawberry"));
    }

    @Test
    void testNegativeReturnOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", -100);
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.performOperation(fruitTransaction));
    }

    @Test
    void testZeroReturnOperation_ok() {
        Storage.add("apple", 0);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 0);
        assertDoesNotThrow(() -> returnOperation.performOperation(fruitTransaction));
        assertEquals(0, Storage.getAmount("apple"));
    }
}
