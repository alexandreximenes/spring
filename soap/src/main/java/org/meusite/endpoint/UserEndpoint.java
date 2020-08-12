package org.meusite.endpoint;

import org.meusite.service.UserService;
import org.meusite.user.GetUserRequest;
import org.meusite.user.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {

    @Autowired
    private UserService userService;

    @PayloadRoot(namespace="http://www.meusite.org/user", localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserResponse(@RequestPayload GetUserRequest request){

        GetUserResponse response = new GetUserResponse();
        response.setName(userService.getName(request.getName()));
        return response;
    }

}
