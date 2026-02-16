package ricksciascia.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.ValException;
import ricksciascia.u5d11.payloads.DipendenteDTO;
import ricksciascia.u5d11.services.DipendenteService;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    private final DipendenteService dipendenteService;

    @Autowired
    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

//    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente creaDipendente(@RequestBody @Validated DipendenteDTO payload, BindingResult validationResult) {
//        validazione payload
        if(validationResult.hasErrors()) {
//        trasformo la Lista di FieldError che mi fornisce il validationResult in una Lista di Stringhe tramite i Java Stream con Map e toList
            List<String> listaErrori = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValException(listaErrori);
        } else {
            return this.dipendenteService.saveDipendente(payload);
        }
    }
//    GET
    @GetMapping
    public Page<Dipendente> getAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam(defaultValue = "username") String orderBy) {
        return this.dipendenteService.findAll(page, size, orderBy);
    }

//    GET specifica
    @GetMapping("/{idDipendente}")
    public Dipendente getDipendente(@PathVariable long idDipendente) {
        return this.dipendenteService.findById(idDipendente);
    }

//    PUT
    @PutMapping("/{idDipendente}")
    public Dipendente editDipendente(@RequestBody @Validated DipendenteDTO payload, BindingResult validationResult, @PathVariable long idDipendente) {

        if(validationResult.hasErrors()) {
//        trasformo la Lista di FieldError che mi fornisce il validationResult in una Lista di Stringhe tramite i Java Stream con Map e toList
            List<String> listaErrori = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValException(listaErrori);
        } else {
            return this.dipendenteService.updateDipendenteById(idDipendente,payload);
        }

    }
//    DELETE
    @DeleteMapping("/{idDipendente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable long idDipendente) {
        this.dipendenteService.deleteById(idDipendente);
    }
}
