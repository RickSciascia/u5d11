package ricksciascia.u5d11.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "dipendenti")
@NoArgsConstructor
@Setter
@Getter
public class Dipendente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String avatar;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;


    public Dipendente(String username, String nome,String cognome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.avatar = "https://ui-avatars.com/api/?name=" + nome + "+" + cognome;
//        imposto di Default che un dipendente appena registrato sarà un utente semplice
        this.ruolo = Ruolo.UTENTE;
    }

    @Override
    public String toString() {
        return "Dipendente{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        METODO che restituisce una collezione di Autorities ovvero i ruoli, nel nostro caso solo 1 (ma ne potrebbe avere anche più di uno)
//        SimpleGrantedAuthority è una classe del pacchetto Security che crea oggetti ruolo che sono compatibili con questo metodo che deve ritornare una collection
//        Al suo interno passo il ruolo attraverso il metodo .name() del Enum
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }


}
