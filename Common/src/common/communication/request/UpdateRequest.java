package common.communication.request;

import common.communication.request.Request;
import common.communication.request.strategy.UpdateGetDataStrategy;

public class UpdateRequest extends Request {

    public UpdateRequest() {
        this.getDataStrategy = new UpdateGetDataStrategy();
    }
}
