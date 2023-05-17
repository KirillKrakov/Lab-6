package common.communication.request.strategy;

import common.communication.request.Request;

public class StrArgGetDataStrategy implements GetDataStrategy{
    @Override
    public Request getData(String[] request) {
        return new Request(request[0], request[1], null);
    }
}
