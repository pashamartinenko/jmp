package org.jmp.spring.crud.model.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jmp.spring.crud.model.Event;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(schema = "bookings", name = "event")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EventImpl implements Event
{
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;

    private Long price;


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
