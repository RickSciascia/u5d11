package ricksciascia.u5d11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.UnauthorizedException;
import ricksciascia.u5d11.payloads.LoginDTO;
import ricksciascia.u5d11.security.JWTTools;

@Service
public class AuthService {
    private final DipendenteService dipendenteService;
    private final JWTTools jwtTools;

    @Autowired
    public AuthService(DipendenteService dipendenteService, JWTTools jwtTools) {
        this.dipendenteService = dipendenteService;
        this.jwtTools = jwtTools;
    }


    public String checkCredentialsAndGenerateToken(LoginDTO payload) {
//        controllo le credenziali
        Dipendente trovato = this.dipendenteService.findByEmail(payload.email());

        if(trovato.getPassword().equals(payload.password())) {

            String accessToken = jwtTools.generateToken(trovato);
            return accessToken;

        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
//        genero token
//        ritorno token
    }
}
