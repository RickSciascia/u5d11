package ricksciascia.u5d11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ricksciascia.u5d11.entities.Viaggio;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio,Long> {
//    TODO: eventuali queries
}
