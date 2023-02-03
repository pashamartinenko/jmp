package com.epam.ld.module2.testing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestUtils {

  private TestUtils() { }

  public static String getResourceAsString(String path) {
    ClassLoader classLoader = TestUtils.class.getClassLoader();
    try {
      File file = new File(classLoader.getResource(path).getFile());
      return Files.readString(file.toPath());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
