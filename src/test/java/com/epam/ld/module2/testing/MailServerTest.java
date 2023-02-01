package com.epam.ld.module2.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.template.Template;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

@ExtendWith(MockitoExtension.class)
class MailServerTest {

    @Mock
    private Scanner scanner;

    @Mock
    private PrintStream ps;

    @InjectMocks
    private MailServer mailServer;

    @Test
    void shouldReceiveTemplateText() {
        // GIVEN
        String templateText = "template text";
        Template expectedTemplate = new Template(templateText);
        doReturn(templateText).when(scanner).nextLine();

        // WHEN
        Template actualTemplate = mailServer.receiveTemplate();

        // THEN
        assertEquals(expectedTemplate, actualTemplate);
    }

    @Test
    void shouldReceivePlaceholders() {
        // GIVEN
        String placeholdersText = "tag1:value1 tag2:value2 tag3:value3";
        Map<String, String> expectedPlaceholders = Map.of(
                "tag1", "value1",
                "tag2", "value2",
                "tag3", "value3"

        );
        doReturn(placeholdersText).when(scanner).nextLine();

        // WHEN
        Map<String, String> actualPlaceholders = mailServer.receivePlaceholders();

        // THEN
        assertEquals(expectedPlaceholders, actualPlaceholders);
    }

    @Test
    void shouldSendMessage() {
        // GIVEN
        String messageContent = "";
        String addresses = "";

        // WHEN
        mailServer.send(addresses, messageContent);

        // THEN
        verify(ps, times(1)).println(messageContent);
    }
}
