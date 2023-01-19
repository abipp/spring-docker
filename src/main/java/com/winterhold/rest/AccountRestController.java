package com.winterhold.rest;

import com.winterhold.dto.account.RequestTokenDTO;
import com.winterhold.dto.account.ResponseTokenDTO;
import com.winterhold.service.AccountService;
import com.winterhold.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class AccountRestController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseTokenDTO post(@RequestBody RequestTokenDTO dto){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            System.out.println("Is authenticate: " + authentication.isAuthenticated());
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("Credential: " + authentication.getCredentials());
        }catch (Exception exception){
            System.out.println("Can not authenticate!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not authenticate", exception);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
        System.out.println("userDetails: " + userDetails.getUsername() + ", " + userDetails.getPassword());

        String token = jwtToken.generateToken(dto.getSubject(),dto.getUsername(), dto.getSecretKey(), dto.getAudience());
        ResponseTokenDTO response = new ResponseTokenDTO(dto.getUsername(), token);
        return response;
    }
}
