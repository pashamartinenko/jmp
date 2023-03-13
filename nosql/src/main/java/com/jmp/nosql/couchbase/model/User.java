package com.jmp.nosql.couchbase.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import java.time.LocalDate;
import java.util.Map;

@Document
@JsonInclude(Include.NON_NULL)
@Data
public class User
{
    @Id
    @Field
    private String id;

    @Field
    private String email;

    @Field
    private String fullName;

    @Field
    private LocalDate birthDate;

    @Field
    private Gender gender;

    @Field
    private Sport sport;

    @Field

    private Map<String, Object> fields;
}
