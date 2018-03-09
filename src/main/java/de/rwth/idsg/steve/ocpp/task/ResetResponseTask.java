package de.rwth.idsg.steve.ocpp.task;

import de.rwth.idsg.steve.handler.OcppCallback;
import de.rwth.idsg.steve.ocpp.CommunicationTask;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.web.dto.ocpp.ResetParams;

import javax.xml.ws.AsyncHandler;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 09.03.2018
 */
public class ResetResponseTask extends CommunicationTask<ResetParams, String> {

    public ResetResponseTask(OcppVersion ocppVersion, ResetParams params) {
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
    public ocpp.cp._2010._08.ResetRequest getOcpp12Request() {
        return new ocpp.cp._2010._08.ResetRequest()
                .withType(ocpp.cp._2010._08.ResetType.fromValue(params.getResetType().value()));
    }

    @Override
    public ocpp.cp._2012._06.ResetRequest getOcpp15Request() {
        return new ocpp.cp._2012._06.ResetRequest()
                .withType(ocpp.cp._2012._06.ResetType.fromValue(params.getResetType().value()));
    }

    @Override
    public AsyncHandler<ocpp.cp._2010._08.ResetResponse> getOcpp12Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getStatus().value());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }

    @Override
    public AsyncHandler<ocpp.cp._2012._06.ResetResponse> getOcpp15Handler(String chargeBoxId) {
        return res -> {
            try {
                success(chargeBoxId, res.get().getStatus().value());
            } catch (Exception e) {
                failed(chargeBoxId, e);
            }
        };
    }
}
