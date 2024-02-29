package com.example.issatc.UseCases;

import com.example.issatc.Entities.Account;
import com.example.issatc.Entities.Requests.AccountRequest;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Ports.AuthenticationRepository;
import com.example.issatc.Ports.AuthenticationServicePort;


public class AuthenticationService implements AuthenticationServicePort {
    private final AuthenticationRepository authenticationRepository;
    public  AuthenticationService(AuthenticationRepository authenticationRepository){
        this.authenticationRepository=authenticationRepository;
    }

    @Override
    public int createAccount(AccountRequest accountRequest) {
      Account  account=new Account(accountRequest.getEmail(), accountRequest.getPassword(), accountRequest.getNumTelephone());
      //verify user exists and doesn't have account
        int result =this.authenticationRepository.createAccountVerification(accountRequest.getEmail());
      if(result  ==1)
      {
            authenticationRepository.saveAccount(account);

      }
       return result;

    }

    @Override
    public Account recoverAccount(String email) {
        return authenticationRepository.recoverAccount(email);
    }

    @Override
    public boolean saveUser(UserRequest userRequest) {
       if( !this.authenticationRepository.existsByEmail(userRequest.getEmail()))
        return this.authenticationRepository.save(userRequest);
        return false;

    }
    public void test(){
        System.out.println("test failed");
    }
}
