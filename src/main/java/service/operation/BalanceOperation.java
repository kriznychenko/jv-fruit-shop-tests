package service.operation;

import db.Storage;
import model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void performOperation(FruitTransaction fruitTransaction) {
        Storage.setAmount(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
