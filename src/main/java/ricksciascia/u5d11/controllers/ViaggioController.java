package ricksciascia.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.u5d11.entities.Viaggio;
import ricksciascia.u5d11.exceptions.ValException;
import ricksciascia.u5d11.payloads.ViaggioDTO;
import ricksciascia.u5d11.services.ViaggioService;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    private final ViaggioService viaggioService;
    @Autowired
    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }
//    POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio creaViaggio(@RequestBody @Validated ViaggioDTO payload, BindingResult validationResult) {
        if(validationResult.hasErrors()) {
//        trasformo la Lista di FieldError che mi fornisce il validationResult in una Lista di Stringhe tramite i Java Stream con Map e toList
            List<String> listaErrori = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValException(listaErrori);
        } else {
            return this.viaggioService.saveViaggio(payload);
        }
    }
//    GET
    @GetMapping
    public Page<Viaggio> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                @RequestParam(defaultValue = "data") String orderBy) {
        return this.viaggioService.findAll(page,size,orderBy);
    }
//    GET specifica
    @GetMapping("/{idViaggio}")
    public Viaggio getViaggio(@PathVariable long idViaggio) {
        return this.viaggioService.findById(idViaggio);
    }
//    PUT

    @PutMapping("/{idViaggio}")
    public Viaggio editViaggio(@RequestBody @Validated ViaggioDTO payload, BindingResult validationResult, @PathVariable long idViaggio) {
        if(validationResult.hasErrors()) {
            List<String> listaErrori = validationResult.getFieldErrors()
                    .stream().map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValException(listaErrori);
        } else {
            return this.viaggioService.updateViaggioById(idViaggio,payload);
        }
    }
//    DELETE
    @DeleteMapping("/{idViaggio}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViaggio(@PathVariable long idViaggio) {
        this.viaggioService.deleteById(idViaggio);
    }
//
////    PATCH
//    @PatchMapping("/{idViaggio}")
//    public Viaggio updateStatusViaggio(@RequestBody @Validated StatusViaggioDTO payload, BindingResult validationResult,@PathVariable long idViaggio) {
//        if(validationResult.hasErrors()) {
//            List<String> listaErrori = validationResult.getFieldErrors()
//                    .stream().map(fieldError -> fieldError.getDefaultMessage())
//                    .toList();
//
//            throw new ValException(listaErrori);
//        } else {
//            return this.viaggioService.updateStatusViaggio(idViaggio,payload);
//        }
//    }

}
