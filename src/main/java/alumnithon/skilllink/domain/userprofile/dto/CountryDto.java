package alumnithon.skilllink.domain.userprofile.dto;

public class CountryDto {
    String nombre;
    String name;
    String iso2;
    String iso3;
    String phoneCode;

    // ---Getter---

    public String getNombre() {
        return nombre;
    }

    public String getName() {
        return name;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    //---Setter---

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }
}
