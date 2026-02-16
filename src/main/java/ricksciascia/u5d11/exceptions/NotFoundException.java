package ricksciascia.u5d11.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Il record con id " + id + " non è stato trovato, prova con un altro.");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
