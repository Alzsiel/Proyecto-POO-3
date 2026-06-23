//Marisol Yañez Borquez
import excepciones.SVPException;
import java.io.Serializable;

public class RuntimeException extends Exception implements Serializable {
    public RuntimeException(String msg){
        super(msg);
    }
    @Override
    public String getMessage (){
        return super.getMessage();
    }
}