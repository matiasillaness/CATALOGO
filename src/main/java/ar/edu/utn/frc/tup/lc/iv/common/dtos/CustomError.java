package ar.edu.utn.frc.tup.lc.iv.common.dtos;

public class CustomError {
    private int status;
    private String message;

    public CustomError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}