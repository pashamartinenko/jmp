package org.jmp.spring.crud.model;

import java.util.Date;

/**
 * Created by maksym_govorischev.
 */
public interface Event {
    /**
     * Event id. UNIQUE.
     * @return Event Id
     */
    Long getId();
    void setId(Long id);
    String getTitle();
    void setTitle(String title);
    Date getDate();
    void setDate(Date date);
    Long getPrice();
    void setPrice(Long price);
}
