package alumnithon.skilllink.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import alumnithon.skilllink.domain.userprofile.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long>{

    Country getById(Long countryId);

}
