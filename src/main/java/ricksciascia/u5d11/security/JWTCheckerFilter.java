package ricksciascia.u5d11.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.UnauthorizedException;
import ricksciascia.u5d11.services.DipendenteService;

import java.io.IOException;

// va messa notazione @Component per inserirlo all interno dei filtri e deve estendere OncePerRequestFilter
@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    private final JWTTools jwtTools;
    private final DipendenteService dipendenteService;

    @Autowired
    public JWTCheckerFilter(JWTTools jwtTools, DipendenteService dipendenteService) {
        this.jwtTools = jwtTools;
        this.dipendenteService = dipendenteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        doFilterInternal è il metodo che viene eseguito ad ogni request e controllerà il token
//        System.out.println("FILTER");


//        ************************************************* SEZIONE AUTENTICAZIONE ****************************************************************
//        leggo il token
        String authHeader = request.getHeader("Authorization");
//        controllo se è null e se non inizia con "Bearer "
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserire token nell'Auth header nel formato corretto!");
//        estraggo il token dall header pulendolo dal Bearer iniziale usando .replace()
        String accessToken = authHeader.replace("Bearer ","");
//        verifico il token con firma e data di scadenza mi serve classe JWTTools che prendo dai bean con @Autowired
        jwtTools.verifyToken(accessToken);
//        ************************************************* SEZIONE AUTORIZZAZIONE ****************************************************************
//        LEGGO IL TOKEN nel cui interno troverò info sull utente perciò dovrò fare un metodo apposito nei JWTTools lo chiamerò extractIdFromToken a cui passerò l accessToken
        long userId = jwtTools.extractIdFromToken(accessToken);
//        CON ID faccio Find nel DB quindi mi servirà il service del Dipendente
        Dipendente utenteAutenticato = this.dipendenteService.findById(userId);
//        Associo utente al security context
//        devo usare un oggetto Authentication che si crea con new UsernamePasswordAuthenticationToken() per poi associarlo dopo al Security Context
//        come parametri di questo va passato l utente che ho trovato attraverso claim del token, null e infine il ruolo dell utente che trovo direttamente dall utente
        Authentication authentication = new UsernamePasswordAuthenticationToken(utenteAutenticato,null,utenteAutenticato.getAuthorities());
//        Ora SecurityContext a cui setto l autenticazione con questo oggetto che mi sono appena creato.
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        fatto ciò devo andare a impostare nei controller dei vari endpoint il @PreAuthorize
//        per andare al prossimo step (e quindi passare allo step successivo in questo caso il controller, ma potrebbe essere un altro filtro)
        filterChain.doFilter(request,response);
//        se c'è problema con il token devo lanciare un eccezione

    }


//    override di shouldNotFilter mi permette di escludere delle rotte es. quella "/auth" o quella di creazione nuovi dipendenti ed anche altre dal filtro
//    cosa che non avrebbe senso siano filtrate
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
//        return request.getServletPath().equals("/auth/login")
    }
}
