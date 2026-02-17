package ricksciascia.u5d11.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//per Autorizzazione aggiungo annotazione OBBLIGATORIA per poi poter usare @PreAuthorize nei vari ENDPOINT
@EnableMethodSecurity
public class SecurityConfig {
    @Bean // bean che serve per la configurazione della sicurezza
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
//        disabilitazione form login front end
        httpSecurity.formLogin(formLogin -> formLogin.disable());
//        disabilitazione protezione attacchi csrf (non serve questa protezione con la gestione auth tramite token)
        httpSecurity.csrf(csrf -> csrf.disable());
//        disabilito la gestione tramite sessione poichè noi lavoriamo con i token (approccio diverso)
        httpSecurity.sessionManagement(sessions->sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        disabilito il comportamento di default di spring che blinda tutte le rotte in automatico dando un 401 a qualsiasi richiesta
        httpSecurity.authorizeHttpRequests(req -> req.requestMatchers("/**").permitAll());

        return httpSecurity.build();
    }


//    DISCORSO PASSWORD MANAGEMENT
    @Bean // bean che servirà a RITORNARE L ENCODER che useremo per la password
    public PasswordEncoder getBCrypt() {
//        all interno come parametro la lentezza o la "forza" più è alto pìu sarà il n. di giri che farà l encoder per mettere una stringa random sulla nostra psw rendendola
//        complicata da decrittare
        return new BCryptPasswordEncoder(12);
    }

}
