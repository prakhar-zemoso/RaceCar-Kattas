package tddmicroexercises.telemetrysystem.updated;

public interface TelementryMessageStatus {
    String DIAGNOSTIC_MESSAGE = "AT#UD";
    String receive();
    void send(String message);
}
