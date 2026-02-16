package ricksciascia.u5d11.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.ValException;
import ricksciascia.u5d11.payloads.DipendenteDTO;
import ricksciascia.u5d11.payloads.LoginDTO;
import ricksciascia.u5d11.payloads.LoginResponseDTO;
import ricksciascia.u5d11.services.AuthService;
import ricksciascia.u5d11.services.DipendenteService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final DipendenteService dipendenteService;

    @Autowired
    public AuthController(AuthService authService, DipendenteService dipendenteService) {
        this.authService = authService;
        this.dipendenteService = dipendenteService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO payload) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(payload));
    }

    //    POST NEW DIPENDETE
    @PostMapping("/register")
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
}
