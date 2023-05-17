package common.communication.request;

import common.communication.request.Request;
import common.communication.request.strategy.LabWorkGetDataStrategy;

public class WithLabWorkRequest extends Request {

    public WithLabWorkRequest() {
        this.getDataStrategy = new LabWorkGetDataStrategy();
    }
}
