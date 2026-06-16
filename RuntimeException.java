//Marisol Yañez Borquez
import excepciones.SVPException;

public class RuntimeException extends Exception {
    public RuntimeException(String msg){
        super(msg);
    }
    @Override
    public String getMessage (){
        return super.getMessage();
    }
}
