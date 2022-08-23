package tddmicroexercises.telemetrysystem.updated;

public interface TelementryClientConnection {
    boolean getOnlineStatus();
    void connect(String telemetryServerConnectionString);
    void disconnect();
}
