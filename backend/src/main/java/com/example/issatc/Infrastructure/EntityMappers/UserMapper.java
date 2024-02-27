package com.example.issatc.Infrastructure.EntityMappers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity(name = "user")
@AllArgsConstructor
public class UserMapper implements UserDetails {
    @Id
    private String email;

    private String firstName;
    private String lastName;
    private String password;
    public UserMapper() {

    }

    public UserMapper(String email, String name, String lastname) {
        this.email = email;
        this.firstName = name;
        this.lastName = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
