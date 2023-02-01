package com.epam.ld.module2.testing;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class MessengerIntegrationTest
{
    @Mock
    private MailServer server;

    @Spy
    private TemplateEngine templateEngine;

    @Spy
    private Client client;

    @InjectMocks
    private Messenger messenger;

    @Test
    @Tag("IntegrationTest")
    void shouldSendMessage() {
        // GIVEN
        Template template = new Template("Text with #{tag}");
        String addresses = "user@mail.com";
        client.setAddresses(addresses);
        Map<String, String> placeholders = Map.of("tag", "value");
        doReturn(placeholders).when(server).receivePlaceholders();
        doReturn(template).when(server).receiveTemplate();

        // WHEN
        messenger.sendMessage(client);

        // THEN
        verify(client).setPlaceholders(placeholders);
        verify(server).send(addresses, "Text with value");
    }
}
