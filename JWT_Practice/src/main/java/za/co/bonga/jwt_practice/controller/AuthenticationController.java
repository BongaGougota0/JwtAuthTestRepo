package za.co.bonga.jwt_practice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import za.co.bonga.jwt_practice.dto.AppResponse;
import za.co.bonga.jwt_practice.dto.LoginDetails;
import za.co.bonga.jwt_practice.jwt.JWTService;
import za.co.bonga.jwt_practice.model.AppUser;
import za.co.bonga.jwt_practice.service.AppUserDetailsService;
import za.co.bonga.jwt_practice.service.AppUserImpl;
import za.co.bonga.jwt_practice.service.AppUserService;
import za.co.bonga.jwt_practice.service.interfaces.AppUserInt;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private final AppUserService appUserService;
    private final AppUserInt appUserInt;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService appUserDetailsService;

    public AuthenticationController(AppUserService appUserService, AuthenticationManager authentication,
                                    AppUserImpl appUserInt, JWTService jwtService, AppUserDetailsService appUserDetailsService) {
        this.appUserService = appUserService;
        this.appUserInt = appUserInt;
        this.jwtService = jwtService;
        this.authenticationManager = authentication;
        this.appUserDetailsService = appUserDetailsService;
    }

    @GetMapping("home/")
    public ResponseEntity<AppResponse> home(){
        return ResponseEntity.ok().body(new AppResponse("Welcome home",
                HttpStatus.ACCEPTED.getReasonPhrase(), LocalDateTime.now()));
    }

    @GetMapping("dashboard/")
    public ResponseEntity<AppResponse> dashboard(){
        AppResponse response = new AppResponse("Welcome to the dashboard",
                HttpStatus.ACCEPTED.getReasonPhrase(), LocalDateTime.now());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("all/")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok(appUserInt.getAllAppUsers());
    }

    @GetMapping("getbyid")
    public ResponseEntity<AppUser> getUserById(@RequestParam("id") Integer id){
        return ResponseEntity.ok().body(appUserInt.getAUserId(id).get());
    }

    @GetMapping("getbyemail")
    public ResponseEntity<AppUser> getUserByEmailAddress(@RequestParam("email") String userEmail){
        return ResponseEntity.ok().body(appUserInt.getAUserByEmail(userEmail).get());
    }
    
    @PostMapping("register/")
    public ResponseEntity<AppResponse> register(@RequestBody AppUser appUserDetails){
        AppUser appUser = appUserService.addUser(appUserDetails);
        if(appUser != null){
            AppResponse response = new AppResponse("Welcome you are registered.",
                    HttpStatus.ACCEPTED.getReasonPhrase(), LocalDateTime.now());
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(new AppResponse("User not created/exists.",
                    HttpStatus.ACCEPTED.getReasonPhrase(), LocalDateTime.now()));
        }
    }

    @PostMapping("login/")
    public ResponseEntity<AppResponse> login(@RequestBody LoginDetails loginDetails){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDetails.userEmail(),
                        loginDetails.userPassword()));
        if(authentication.isAuthenticated()){
            String jwt = jwtService.generateToken(
                    appUserDetailsService.loadUserByUsername(loginDetails.userEmail()));
            AppResponse response = new AppResponse("Welcome you are logged in. jwt=" + jwt,
                    HttpStatus.ACCEPTED.getReasonPhrase(), LocalDateTime.now());
            return ResponseEntity.ok().body(response);
        }
        return ResponseEntity.ok().body(new AppResponse("Oops", HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                LocalDateTime.now()));
    }
}
