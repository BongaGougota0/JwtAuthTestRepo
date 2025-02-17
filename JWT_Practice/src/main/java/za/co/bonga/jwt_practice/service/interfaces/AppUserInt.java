package za.co.bonga.jwt_practice.service.interfaces;

import za.co.bonga.jwt_practice.model.AppUser;
import java.util.List;
import java.util.Optional;

public interface AppUserInt {
    List<AppUser> getAllAppUsers();
    Optional<AppUser> getAUserByEmail(String userEmail);
    Optional<AppUser> getAUserId(int userId);
    List<AppUser> getUsersByRole(String userRole);
}
