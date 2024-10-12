package culturemedia.exception;

public class DuracionNotValidException extends RuntimeException {
    public DuracionNotValidException(String titulo, Double duracion) {
        super(titulo);
    }
}
