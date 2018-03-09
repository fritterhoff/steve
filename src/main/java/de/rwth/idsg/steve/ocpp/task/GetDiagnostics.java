package de.rwth.idsg.steve.ocpp.task;

import de.rwth.idsg.steve.handler.OcppCallback;
import de.rwth.idsg.steve.ocpp.CommunicationTask;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.web.dto.ocpp.GetDiagnosticsParams;

import javax.xml.ws.AsyncHandler;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 09.03.2018
 */
public class GetDiagnostics extends CommunicationTask<GetDiagnosticsParams, String> {

    public GetDiagnostics(OcppVersion ocppVersion, GetDiagnosticsParams params) {
        super(ocppVersion, params);
    }

    @Override
    public OcppCallback<String> defaultCallback() {
        return new OcppCallback<String>() {
            @Override
            public void success(String chargeBoxId, String response) {
                addNewResponse(chargeBoxId, response);
            }

            @Override
            public void failed(String chargeBoxId, String errorMessage) {
                addNewError(chargeBoxId, errorMessage);
            }
        };
    }

    @Override
    public ocpp.cp._2010._08.GetDiagnosticsRequest getOcpp12Request() {
        return new ocpp.cp._2010._08.GetDiagnosticsRequest()
                .withLocation(params.getLocation())
                .withRetries(params.getRetries())
                .withRetryInterval(params.getRetryInterval())
                .withStartTime(params.getStart().toDateTime())
                .withStopTime(params.getStop().toDateTime());
    }

    @Override
    public ocpp.cp._2012._06.GetDiagnosticsRequest getOcpp15Request() {
        return new ocpp.cp._2012._06.GetDiagnosticsRequest()
                .withLocation(params.getLocation())
                .withRetries(params.getRetries())
                .withRetryInterval(params.getRetryInterval())
                .withStartTime(params.getStart().toDateTime())
                .withStopTime(params.getStop().toDateTime());
    }

    @Override
    public AsyncHandler<ocpp.cp._2010._08.GetDiagnosticsResponse> getOcpp12Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getFileName());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }

    @Override
    public AsyncHandler<ocpp.cp._2012._06.GetDiagnosticsResponse> getOcpp15Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getFileName());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }
 }
