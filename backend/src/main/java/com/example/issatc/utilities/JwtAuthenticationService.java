package com.example.issatc.utilities;

import com.example.issatc.Entities.Requests.AccountRequest;
import com.example.issatc.Infrastructure.JpaAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
    private final JpaAuthenticationRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationManager authenticationManager;
    public String register(AccountRequest accountRequest) {

        UserDetails user=repository.findById(accountRequest.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return  jwtToken;

    }
    public  String authenticate(String email,String password) throws Exception {


        try{
            //will verify if there is a user in the database with these credentials
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password)) ;

        }    catch (DisabledException e)
        {
            e.printStackTrace();

        }
        catch (BadCredentialsException e)
        {
            System.out.println("Bad Credentials ");
            e.printStackTrace();

        }


//if the authentication manager went with no problems it's time to generate The token
        var user= repository.findById(email) .orElseThrow(() -> new Exception("User not found with email: " + email));
        var jwtToken=jwtService.generateToken(user);
        return  jwtToken;   }
}
