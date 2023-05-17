package common.communication.request;

import common.communication.request.Request;
import common.communication.request.strategy.AuthorGetDataStrategy;

public class WithAuthorRequest extends Request {

    public WithAuthorRequest() {
        this.getDataStrategy = new AuthorGetDataStrategy();
    }
}
