package com.springboot.update;

import com.springboot.update.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

public class Response<T>{

    public ResponseEntity<T> ok(User userS) {
        URI location = getLocation(userS, PathUtils.ID);
        return ResponseEntity.created(location).build();
    }


    public ResponseEntity<Object> ok(Object object) {
        return ResponseEntity.ok(object);
    }

    public ResponseEntity<List<T>> ok(List<T> list) {
        return ResponseEntity.ok(list);
    }

    private URI getLocation(User userS, String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(id)
                .buildAndExpand(userS.getId())
                .toUri();
    }

    public ResponseEntity<T> resolve(T userS) throws Exception {
        if(isNull(userS))
            throw new UserNotFoundException("Not found");
        return ResponseEntity.ok(userS);
    }
}
