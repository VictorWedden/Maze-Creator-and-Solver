public class InvalidArgumentException extends Exception{

    String errorMessage = "Invalid Argument Exception: ";

    public InvalidArgumentException(String s){
        errorMessage += s;
    }
}
