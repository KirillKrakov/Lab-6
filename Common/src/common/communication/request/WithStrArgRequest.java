package common.communication.request;

import common.communication.request.Request;
import common.communication.request.strategy.StrArgGetDataStrategy;

public class WithStrArgRequest extends Request {
    public WithStrArgRequest() {
        this.getDataStrategy = new StrArgGetDataStrategy();
    }
}
