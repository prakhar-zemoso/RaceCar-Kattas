package tddmicroexercises.telemetrysystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import tddmicroexercises.telemetrysystem.updated.TelementryClientConnection;
import tddmicroexercises.telemetrysystem.updated.TelementryDiagnosticMethod;
import tddmicroexercises.telemetrysystem.updated.TelementryMessageStatus;
import tddmicroexercises.telemetrysystem.updated.TelemetryDiagnosticControlsUpdate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.fail;



public class TelemetryDiagnosticControlsTest {



    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response1() throws Exception {
        TelementryMessageStatus telemetryClient = mock(TelementryMessageStatus.class);
        TelementryClientConnection telemetryChannel = mock(TelementryClientConnection.class);
        TelemetryDiagnosticControlsUpdate telemetryDiagnosticControls = new TelemetryDiagnosticControlsUpdate(telemetryClient, telemetryChannel);

        when(telemetryChannel.getOnlineStatus()).thenReturn(true);
        when(telemetryClient.receive()).thenReturn("it works");

        // WHEN
        TelementryDiagnosticMethod result = telemetryDiagnosticControls.checkTransmission();


        // THEN
        assertEquals("it works", result.getDiagnosticInfo());
        System.out.println("Its Working");
        verify(telemetryClient, times(1)).send(telemetryClient.DIAGNOSTIC_MESSAGE);
    }




    @Test
    public void CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response() throws Exception {
//
        TelemetryDiagnosticControls telemetryDiagnosticControls = new TelemetryDiagnosticControls();

        // WHEN
        try{
            telemetryDiagnosticControls.checkTransmission();
            System.out.println("------------CheckTransmission_should_send_a_diagnostic_message_and_receive_a_status_message_response");
            System.out.println(telemetryDiagnosticControls.getDiagnosticInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void checkTransmission_should_disconnect_and_if_online_status_is_false_reconnect() throws Exception {
        // GIVEN
        TelementryMessageStatus telemetryClient = mock(TelementryMessageStatus.class);
        TelementryClientConnection telemetryChannel = mock(TelementryClientConnection.class);
        TelemetryDiagnosticControlsUpdate telemetryDiagnosticControls = new TelemetryDiagnosticControlsUpdate(telemetryClient, telemetryChannel);

        doReturn(false).doReturn(true).when(telemetryChannel).getOnlineStatus();
        when(telemetryClient.receive()).thenReturn("it works");

        // WHEN
        TelementryDiagnosticMethod result = telemetryDiagnosticControls.checkTransmission();

        // THEN
        assertEquals("it works", result.getDiagnosticInfo());
        verify(telemetryChannel, times(1)).disconnect();

        verify(telemetryChannel, times(1)).connect("*111#");
    }
    @Test
    public void checkTransmission_should_throw_an_exception_if_cannot_reconnect_but_try_3_times() throws Exception {
        // GIVEN
        TelementryMessageStatus telemetryClient = mock(TelementryMessageStatus.class);
        TelementryClientConnection telemetryChannel = mock(TelementryClientConnection.class);
        TelemetryDiagnosticControlsUpdate telemetryDiagnosticControls = new TelemetryDiagnosticControlsUpdate(telemetryClient, telemetryChannel);


        when(telemetryChannel.getOnlineStatus()).thenReturn(false);

        // WHEN
        var thrown = Assertions.assertThrows(Exception.class, () -> telemetryDiagnosticControls.checkTransmission());
        System.out.println("Test Case 4");

        System.out.println(thrown.getMessage());

        // THEN
        Assertions.assertEquals("Unable to connect.", thrown.getMessage());
        verify(telemetryChannel, times(3)).connect("*111#");
    }
}

