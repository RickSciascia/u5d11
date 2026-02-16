package ricksciascia.u5d11.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@Getter
@Setter
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(name = "data_richiesta", nullable = false)
    private LocalDate dataRichiesta;
    @Column(name = "note_preferenze")
    private String notePreferenze;
    @ManyToOne
    @JoinColumn(name = "id_dipendente",nullable = false)
    private Dipendente dipendente;
    @OneToOne
    @JoinColumn(name = "id_viaggio",nullable = false )
    private Viaggio viaggio;

    public Prenotazione (String notePreferenze, Dipendente dipendente, Viaggio viaggio){
        this.dataRichiesta = LocalDate.now();
        this.notePreferenze = notePreferenze;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", dataRichiesta=" + dataRichiesta +
                ", notePreferenze='" + notePreferenze + '\'' +
                ", dipendente=" + dipendente +
                ", viaggio=" + viaggio +
                '}';
    }
}
