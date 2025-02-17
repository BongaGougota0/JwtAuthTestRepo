package za.co.bonga.jwt_practice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.bonga.jwt_practice.model.AppUser;
import za.co.bonga.jwt_practice.repositories.AppUserRepository;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository appUserRepository,PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    AppUser getAppUserByEmail(String userEmail) {
        return this.appUserRepository.findAppUserByUserEmail(userEmail);
    }

    public AppUser addUser(AppUser appUserDetails) {
        appUserDetails.setPassword(passwordEncoder.encode(appUserDetails.getPassword()));
        return this.appUserRepository.save(appUserDetails);
    }
}
