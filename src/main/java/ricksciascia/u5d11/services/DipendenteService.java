package ricksciascia.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.BadReqException;
import ricksciascia.u5d11.exceptions.NotFoundException;
import ricksciascia.u5d11.payloads.DipendenteDTO;
import ricksciascia.u5d11.repositories.DipendenteRepository;

@Service
public class DipendenteService {
    private final DipendenteRepository dipendenteRepository;
    private final PasswordEncoder bcryptEncoder;

    @Autowired
    public DipendenteService(DipendenteRepository dipendenteRepository, PasswordEncoder bcryptEncoder) {
        this.dipendenteRepository = dipendenteRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public Dipendente saveDipendente(DipendenteDTO payload) {
//        validazione se email è già presente in DB tramite query
        this.dipendenteRepository.findByEmail(payload.email()).ifPresent(dipendente -> {
            throw new BadReqException("L'email " + dipendente.getEmail() + " è già registrata");
        });
//        creo dipendente
        Dipendente dipendenteDaSalvare = new Dipendente(payload.username(), payload.nome(), payload.cognome(), payload.email(), bcryptEncoder.encode(payload.password()));
//        salvo e ritorno il dipendente
        Dipendente salvato = this.dipendenteRepository.save(dipendenteDaSalvare);
        return salvato;
    }

    public Page<Dipendente> findAll(int page , int size,String orderBy) {
        if(size > 20) size =20;
        if(size < 0) size = 10;
        if(page<0) page = 0;
        if(!(orderBy.equals("nome")|| orderBy.equals("cognome"))) orderBy = "id";
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy).ascending());
        return this.dipendenteRepository.findAll(pageable);

    }

    public Dipendente findById(long idDipendente) {
        return this.dipendenteRepository.findById(idDipendente).orElseThrow(()-> new NotFoundException(idDipendente));
    }

    public Dipendente updateDipendenteById(long idDipendente,DipendenteDTO payload){
//        trovo dipendente con findById
        Dipendente trovato = this.findById(idDipendente);
//        validazione email
        if(!trovato.getEmail().equals(payload.email())) {
            this.dipendenteRepository.findByEmail(payload.email())
                    .ifPresent(dipendente -> {throw new BadReqException("L'email è già in uso");});
        }
//        passata validazione sovrascrivo
        trovato.setNome(payload.nome());
        trovato.setCognome(payload.cognome());
        trovato.setUsername(payload.username());
        trovato.setEmail(payload.email());
//        salvo + log + ritorno
        Dipendente dipendenteModificato = this.dipendenteRepository.save(trovato);
        System.out.println("Dipendente con id " + dipendenteModificato.getId() + " aggiornato!");
        return dipendenteModificato;
    }

    public void deleteById(long idDipendente) {
        Dipendente trovato = this.findById(idDipendente);
        this.dipendenteRepository.delete(trovato);
    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("L'utente con email: "+ email + " non è registrato!"));
    }
}
