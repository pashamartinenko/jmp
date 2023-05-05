package org.jmp.spring.core.model.impl;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.jmp.spring.core.model.User;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "bookings", name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class UserImpl implements User
{

    public UserImpl(long id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;

    @Embedded
    private UserAccount userAccount;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<TicketImpl> tickets;

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
        UserImpl user = (UserImpl) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
