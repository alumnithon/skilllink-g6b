package alumnithon.skilllink.domain.userprofile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre; // Nombre principal en español

    @Column(length = 100)
    private String name; // Nombre en inglés u otro idioma

    @Column(length = 2)
    private String iso2;

    @Column(length = 3)
    private String iso3;

    @Column(name = "phone_code", length = 10)
    private String phoneCode;

    //---Getter---

    public Integer getId() {
        return id;
    }

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


    public void setId(Integer id) {
        this.id = id;
    }

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
