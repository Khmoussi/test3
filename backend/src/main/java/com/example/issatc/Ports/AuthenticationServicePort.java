package com.example.issatc.Ports;

import com.example.issatc.Entities.Account;
import com.example.issatc.Entities.Requests.AccountRequest;
import com.example.issatc.Entities.Requests.UserRequest;

public interface AuthenticationServicePort {
    boolean createAccount(AccountRequest accountRequest);
    Account recoverAccount(String email);
    boolean saveUser(UserRequest userRequest);
}
