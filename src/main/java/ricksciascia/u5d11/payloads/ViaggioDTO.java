package ricksciascia.u5d11.payloads;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message = "La destionazione è un campo obbligatorio")
        @Size(min = 2, message = "La destinazione deve avere minimo 2 caratteri")
        String destinazione,
        @NotNull
        @Future(message = "La data non può essere nel passato!")
        LocalDate data) {
}
