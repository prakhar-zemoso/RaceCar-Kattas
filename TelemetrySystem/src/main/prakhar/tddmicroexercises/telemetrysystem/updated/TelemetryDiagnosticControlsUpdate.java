package tddmicroexercises.telemetrysystem.updated;

import tddmicroexercises.telemetrysystem.TelemetryClient;

public class TelemetryDiagnosticControlsUpdate implements TelementryDiagnosticContrl {

    private final String DiagnosticChannelConnectionString = "*111#";

       // private final TelementryClientUpdated telemetryClient;
        private String diagnosticInfo = "";
    private TelementryMessageStatus telemetryClient;
    private TelementryClientConnection telemetryChannel;



    public  TelemetryDiagnosticControlsUpdate(TelementryMessageStatus client, TelementryClientConnection channel) {
        telemetryClient = client;
        telemetryChannel =  channel;
    }
    public  TelemetryDiagnosticControlsUpdate() {
        TelementryClientUpdated telemetry = new TelementryClientUpdated();
    }


        public String getDiagnosticInfo(){
            return diagnosticInfo;
        }

        public void setDiagnosticInfo(String diagnosticInfo){
            this.diagnosticInfo = diagnosticInfo;
        }

    public TelementryDiagnosticMethod checkTransmission() throws Exception {
        telemetryChannel.disconnect();

        int retryLeft = 3;
        while (!telemetryChannel.getOnlineStatus() && retryLeft > 0) {
            telemetryChannel.connect(DiagnosticChannelConnectionString);
            retryLeft -= 1;
        }

        if (!telemetryChannel.getOnlineStatus()) {
            throw new Exception("Unable to connect.");
        }

        telemetryClient.send(TelemetryClient.DIAGNOSTIC_MESSAGE);
        return new TelementryDiagnosticMethod(telemetryClient.receive());
    }


}
