package com.example.issatc.Controller;

import com.example.issatc.Entities.Requests.AccountRequest;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Infrastructure.EntityMappers.AuthenticationRequest;
import com.example.issatc.Ports.AuthenticationServicePort;
import com.example.issatc.utilities.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationServicePort authenticationService;
    private final JwtAuthenticationService jwtAuthenticationService;
    @PostMapping("/signUp")
    ResponseEntity<String>  createAccount(@RequestBody AccountRequest accountRequest){
    boolean result=authenticationService.createAccount(accountRequest);
    if(result){
        return ResponseEntity.ok().body(jwtAuthenticationService.register(accountRequest));
    }
    return ResponseEntity.ok("bad credentials");
    }

    @PostMapping("/saveUser")
    ResponseEntity<Boolean> saveUser(@RequestBody UserRequest userRequest){
      boolean result=  this.authenticationService.saveUser(userRequest);

      if(result==true) {
          return ResponseEntity.ok(result);
      }
      return ResponseEntity.badRequest().body(result);
    }
    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {


            return ResponseEntity.ok().body(jwtAuthenticationService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("bad credentials");
        }
    }
}
