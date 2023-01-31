package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Mail server class.
 */
public class MailServer
{

    private final Scanner scanner;
    private final PrintStream ps;

    public MailServer(Scanner scanner, PrintStream ps)
    {
        this.scanner = scanner;
        this.ps = ps;
    }

    public Template receiveTemplate()
    {
        return new Template(scanner.nextLine());
    }

    /**
     * Fetch placeholders using scanner.
     * @return map of pairs with placeholder and its value
     */
    public Map<String, String> receivePlaceholders()
    {
        return Arrays.stream(scanner.nextLine().split(" "))
                .map(e -> e.split(":"))
                .filter(e -> e.length == 2)
                .collect(Collectors.toMap(e -> e[0], e-> e[1]));
    }

    /**
     * Send notification.
     *
     * @param addresses  the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        ps.println(messageContent);
    }
}
