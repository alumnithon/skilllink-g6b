package alumnithon.skilllink.domain.userprofile.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries")
@Getter
@Setter
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
}
