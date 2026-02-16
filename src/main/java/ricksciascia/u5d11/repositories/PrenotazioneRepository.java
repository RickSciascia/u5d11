package ricksciascia.u5d11.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.entities.Prenotazione;

import java.time.LocalDate;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {
//    TODO: eventuali queries
    boolean existsByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);
}
