import java.io.IOException;

public class StateFileNotFoundException extends IOException {

    public StateFileNotFoundException() {
        super("File cannot be found!");
    }
}
