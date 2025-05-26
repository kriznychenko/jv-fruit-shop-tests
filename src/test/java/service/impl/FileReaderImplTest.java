package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class FileReaderImplTest {
    private static final String VALID_FILE = "src/test/resources/test.csv";
    private static final String EMPTY_FILE = "src/test/resources/empty.csv";
    private static final String NON_EXISTENT_FILE = "src/test/resources/nonExistentFile.csv";

    private FileReaderImpl fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    public void readValidFile_ok() {
        List<String> expected = Arrays.asList(
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        );

        List<String> actual = fileReader.read(VALID_FILE);
        assertEquals(expected, actual);
    }

    @Test
    public void readInvalidFile_notOk() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(NON_EXISTENT_FILE));
        assertEquals("File not found: " + NON_EXISTENT_FILE, exception.getMessage());
    }

    @Test
    public void readEmptyFile_ok() {
        List<String> lines = fileReader.read(EMPTY_FILE);
        assertTrue(lines.isEmpty(), "Expected the file to be empty");
    }
}
