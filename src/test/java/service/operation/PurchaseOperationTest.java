package service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private final OperationHandler purchaseOperation = new PurchaseOperation();

    @AfterEach
    void cleanUp() {
        Storage.clearStorage();
    }

    @Test
    void testPurchaseOperation_ok() {
        Storage.add("orange", 50);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "orange", 20);
        assertDoesNotThrow(() -> purchaseOperation.performOperation(fruitTransaction));
        assertEquals(30, Storage.getAmount("orange"));
    }

    @Test
    void testNegativePurchaseOperation_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", -10);
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.performOperation(fruitTransaction));
    }

    @Test
    void testPurchaseOperationWithNotEnoughFruitsAmount_notOk() {
        Storage.fruits.put("apple", 5);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.performOperation(fruitTransaction));
        assertEquals("Not enough apple in the store.", ex.getMessage());
    }
}
