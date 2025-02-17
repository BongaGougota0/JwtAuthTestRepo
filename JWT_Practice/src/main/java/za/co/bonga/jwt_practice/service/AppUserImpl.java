package za.co.bonga.jwt_practice.service;

import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.bonga.jwt_practice.model.AppUser;
import za.co.bonga.jwt_practice.repositories.AppUserRepository;
import za.co.bonga.jwt_practice.service.interfaces.AppUserInt;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserImpl implements AppUserInt {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> getAUserByEmail(String userEmail) {
        AppUser appUser = appUserRepository.findAppUserByUserEmail(userEmail);
        if(appUser == null){
            throw new ResourceNotFoundException("User not found");
        }
        return Optional.of(appUser);
    }

    @Override
    public Optional<AppUser> getAUserId(int userId) {
        AppUser appUser = appUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return Optional.ofNullable(appUser);
    }

    @Override
    public List<AppUser> getUsersByRole(String userRole) {
        return appUserRepository.findAll()
                .stream()
                .filter(existingUser -> existingUser.getUserRole().equalsIgnoreCase(userRole))
                .collect(Collectors.toList());
    }
}
