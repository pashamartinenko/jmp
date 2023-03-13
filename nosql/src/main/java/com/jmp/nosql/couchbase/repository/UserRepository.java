package com.jmp.nosql.couchbase.repository;

import com.jmp.nosql.couchbase.model.User;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends CrudRepository<User, String>
{
    List<User> findByEmail(String email);

    @Query("#{#n1ql.selectEntity} WHERE sport.sportName = $sportName AND #{#n1ql.filter}")
    List<User> findUsersBySportName(@Param("sportName") String sportName);
}
