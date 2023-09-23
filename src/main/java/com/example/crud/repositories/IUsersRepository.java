package com.example.crud.repositories;

import com.example.crud.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUsersRepository extends JpaRepository<Users,Long> {
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    public Users getUsersByUsername(@Param("username")String username);
}
