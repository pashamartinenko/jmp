package org.jmp.spring.core.model.impl;

import jakarta.persistence.Embedded;
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
import org.jmp.spring.core.model.User;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "bookings", name = "user")
@ToString
@NoArgsConstructor
@XmlRootElement
@Setter
public class UserImpl implements User
{
    @Id
    @GeneratedValue
    @Getter
    private long id;

    @Getter(onMethod = @__({@XmlAttribute}))
    private String name;

    @Getter(onMethod = @__({@XmlAttribute}))
    private String email;

    @Embedded
    @Getter(onMethod = @__({@XmlAttribute(name = "balance")}))
    private UserAccount userAccount;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Getter(onMethod = @__({@XmlTransient}))
    private List<TicketImpl> tickets;

    public UserImpl(long id, String name, String email, UserAccount userAccount)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userAccount = userAccount;
    }

    public UserImpl(String name, String email, UserAccount userAccount)
    {
        this.name = name;
        this.email = email;
        this.userAccount = userAccount;
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
        UserImpl user = (UserImpl) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}
