package org.jmp.spring.core.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmp.spring.core.model.Event;

import java.util.Date;

@Data
@NoArgsConstructor
public class EventImpl implements Event
{
    private long id;
    private String title;
    private Date date;

    public EventImpl(long id, String title, Date date)
    {
        this.id = id;
        this.title = title;
        this.date = date;
    }
}
