package ricksciascia.u5d11.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "L'username è un campo obbligatorio")
        @Size(min = 3, max = 20,message = "L'username deve essere compreso tra i 3 e i 20 caratteri")
        String username,
        @NotBlank(message = "il nome è un campo obbligatorio")
        @Size(min = 2, max = 25, message = "Il nome deve essere compreso tra 2 e 25 caratteri")
        String nome,
        @NotBlank(message = "il cognome è un campo obbligatorio")
        @Size(min = 2, max = 25, message = "Il cognome deve essere compreso tra 2 e 25 caratteri")
        String cognome,
        @NotBlank(message = "Il campo email è obbligatorio")
        @Email(message = "L'indirizzo email non è nel formato corretto, controlla la presenza di @")
        String email) {
}
