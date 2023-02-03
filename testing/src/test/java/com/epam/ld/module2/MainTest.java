package com.epam.ld.module2;


import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Tag("IntegrationTest")
class MainTest
{

    private static final String FILE_INPUT_PREFIX_FLAG = "--inputFile=";
    private static final String FILE_OUTPUT_PREFIX_FLAG = "--outputFile=";

    @Test
    void shouldWorkInConsoleModeWhenNoArgsProvided() {
        // GIVEN
        String expectedText = "Text with value%n";
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("Text with #{tag}\ntag:value".getBytes(UTF_8)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream, false, UTF_8);
        PrintStream stdout = System.out;
        System.setOut(ps);

        // WHEN
        Main.startSystem(new String[0]);

        // THEN
        System.setIn(stdin);
        System.setOut(stdout);
        String actualText = byteArrayOutputStream.toString(UTF_8);
        assertEquals(actualText, format(expectedText));
    }

    @Test
    @Disabled
    void shouldWorkInFileModeWhenBothFilesAreProvided(@TempDir Path tempDir) throws IOException {
        // GIVEN
        Path inputFile = tempDir.resolve("inputFile.txt");
        Path outputFile = tempDir.resolve("outputFile.txt");
        String templateText = "Text with #{tag1} #{tag2}\ntag1:value1 tag2:value2";
        Files.writeString(inputFile, templateText);
        String expectedText = "Text with value1 value2%n";
        String[] args = {FILE_INPUT_PREFIX_FLAG + inputFile, FILE_OUTPUT_PREFIX_FLAG + outputFile};

        // WHEN
        Main.startSystem(args);

        // THEN
        String actualText = Files.readString(outputFile);
        assertEquals(actualText, format(expectedText));
    }

    @Test
    void shouldSupportLatin1Charset() {
        // GIVEN
        String templateText = new String("Text with #{tag}\ntag:value".getBytes(UTF_8), ISO_8859_1);
        String expectedText = new String("Text with value%n".getBytes(UTF_8), ISO_8859_1);
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream(templateText.getBytes(ISO_8859_1)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream, false, ISO_8859_1);
        PrintStream stdout = System.out;
        System.setOut(ps);

        // WHEN
        Main.startSystem(new String[0]);

        // THEN
        System.setIn(stdin);
        System.setOut(stdout);
        String actualText = byteArrayOutputStream.toString(ISO_8859_1);
        assertEquals(actualText, format(expectedText));
    }

    @Test
    void shouldThrowExceptionWhenOnlyOneOfFileFlagsProvided() {
        // GIVEN
        String expectedExceptionMessage = "Either input or output file parameter is not provided";
        String[] args = { FILE_OUTPUT_PREFIX_FLAG + "test.txt"};

        // WHEN
        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                () -> Main.startSystem(args),
                "Exception is expected to be thrown when one of file parameters is not provided");

        // THEN
        assertEquals(expectedExceptionMessage, actualException.getMessage());
    }
}
