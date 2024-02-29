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

   @Query(value = " select sub.result from ( select case when email =:email and password is NULL then 1 " +
           "when email =:email and password is not NULL then 2 " +
           "else 3 " +
           "end as result " +
           "from user ) as sub   " +
           "where 1  in (sub.result) or 2 in (sub.result) ;"
           , nativeQuery = true)
   int createAccountVerification(@Param("email") String email);



}
