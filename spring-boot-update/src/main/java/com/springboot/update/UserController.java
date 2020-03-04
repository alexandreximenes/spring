package com.springboot.update;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/")
public class UserController {

    private final MessageSource messageSource;
    private final UserService userService;
    private final Response<User> response = new Response<>();

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return response.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user){
        final User userS = userService.save(user);
        return response.ok(userS);
    }

    @GetMapping(PathUtils.ID)
    public ResponseEntity<User> getId(@PathVariable Long id) throws Exception {
        final User userS = userService.findById(id);
        return response.resolve(userS);
    }

    @GetMapping("/i18n")
    public String getI18n(@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        return messageSource.getMessage("good.morning", null, locale);
    }





}
