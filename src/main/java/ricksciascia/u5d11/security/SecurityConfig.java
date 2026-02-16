package ricksciascia.u5d11.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
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

}
