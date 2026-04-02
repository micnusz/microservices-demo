package com.micnusz.orderService.Error;


import com.micnusz.orderService.Exception.UserNotFoundException;
import com.micnusz.orderService.Exception.UserServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

@Component
public class UserErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            return new UserNotFoundException();
        }

        if (response.status() >= 500) {
            return new UserServiceUnavailableException();
        }

        return new RuntimeException("Unexpected error");
    }
}
