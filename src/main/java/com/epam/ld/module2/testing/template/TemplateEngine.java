package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * The type Template engine.
 */
public class TemplateEngine {

    private static final String PLACEHOLDER_REGEX = "#\\{[^#{}]+}";

    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {
        String templateText = template.getText();
        Map<String, String> clientPlaceholders = client.getPlaceholders();
        Set<String> templatePlaceholders = findTemplatePlaceholders(template);
        if (!clientPlaceholders.keySet().containsAll(templatePlaceholders)) {
            throw new IllegalArgumentException("Value for one of the tags is not provided");
        }
        for (Map.Entry<String, String> tag: clientPlaceholders.entrySet()) {
            String tagKey = tag.getKey();
            templateText = templateText.replace(format("#{%s}", tagKey), tag.getValue());
        }

        return templateText;
    }

    private Set<String> findTemplatePlaceholders(Template template) {
        Set<String> placeholders = new HashSet<>();
        String templateText = template.getText();
        Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        Matcher matcher = pattern.matcher(templateText);
        while (matcher.find()) {
            placeholders.add(templateText.substring(matcher.start() + 2, matcher.end() - 1));
        }
        return placeholders;
    }
}
