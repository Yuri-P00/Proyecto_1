package culturemedia.exception;

public class VideoNotFoundException extends CultureMediaException {
    public VideoNotFoundException(String titulo) {
        super(titulo);
    }
    public VideoNotFoundException(){
        super();
    }
}
