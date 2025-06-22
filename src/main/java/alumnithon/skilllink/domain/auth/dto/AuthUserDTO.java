package alumnithon.skilllink.domain.auth.dto;

public record AuthUserDTO(
        Long id,
        String name,
        String role,
        String avatar
) {
}
