package br.com.cingo.controller;


import br.com.cingo.domain.model.Log;
import br.com.cingo.domain.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public List<Log> findAll() {
        return logService.findAll();
    }

    @PostMapping
    public Log save(@RequestBody @Valid Log logInput) {
        return logService.save(logInput);
    }
}
