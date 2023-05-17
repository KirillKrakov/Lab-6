package common.communication.request;

import common.communication.request.Request;
import common.communication.request.strategy.EmptyGetDataStrategy;

public class OnlyCommandRequest extends Request {
    public OnlyCommandRequest() {
        this.getDataStrategy = new EmptyGetDataStrategy();
    }
}
