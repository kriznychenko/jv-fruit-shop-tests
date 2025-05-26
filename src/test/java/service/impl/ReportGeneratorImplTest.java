package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

public class ReportGeneratorImplTest {
    @AfterEach
    void cleanUp() {
        Storage.fruits.clear();
    }

    @Test
    void testReport_Ok() {
        Storage.fruits.put("apple", 30);
        Storage.fruits.put("orange", 15);

        String expected = "fruit,amount" + System.lineSeparator()
                + "apple,30" + System.lineSeparator()
                + "orange,15" + System.lineSeparator();

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        assertEquals(expected, reportGenerator.getReport());
    }

    @Test
    void testReportWithEmptyStorage_Ok() {
        String header = "fruit,amount";

        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        assertEquals(header + System.lineSeparator(), reportGenerator.getReport());
    }
}
