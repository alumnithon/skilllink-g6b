package alumnithon.skilllink.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import alumnithon.skilllink.domain.userprofile.model.Profile;
import alumnithon.skilllink.domain.userprofile.model.User;

public interface ProfileRepository extends JpaRepository <Profile, Long>{
    
    boolean existsByUser(User user);

    Profile findByUser(User user);
}
