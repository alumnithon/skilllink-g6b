package alumnithon.skilllink.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository <VerificationToken, Long>{

    VerificationToken findByToken(String token);

    @Modifying
    @Query("DELETE FROM VerificationToken t WHERE t.user = :user")
    void deleteByUser(@Param("user") User user);
}
