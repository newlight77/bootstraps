package io.github.newlight77.bootstrap.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/bootstrap")
public class BootstrapController {

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String hello() throws IllegalAccessException {

        return "Hello Kong!";
    }
}
