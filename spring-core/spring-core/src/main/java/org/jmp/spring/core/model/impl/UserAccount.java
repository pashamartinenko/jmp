package org.jmp.spring.core.model.impl;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
public class UserAccount
{
    private Long balance;

    public void addBalance(Long balance) {
        this.balance += balance;
    }

    public void withdrawBalance(Long balance) {
        this.balance -= balance;
    }
}
