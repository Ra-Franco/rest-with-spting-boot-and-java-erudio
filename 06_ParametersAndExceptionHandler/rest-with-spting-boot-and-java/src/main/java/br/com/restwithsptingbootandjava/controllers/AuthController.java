package br.com.restwithsptingbootandjava.controllers;

import br.com.restwithsptingbootandjava.data.vo.v1.security.AccountCredentialsVO;
import br.com.restwithsptingbootandjava.services.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoint for authentication users")
public class AuthController {

    @Autowired
    private AuthServices authServices;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = authServices.signin(data);
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request");
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @Operation(summary = "Refresh token for authenticated user and returns a t oken")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,@RequestHeader("Authorization") String refreshToken){
        if (checkIfParamsIsNotNull(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request");
        var token = authServices.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid client request");
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    private static boolean checkIfParamsIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() || username == null || refreshToken.isBlank();
    }

    private static boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getPassword() == null || data.getUsername().isBlank() || data.getUsername() == null || data.getPassword().isBlank();
    }
}
