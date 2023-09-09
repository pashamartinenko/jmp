package org.jmp.spring.core.model.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jmp.spring.core.model.Event;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "bookings", name = "event")
@Setter
@ToString
@NoArgsConstructor
@XmlRootElement(name = "event")
public class EventImpl implements Event
{
    @Id
    @GeneratedValue
    @Getter
    private long id;
    @Getter(onMethod = @__({@XmlAttribute}))
    private String title;

    @Getter(onMethod = @__({@XmlAttribute}))
    private Date date;

    @Getter(onMethod = @__({@XmlAttribute}))
    private Long price;

    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    @Getter(onMethod = @__({@XmlTransient}))
    private List<TicketImpl> tickets;

    public EventImpl(long id, String title, Date date, Long price)
    {
        this.id = id;
        this.title = title;
        this.date = date;
        this.price = price;
    }

    public EventImpl(String title, Date date, Long price)
    {
        this.title = title;
        this.date = date;
        this.price = price;
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
        EventImpl event = (EventImpl) o;
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
