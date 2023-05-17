package common.communication.request.strategy;

import common.communication.request.Request;

public interface GetDataStrategy {
    public Request getData(String[] request);
}
