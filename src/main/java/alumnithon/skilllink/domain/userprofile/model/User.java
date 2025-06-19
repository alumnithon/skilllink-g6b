package alumnithon.skilllink.domain.userprofile.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Email
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    private String image_url;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    // --- Métodos de UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // --- Getter ---

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public @Email String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getImage_url() {
        return image_url;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setEmail(String email2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEmail'");
    }

    public void setName(String name2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }

    public void setPassword(String hasedPasword) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setPassword'");
    }

    public void setRole(Role roleUser) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setRole'");
    }

    public void setImage_url(String image_url2) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setImage_url'");
    }

    public void setEnabled(boolean b) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setEnabled'");
    }
}
