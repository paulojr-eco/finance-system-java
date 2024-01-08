package com.paulojreco.Finance.System.models;

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
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @Email
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  @NotBlank(message = "Password cannot be blank")
  @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters")
  private String password;

}
