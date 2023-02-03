package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;

/**
 * The type Messenger.
 */
public class Messenger {
    private final MailServer mailServer;
    private final TemplateEngine templateEngine;

    /**
     * Instantiates a new Messenger.
     *
     * @param mailServer     the mail server
     * @param templateEngine the template engine
     */
    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    /**
     * Send message.
     *
     * @param client   the client
     */
    public void sendMessage(Client client) {
        Template template = mailServer.receiveTemplate();
        client.setPlaceholders(mailServer.receivePlaceholders());
        String messageContent =
            templateEngine.generateMessage(template, client);
        mailServer.send(client.getAddresses(), messageContent);
    }
}