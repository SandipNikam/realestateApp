package io.bootify.my_app.controller;


import io.bootify.my_app.domain.User;
import io.bootify.my_app.exception.InvalidCredentialsException;
import io.bootify.my_app.exception.UserDisabledException;
import io.bootify.my_app.payloads.JwtAuthRequest;
import io.bootify.my_app.payloads.JwtAuthResponse;
import io.bootify.my_app.security.CustomUserDetailsService;
import io.bootify.my_app.security.JwtTokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    // generate token
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> generateToken(@RequestBody JwtAuthRequest jwtRequest) throws Exception {

        try {
            try {
                this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            } catch (UsernameNotFoundException e) {
                throw new Exception("User not found");
            } catch (InvalidCredentialsException | UserDisabledException e) {
                throw new InvalidCredentialsException("Invalid credentials");
            }
            // authentication done
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
            User user = (User) this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = this.jwtTokenHelper.generateToken(userDetails,user);
            return ResponseEntity.ok(new JwtAuthResponse(token, "success"));
        } catch (Exception e) {
            return ResponseEntity.ok(new JwtAuthResponse("null", "Invalid credentials"));
        }
    }

    private void authenticate(String username, String password) throws Exception, UserDisabledException, InvalidCredentialsException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }
}
