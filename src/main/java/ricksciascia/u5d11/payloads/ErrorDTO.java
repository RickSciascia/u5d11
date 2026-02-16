package ricksciascia.u5d11.payloads;

import java.time.LocalDateTime;

public record ErrorDTO(String messaggio, LocalDateTime timestampErrore) {
}
