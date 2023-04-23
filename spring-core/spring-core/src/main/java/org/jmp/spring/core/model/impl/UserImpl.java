package org.jmp.spring.core.model.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jmp.spring.core.model.User;

@Entity
@Data
@NoArgsConstructor
public class UserImpl implements User
{
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;

    public UserImpl(String name, String email)
    {
        this.name = name;
        this.email = email;
    }
}
