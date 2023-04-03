package org.jmp.spring.core.model.impl;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmp.spring.core.model.User;

@Data
@NoArgsConstructor
public class UserImpl implements User
{
    private long id;
    private String name;
    private String email;
}
