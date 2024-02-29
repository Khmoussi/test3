package com.example.issatc.Infrastructure;

import com.example.issatc.Entities.Account;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Entities.User;
import com.example.issatc.Infrastructure.EntityMappers.Role;
import com.example.issatc.Infrastructure.EntityMappers.UserMapper;
import com.example.issatc.Ports.AuthenticationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    public final JpaAuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean save(UserRequest user) {
        try {
            authenticationRepository.save(new UserMapper(user.getEmail(), user.getFirstName(), user.getLastName(),user.getRole()));
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
    this.authenticationRepository.saveAccount(account.getEmail(),passwordEncoder.encode(account.getPassword()));
    return true;

} catch(IllegalArgumentException e){
    e.printStackTrace();
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
    public int createAccountVerification(String email) {
try {
        return this.authenticationRepository.createAccountVerification(email);
}catch (Exception e){
    e.printStackTrace();
    return 0;

}
    }
    //initializing the admin or admins
    @PostConstruct
    private void postConstruct() {

        ArrayList<UserMapper> usersList= new ArrayList<UserMapper>();
        if(!authenticationRepository.existsById("admin@Gmail.com")){
            UserMapper admin=new UserMapper("admin@Gmail.com","admin","admin",passwordEncoder.encode("admin"), Role.ADMIN);
                authenticationRepository.save(admin);
        }
      for(int i =1 ;i<=20;i++){
          if(!authenticationRepository.existsById("user"+String.valueOf(i)+"@gmail.com"))
       authenticationRepository.save(new UserMapper("user"+String.valueOf(i)+"@gmail.com","user"+String.valueOf(i)+"Firstname","user"+String.valueOf(i)+"Lastname",Role.STUDENT));

      }
    }
}
