package ricksciascia.u5d11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ricksciascia.u5d11.entities.Dipendente;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente,Long> {
//    TODO: eventuali queries
    Optional<Dipendente> findByEmail(String email);

}
