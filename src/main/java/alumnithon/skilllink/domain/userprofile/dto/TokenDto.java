package alumnithon.skilllink.domain.userprofile.dto;

public class TokenDto {
String token;
UserDto user;

///---Getter---
    public String getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
///---Setter---
    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
