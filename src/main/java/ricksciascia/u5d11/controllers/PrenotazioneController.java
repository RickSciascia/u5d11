package ricksciascia.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.u5d11.entities.Prenotazione;
import ricksciascia.u5d11.exceptions.ValException;
import ricksciascia.u5d11.payloads.PrenotazioneDTO;
import ricksciascia.u5d11.services.PrenotazioneService;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    @Autowired
    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }
//    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione creaPrenotazione(@RequestBody @Validated PrenotazioneDTO payload, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
            List<String> listaErrori = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValException(listaErrori);
        } else {
            return this.prenotazioneService.savePrenotazione(payload);
        }
    }
//    GET
}
