package org.jmp.spring.core.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmp.spring.core.model.Ticket;

@Data
@NoArgsConstructor
public class TicketImpl implements Ticket
{

    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    public TicketImpl(long id, long eventId, long userId, Category category, int place)
    {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }
}
