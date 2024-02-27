package com.example.issatc.Infrastructure;

import com.example.issatc.Entities.Account;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Entities.User;
import com.example.issatc.Infrastructure.EntityMappers.UserMapper;
import com.example.issatc.Ports.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    public final JpaAuthenticationRepository authenticationRepository;
    @Override
    public boolean save(UserRequest user) {
        try {
            authenticationRepository.save(new UserMapper(user.getEmail(), user.getFirstName(), user.getLastName()));
            return true;
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean saveAccount(Account account) {
try{
    this.authenticationRepository.saveAccount(account.getEmail(),account.getPassword());
    return true;

} catch(IllegalArgumentException e){
    return false;
}
    }

    @Override
    public Account recoverAccount(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
       return this.authenticationRepository.existsById(email);
    }

    @Override
    public boolean createAccountVerification(String email) {

        if( this.authenticationRepository.createAccountVerification(email)>0)
            return true;
        return false;
    }
}
