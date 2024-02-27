package com.example.issatc.Ports;

import com.example.issatc.Entities.Account;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Entities.User;

public interface AuthenticationRepository {
    boolean save(UserRequest user);
    User  findByEmail(String email);

    boolean saveAccount(Account account);

    Account recoverAccount(String email);

    boolean existsByEmail(String email);

    boolean createAccountVerification(String email);
}
