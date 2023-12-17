package com.example.taskmangement.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private	Integer id;
@NotEmpty
@Size(min = 4,message = "name should have minimum 4 characters")
private String name;
@Email(message = "Email Address is not valid")
@Column(unique = true)
private String email;
@NotEmpty
@Size(min = 4,message = "password must have min of 4 and max of 10 characters")
private String password;


@Enumerated(EnumType.STRING)
private Role role;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {

	return List.of(new SimpleGrantedAuthority(role.name()));
}

@Override
public String getUsername() {
	return this.email;
}
@Override
public String getPassword() {
	return this.password;
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
