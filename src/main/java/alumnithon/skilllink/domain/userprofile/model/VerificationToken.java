package alumnithon.skilllink.domain.userprofile.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="verification_token")
public class VerificationToken {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String token;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(nullable = false)
        private LocalDateTime expiryDate;

        public boolean isExpired() {
                return LocalDateTime.now().isAfter(expiryDate);
        }

        //---Getter---

        public Long getId() {
                return id;
        }

        public String getToken() {
                return token;
        }

        public User getUser() {
                return user;
        }

        public LocalDateTime getExpiryDate() {
                return expiryDate;
        }

        //---Setter---
        
        public void setId(Long id) {
            this.id = id;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public void setExpiryDate(LocalDateTime expiryDate) {
            this.expiryDate = expiryDate;
        }

       
        //---NoArgsConstructor---
        public VerificationToken(){}

        //---AllArgsConstructor---
        public VerificationToken( String token, User user, LocalDateTime expiryDate) {
                this.token = token;
                this.user = user;
                this.expiryDate = expiryDate;
        }

       
}
