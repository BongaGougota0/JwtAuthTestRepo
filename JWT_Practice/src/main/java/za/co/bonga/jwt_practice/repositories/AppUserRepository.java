package za.co.bonga.jwt_practice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.bonga.jwt_practice.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findAppUserByUserEmail(String userEmail);

    AppUser findAppUsersByUserRole(String userRole);
}
