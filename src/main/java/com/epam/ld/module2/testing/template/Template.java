package com.epam.ld.module2.testing.template;

import java.util.Objects;

/**
 * The type Template.
 */
public class Template {
  private final String text;

  public Template(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null || getClass() != obj.getClass())
    {
      return false;
    }
    Template template = (Template) obj;
    return Objects.equals(text, template.text);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(text);
  }
}
