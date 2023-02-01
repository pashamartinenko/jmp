package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.TestUtils;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TemplateEngineTest {

  private final TemplateEngine templateEngine = new TemplateEngine();

  @ParameterizedTest
  @CsvFileSource(resources = "/test-tag.csv", numLinesToSkip = 1)
  void shouldReplaceTagWithValue(String templateText, String tag, String tagValue, String expectedText) {
    // GIVEN
    Template template = new Template(templateText);
    Client client = new Client();
    Map<String, String> placeholders = Map.of(tag, tagValue);
    client.setPlaceholders(placeholders);

    // WHEN
    String actualText = templateEngine.generateMessage(template, client);

    // THEN
    assertEquals(expectedText, actualText);
  }

  @Test
  void shouldReplaceSeveralTagsWithCorrespondingValues() {
    // GIVEN
    String templateText = TestUtils.getResourceAsString("test-several-tags-template.txt");
    String expectedText = TestUtils.getResourceAsString("test-several-tags-text.txt");
    Template template = new Template(templateText);
    Client client = new Client();
    Map<String, String> placeholders = Map.of(
        "word1", "sociis",
        "word2", "pellentesque",
        "word3", "fringilla",
        "word4", "justo",
        "word5", "ante"
        );
    client.setPlaceholders(placeholders);

    // WHEN
    String actualText = templateEngine.generateMessage(template, client);

    // THEN
    assertEquals(expectedText, actualText);
  }

  @Test
  void shouldReplaceTagsWithValuesStartedWithHashtagAndBraces() {
    // GIVEN
    Template template = new Template("Text with #{tag1} and #{tag2}");
    String expectedText = "Text with #{value1} and #{value2}";
    Client client = new Client();
    Map<String, String> placeholders = Map.of(
        "tag1", "#{value1}",
        "tag2", "#{value2}"
    );
    client.setPlaceholders(placeholders);

    // WHEN
    String actualText = templateEngine.generateMessage(template, client);

    // THEN
    assertEquals(expectedText, actualText);
  }

  @TestFactory
  Stream<DynamicTest> shouldThrowExceptionWhenValueIsNotProvided() {
    // GIVEN
    Client client = new Client();
    Map<String, String> placeholders = Map.of(
            "tag1", "#{value1}"
    );
    client.setPlaceholders(placeholders);
    String expectedExceptionMessage = "Value for one of the tags is not provided";

    List<String> templateTexts = List.of("Text with #{tag1} and #{tag2} #{tag1}", "Text with #{tag2}");

    return templateTexts.stream()
            .map( Template::new)
            .map(template -> DynamicTest.dynamicTest("Generating text: " + template.getText(),
                    () -> {
                      // WHEN
                      IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                              () -> templateEngine.generateMessage(template, client),
                              "Exception is expected to be thrown when placeholder value is not provided");

                      // THEN
                      assertEquals(expectedExceptionMessage, actualException.getMessage());
                    }));
  }

  @Test
  void shouldIgnoreValuesForPlaceholdersWhichAreNotInTemplate() {
    // GIVEN
    Template template = new Template("Text with #{tag1} and #{tag2}");
    Client client = new Client();
    Map<String, String> placeholders = Map.of(
            "tag1", "value1",
            "tag2", "value2",
            "tag3", "value3"

    );
    client.setPlaceholders(placeholders);
    String expectedText = "Text with value1 and value2";

    // WHEN
    String actualText = templateEngine.generateMessage(template, client);

    // THEN
    assertEquals(expectedText, actualText);
  }
}


