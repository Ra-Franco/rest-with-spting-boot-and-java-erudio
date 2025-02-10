package br.com.restwithsptingbootandjava.services;

import br.com.restwithsptingbootandjava.data.vo.v1.security.AccountCredentialsVO;
import br.com.restwithsptingbootandjava.data.vo.v1.security.TokenVO;
import br.com.restwithsptingbootandjava.model.User;
import br.com.restwithsptingbootandjava.repositories.UserRepository;
import br.com.restwithsptingbootandjava.security.jwt.JwtTokenProvider;
import org.apache.el.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthServices {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    public ResponseEntity signin(AccountCredentialsVO data){
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = repository.findByUsername(username);
            TokenVO tokenResponse = new TokenVO();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public ResponseEntity refreshToken(String username, String refreshToken){
            var user = repository.findByUsername(username);
            TokenVO tokenResponse = new TokenVO();
            if (user != null) {
                tokenResponse = tokenProvider.refreshToken(refreshToken);
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found");
            }
            return ResponseEntity.ok(tokenResponse);
    }
}
