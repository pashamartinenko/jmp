package org.jmp.spring.core.model.impl;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserAccount
{
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
