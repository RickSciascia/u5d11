package ricksciascia.u5d11.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ricksciascia.u5d11.entities.Dipendente;
import ricksciascia.u5d11.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Dipendente dipendente) {
//        ritorno usando il metodo .builder di Jwts(libreria importata) per generare il token
        return Jwts.builder()
//                momento emissione token da usare con libreria Date
                .issuedAt(new Date(System.currentTimeMillis()))
//                momento scadenza token aggiungo semplicemente al momento di emissione 1000ms(1s)*60(per ottenere 1min)*60(per ottenere 1ora)*24(giorno)*7 (n.giorni validità)
//                ovviamente un token può scadere anche dopo qualche ora o qualche giorno è completamente personalizzabile basta seguire ragionamento logico fatto sopra!
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24*7))
//                a chi appartiene (soggetto) no info personali
                .subject(String.valueOf(dipendente.getId()))
//                firma del token, uso algoritmo di firma digitale "hmacSha"
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
//                conclusione
                .compact();
    }
    public void verifyToken(String token) {
//        metodo parser per leggere token
        try{
            Jwts.parser()
//                devo verificare con stesso algoritmo che ho usato come firma digitale prima "hmacSha"
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
//        questo ci lancia delle eccezzioni diverse che dobbiamo raccogliere per lanciare in caso un unica eccezione
//        quindi avvolgere il tutto in un try catch che poi in caso lancerà un unica eccezione gestita la UnauthorizedException code: 401
        } catch(Exception ex) {
            throw new UnauthorizedException("Problemi con il token, effettuare il login nuovamente!");
        }
    }
}
