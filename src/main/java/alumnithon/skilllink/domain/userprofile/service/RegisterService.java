package alumnithon.skilllink.domain.userprofile.service;

import alumnithon.skilllink.shared.exception.AppException;
import alumnithon.skilllink.shared.exception.ErrorCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import alumnithon.skilllink.domain.userprofile.dto.RegisterRequestDto;
import alumnithon.skilllink.domain.userprofile.model.Role;
import alumnithon.skilllink.domain.userprofile.model.User;
import alumnithon.skilllink.domain.userprofile.model.VerificationToken;
import alumnithon.skilllink.domain.userprofile.repository.UserRepository;
import alumnithon.skilllink.domain.userprofile.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional

public class RegisterService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendMailService sendMailService;

    private static final String DEFAULT_AVATAR_URL = "/images/default-avatar.png";

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            VerificationTokenRepository tokenRepository, SendMailService sendMailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.sendMailService = sendMailService;
    }

    /*
      Registra un nuevo usuario y envía email de verificación
      @param user DTO con datos de registro
      @throws AppException si el email ya está registrado (CONFLICT)
     */
    public void createUser(@Valid RegisterRequestDto user) {
        validateEmail(user.getEmail());
        User newUser = createUserFromDto(user);
      //  User savedUser = 
      userRepository.save(newUser);
       // sendVerificationEmail(savedUser); //desactivado por falta de implementación en Frontend
       
    }
    
    /*
      Valida que el email no esté registrado previamente
      @throws AppException si el email ya existe (CONFLICT)
     */
    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AppException("Email ya registrado", ErrorCode.CONFLICT);
        }
    }

    /*
      Crea una entidad User a partir del DTO de registro
      retorna la entidad creada (con enabled=false)de momento se pone en true
     */
    private User createUserFromDto(RegisterRequestDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setImage_url(processImageUrl(userDto.getImage_url()));
        user.setEnabled(true);
        return user;
    }

    /**
      Procesa la URL de imagen de perfil
      retorna la URL de imagen procesada o un valor por defecto
     */
    private String processImageUrl(String imageUrl) {
        return (imageUrl == null || imageUrl.trim().isEmpty())
                ? DEFAULT_AVATAR_URL
                : imageUrl.trim();
    }

    
     // Crea y guarda un token de verificación para el usuario(válido por 24 horas)
     
     private VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                user,
                LocalDateTime.now().plusHours(24));
        return tokenRepository.save(verificationToken);
    }

    /*
     Confirma el token y activa la cuenta de usuario
     si:
     - Token es inválido (BAD_REQUEST)
     - Token expirado (BAD_REQUEST)
     - Usuario ya activado (CONFLICT)
     */
    public void confirmAndActivateUser(String token) {
        VerificationToken validationToken = confirmToken(token);
        activateUser(validationToken.getUser());
        tokenRepository.delete(validationToken);
    }

    // Valida un token de verificación
    public VerificationToken confirmToken(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new AppException("Token inválido", ErrorCode.BAD_REQUEST);
        }
        if (verificationToken.isExpired()) {
            throw new AppException("Token expirado", ErrorCode.BAD_REQUEST);
        }
        if (verificationToken.getUser().getEnabled()) {
            throw new AppException("Usuario ya activado", ErrorCode.CONFLICT);
        }
        return verificationToken;
    }

    // Activa un usuario y lo guarda en la base de datos
    private void activateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }

    // Envía un email de verificación al usuario
    private void sendVerificationEmail(User user) {
        VerificationToken token = createVerificationToken(user);
        sendMailService.sendVerificationEmail(user.getEmail(), token.getToken());
    }

    // Reenvía el token de verificación a un usuario
    public void resendVerificationToken(String email) {
        User user = getUserForResend(email);
        tokenRepository.deleteByUser(user);
        sendVerificationEmail(user);
    }

    // Obtiene usuario válido para reenvío de token
    private User getUserForResend(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new AppException("Usuario no encontrado", ErrorCode.NOT_FOUND);
        }

        if (user.getEnabled()) {
            throw new AppException("Cuenta ya activada", ErrorCode.BAD_REQUEST);
        }
        return user;
    }
}