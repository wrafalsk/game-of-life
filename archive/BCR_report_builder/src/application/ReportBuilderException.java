package application;

public class ReportBuilderException extends Exception {
    private static final long serialVersionUID = 1L;

    public ReportBuilderException() {
        super();
    }

    public ReportBuilderException(String message) {
        super(message);
    }

    public ReportBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportBuilderException(Throwable cause) {
        super(cause);
    }
}
