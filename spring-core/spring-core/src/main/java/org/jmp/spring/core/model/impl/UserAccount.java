package org.jmp.spring.core.model.impl;

import jakarta.persistence.Embeddable;
import jakarta.xml.bind.annotation.XmlValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@NoArgsConstructor
public class UserAccount
{
    @Getter(onMethod = @__({@XmlValue}))
    private Long balance;

    public UserAccount(Long balance)
    {
        this.balance = balance;
    }

    public void addBalance(Long balance) {
        this.balance += balance;
    }

    public void withdrawBalance(Long balance) {
        this.balance -= balance;
    }
}
