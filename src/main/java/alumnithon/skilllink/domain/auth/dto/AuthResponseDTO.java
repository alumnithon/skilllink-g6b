package alumnithon.skilllink.domain.auth.dto;

public record AuthResponseDTO(
        AuthUserDTO user,
        String token
) {
}
