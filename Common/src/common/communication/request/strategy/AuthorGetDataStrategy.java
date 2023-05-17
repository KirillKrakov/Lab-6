package common.communication.request.strategy;

import common.communication.AuthorForRequest;
import common.communication.request.Request;

public class AuthorGetDataStrategy implements GetDataStrategy{
    @Override
    public Request getData(String[] request) {
        return new Request(request[0], "", AuthorForRequest.outOfString(request[2]));
    }
}
