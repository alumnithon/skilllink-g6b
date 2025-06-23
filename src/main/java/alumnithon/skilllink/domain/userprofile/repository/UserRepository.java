package alumnithon.skilllink.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alumnithon.skilllink.domain.userprofile.model.User;

public interface UserRepository extends JpaRepository <User, Long>{

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User findByEmailAndEnabledTrue(String username);

}
