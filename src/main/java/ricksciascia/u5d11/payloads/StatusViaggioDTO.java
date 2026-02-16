package ricksciascia.u5d11.payloads;

import jakarta.validation.constraints.NotNull;

public record StatusViaggioDTO(
        @NotNull
        boolean completato) {
}
