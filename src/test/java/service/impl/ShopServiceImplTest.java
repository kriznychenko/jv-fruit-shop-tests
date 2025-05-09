package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import service.DataConverter;
import service.FileReader;
import service.operation.BalanceOperation;
import service.operation.OperationHandler;
import service.operation.PurchaseOperation;
import service.operation.ReturnOperation;
import service.operation.SupplyOperation;
import strategy.OperationStrategy;

class ShopServiceImplTest {
    private static final String TEST_DATA_FILE = "src/test/resources/test.csv";

    private ShopServiceImpl shopService;

    @Test
    public void processValidTransactions_ok() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        FileReader fileReader = new FileReaderImpl();
        List<String> csvData = fileReader.read(TEST_DATA_FILE);
        DataConverter dataConverter = new DataConverterImpl();
        List<FruitTransaction> transactions = dataConverter.convertToTransaction(csvData);

        shopService.process(transactions);

        assertEquals(90, Storage.getAmount("apple"));
        assertEquals(152, Storage.getAmount("banana"));
    }
}
