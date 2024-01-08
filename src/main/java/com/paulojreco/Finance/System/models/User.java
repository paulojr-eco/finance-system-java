package com.paulojreco.Finance.System.models;

import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;

  @NotEmpty(message = "Name cannot be empty")
  @NotBlank(message = "Name cannot be blank")
  @Size(min = 4, max = 255, message = "Name must be between 4 and 255 characters")
  private String name;

  @Email
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  @NotBlank(message = "Password cannot be blank")
  @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
  private String password;

  private Date createdAt;

  @Enumerated(EnumType.STRING)
  private Role role;

  @PrePersist
  public void onPrePersist() {
    if (this.createdAt == null) {
      this.createdAt = new Date();
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
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
