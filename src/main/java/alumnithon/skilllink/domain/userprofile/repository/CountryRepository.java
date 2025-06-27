package alumnithon.skilllink.domain.userprofile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import alumnithon.skilllink.domain.userprofile.model.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

    Country getById(Integer countryId);

}
