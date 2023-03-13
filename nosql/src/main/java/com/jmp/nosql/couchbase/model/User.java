package com.jmp.nosql.couchbase.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

@Document
@JsonInclude(Include.NON_NULL)
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

    public User() {

    }

    public User(String id, String email, String fullName, LocalDate birthDate, Gender gender)
    {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Sport getSport()
    {
        return sport;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(fullName, user.fullName) && Objects.equals(birthDate, user.birthDate) && gender == user.gender;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, email, fullName, birthDate, gender);
    }
}
