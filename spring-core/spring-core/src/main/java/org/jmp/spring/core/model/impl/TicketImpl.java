package org.jmp.spring.core.model.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jmp.spring.core.model.Ticket;

import java.util.Objects;

@Entity
@Table(schema = "bookings", name = "ticket")
@Getter
@Setter
@ToString(exclude = {"event", "user"})
@NoArgsConstructor
public class TicketImpl implements Ticket
{

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private Category category;
    private int place;

    @Transient
    private long eventId;
    @Transient
    private long userId;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
    private EventImpl event;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserImpl user;

    public TicketImpl(Category category, int place, EventImpl event, UserImpl user)
    {
        this.category = category;
        this.place = place;
        this.event = event;
        this.user = user;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
        {
            return false;
        }
        TicketImpl ticket = (TicketImpl) o;
        return Objects.equals(getId(), ticket.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
