package ricksciascia.u5d11.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsListDTO(String messaggio, LocalDateTime timestampErrore, List<String> listaErrori) {
}
