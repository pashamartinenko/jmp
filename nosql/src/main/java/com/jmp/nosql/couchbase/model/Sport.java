package com.jmp.nosql.couchbase.model;

import java.util.Objects;

public class Sport
{
    private String id;
    private String sportName;
    private String sportProficiency;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSportName()
    {
        return sportName;
    }

    public void setSportName(String sportName)
    {
        this.sportName = sportName;
    }

    public String getSportProficiency()
    {
        return sportProficiency;
    }

    public void setSportProficiency(String sportProficiency)
    {
        this.sportProficiency = sportProficiency;
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
        Sport sport = (Sport) o;
        return Objects.equals(id, sport.id) && Objects.equals(sportName, sport.sportName) && Objects.equals(sportProficiency, sport.sportProficiency);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, sportName, sportProficiency);
    }
}
