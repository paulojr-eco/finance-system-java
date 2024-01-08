package com.paulojreco.Finance.System.resources;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paulojreco.Finance.System.models.AuthenticationRequest;
import com.paulojreco.Finance.System.models.User;
import com.paulojreco.Finance.System.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Authentication {

  private final AuthenticationService authenticationService;

  @PostMapping("/register")
  @ResponseBody
  public ResponseEntity<?> register(
      @Valid @RequestBody User request, Errors errors) {
    System.err.println("Theres errors: " + errors.hasErrors());
    if (!errors.hasErrors()) {
      return ResponseEntity.ok(authenticationService.register(request));
    }
    return ResponseEntity.badRequest().body(
        errors.getAllErrors()
            .stream()
            .map(msg -> msg.getDefaultMessage())
            .collect(Collectors.joining(",")));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(
      @Valid @RequestBody AuthenticationRequest request, Errors errors) {
    if (!errors.hasErrors()) {
      return ResponseEntity.ok(authenticationService.login(request));
    }
    Map<String, String> errorsMap = errors.getAllErrors()
        .stream()
        .collect(Collectors.toMap(
            msg -> msg.getCodes()[0],
            msg -> msg.getDefaultMessage()));
    return ResponseEntity.badRequest().body(errorsMap);
  }

}
