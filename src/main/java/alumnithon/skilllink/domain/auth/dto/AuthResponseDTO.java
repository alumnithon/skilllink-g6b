package alumnithon.skilllink.domain.auth.dto;

public record AuthResponseDTO(
        String token, String role, String name
) {
}
