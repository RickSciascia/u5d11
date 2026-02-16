package ricksciascia.u5d11.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(String notePreferenze,
                              @NotNull
                              @Min(value = 1, message = "idDipendente deve essere superiore o uguale ad 1")
                              long idDipendente,
                              @NotNull
                              @Min(value = 1, message = "idViaggio deve essere superiore o uguale ad 1")
                              long idViaggio) {
}
