package com.example.issatc.Controller;

import com.example.issatc.Entities.Requests.AccountRequest;
import com.example.issatc.Entities.Requests.UserRequest;
import com.example.issatc.Infrastructure.EntityMappers.AuthenticationRequest;
import com.example.issatc.Infrastructure.EntityMappers.AuthenticationResponse;
import com.example.issatc.Infrastructure.EntityMappers.RefreshTokenResponse;
import com.example.issatc.Infrastructure.EntityMappers.RegisterResponse;
import com.example.issatc.Ports.AuthenticationServicePort;
import com.example.issatc.utilities.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationServicePort authenticationService;
    private final JwtAuthenticationService jwtAuthenticationService;
    @Operation(
            responses = {
                    @ApiResponse(
                            description = "Bad Credentials",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "user not allowed to authenticate",
                            responseCode = "403"

                    ),
                    @ApiResponse(
                            description = "account already exists",
                            responseCode = "202"

                    ),
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterResponse.class))
                    )

            }
    )
    @PostMapping("/signUp")
    ResponseEntity<?>  createAccount(@RequestBody AccountRequest accountRequest){
        //true user exists doesn't have account
        //false
    int result=authenticationService.createAccount(accountRequest);
    switch (result){
        case 1:         return ResponseEntity.ok().body(jwtAuthenticationService.register(accountRequest));

        case 2: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad credentials");

        case 0: return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
        default:return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unknow error");

    }

    }

    @Operation(
            responses = {
                    @ApiResponse(
                            description = "Bad Credentials",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "user not allowed to authenticate",
                            responseCode = "403"

                    ),
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
                    )

            }
    )

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {


            return ResponseEntity.ok().body(jwtAuthenticationService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }
        catch (DisabledException e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user not allowed to authenticate");


        }
        catch (BadCredentialsException e)
        {
            System.out.println("Bad Credentials ");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad credentials");


        }
    }

    @Operation(
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshTokenResponse.class))
                    )

            }
    )
    //RefreshToken
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        jwtAuthenticationService.refreshToken(request, response);
    }



    @PostMapping("/saveUser")
    ResponseEntity<Boolean> saveUser(@RequestBody UserRequest userRequest){
        boolean result=  this.authenticationService.saveUser(userRequest);

        if(result==true) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().body(result);
    }
}
