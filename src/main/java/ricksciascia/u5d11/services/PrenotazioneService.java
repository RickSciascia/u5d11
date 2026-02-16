package ricksciascia.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.entities.Prenotazione;
import ricksciascia.u5d11.entities.Viaggio;
import ricksciascia.u5d11.exceptions.BadReqException;
import ricksciascia.u5d11.payloads.PrenotazioneDTO;
import ricksciascia.u5d11.repositories.PrenotazioneRepository;

@Service
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final DipendenteService dipendenteService;
    private final ViaggioService viaggioService;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendenteService dipendenteService, ViaggioService viaggioService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendenteService = dipendenteService;
        this.viaggioService = viaggioService;
    }
    public Prenotazione savePrenotazione(PrenotazioneDTO payload) {
//        cerco il dipendente ed il viaggio
        Dipendente dipendenteTrovato = dipendenteService.findById(payload.idDipendente());
        Viaggio viaggioTrovato = viaggioService.findById(payload.idViaggio());
//        uso la derived query per controllare che il dipendente non abbia già un viaggio programmato per quel giorno
        if(prenotazioneRepository.existsByDipendenteAndViaggioData(dipendenteTrovato,viaggioTrovato.getData())) throw new BadReqException("Il dipendente " + dipendenteTrovato.getNome() + " ha già un viaggio prenotato per questa data : " + viaggioTrovato.getData());
//        creo una prenotazione
        Prenotazione prenotazioneDaSalvare = new Prenotazione(payload.notePreferenze(), dipendenteTrovato,viaggioTrovato);
        return this.prenotazioneRepository.save(prenotazioneDaSalvare);
    }
}
