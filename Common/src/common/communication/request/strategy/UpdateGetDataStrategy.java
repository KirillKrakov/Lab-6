package common.communication.request.strategy;

import common.communication.LabWorkForRequest;
import common.communication.request.Request;

public class UpdateGetDataStrategy implements GetDataStrategy{
    @Override
    public Request getData(String[] request) {
        return new Request(request[0], request[1], LabWorkForRequest.outOfString(request[2]));
    }
}
