package com.example.issatc.Infrastructure;

import com.example.issatc.Infrastructure.EntityMappers.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaAuthenticationRepository extends JpaRepository<UserMapper,String> {
   @Modifying
   @Transactional
   @Query("update  user u set u.password =:password where u.email =:email  ")

   void saveAccount(@Param("email") String email, @Param("password") String password);
   @Query(value = "select count(*) from user u " +
           "where u.email =:email and u.password IS NULL"
           , nativeQuery = true)
   int createAccountVerification(@Param("email") String email);



}
