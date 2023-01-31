package com.epam.ld.module2.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.template.Template;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    void shouldReceiveTemplateText() {
        String templateText = "template text";
        Template expectedTemplate = new Template(templateText);
        MailServer mailServer = new MailServer(scanner, ps);
        doReturn(templateText).when(scanner).nextLine();

        Template actualTemplate = mailServer.receiveTemplate();

        assertEquals(expectedTemplate, actualTemplate);
    }

    @Test
    void shouldReceivePlaceholders() {
        String placeholdersText = "tag1:value1 tag2:value2 tag3:value3";
        Map<String, String> expectedPlaceholders = Map.of(
                "tag1", "value1",
                "tag2", "value2",
                "tag3", "value3"

        );
        MailServer mailServer = new MailServer(scanner, ps);
        doReturn(placeholdersText).when(scanner).nextLine();

        Map<String, String> actualPlaceholders = mailServer.receivePlaceholders();

        assertEquals(expectedPlaceholders, actualPlaceholders);
    }

    @Test
    void shouldSendMessage() {
        String messageContent = "";
        String addresses = "";
        MailServer mailServer = new MailServer(scanner, ps);

        mailServer.send(addresses, messageContent);

        verify(ps, times(1)).println(messageContent);
    }
}
