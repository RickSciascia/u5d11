package ricksciascia.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ricksciascia.u5d11.entities.Viaggio;
import ricksciascia.u5d11.exceptions.NotFoundException;
import ricksciascia.u5d11.payloads.ViaggioDTO;
import ricksciascia.u5d11.repositories.ViaggioRepository;

@Service
public class ViaggioService {
    private final ViaggioRepository viaggioRepository;

    @Autowired
    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    public Viaggio saveViaggio(ViaggioDTO payload) {
        Viaggio viaggioDaSalvare = new Viaggio(payload.destinazione(), payload.data());
        return this.viaggioRepository.save(viaggioDaSalvare);
    }

    public Page<Viaggio> findAll(int page , int size, String orderBy) {
        if (size > 20) size = 20;
        if (size < 0) size = 10;
        if (page < 0) page = 0;
        if (!(orderBy.equals("destinazione") || orderBy.equals("data"))) orderBy = "data";
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findById(long idViaggio) {
        return this.viaggioRepository.findById(idViaggio).orElseThrow(()->new NotFoundException(idViaggio));
    }

    public Viaggio updateViaggioById(long idViaggio, ViaggioDTO payload) {
        Viaggio trovato = this.findById(idViaggio);
        trovato.setData(payload.data());
        trovato.setDestinazione(payload.destinazione());
        Viaggio viaggioModificato = this.viaggioRepository.save(trovato);
        System.out.println("Viaggio con id "+ viaggioModificato.getId() + " aggiornato correttamente!");
        return viaggioModificato;
    }

    public void deleteById(long idViaggio) {
        Viaggio trovato = this.findById(idViaggio);
        this.viaggioRepository.delete(trovato);
    }

//    public Viaggio updateStatusViaggio(long idViaggio, StatusViaggioDTO payload) {
//        Viaggio trovato = this.findById(idViaggio);
//        trovato.setCompletato(payload.completato());
//        String result = "";
//        if(payload.completato()) {
//            result = "Completato";
//        }else {
//            result = "Non completato";
//        }
//        System.out.println("Status viaggio cambiato in " + result);
//        return this.viaggioRepository.save(trovato);
//    }
}
