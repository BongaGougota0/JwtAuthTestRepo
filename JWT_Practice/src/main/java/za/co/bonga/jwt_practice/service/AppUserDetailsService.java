package za.co.bonga.jwt_practice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import za.co.bonga.jwt_practice.model.AppUser;
import za.co.bonga.jwt_practice.repositories.AppUserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByUserEmail(username);
        if(appUser != null) {
            return User.builder()
                    .username(username)
                    .password(appUser.getPassword())
                    .roles(getRoles(appUser))
                    .build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }

    public String[] getRoles(AppUser appUser) {
        if(appUser.getUserRole() == null) {
            return new String[]{"USER"};
        }
        return appUser.getUserRole().split(",");
    }
}
