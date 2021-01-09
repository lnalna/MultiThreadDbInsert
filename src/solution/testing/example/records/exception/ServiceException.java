package solution.testing.example.records.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(Throwable t) {
        super(t);
    }
}
