package com.example.jackpot.web;

import com.example.jackpot.manager.CasinoManager;
import com.example.jackpot.model.Casino;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casino")
public class CasinoController {

    private final CasinoManager casinoManager;

    public CasinoController(final CasinoManager casinoManager) {
        this.casinoManager = casinoManager;
    }

    @PostMapping
    public Casino createCasino(@RequestBody final Casino casino) {
        return casinoManager.createCasino(casino);
    }

    @PutMapping("/update/")
    public Casino updateCasino(@RequestBody final Casino casino) {
        return casinoManager.updateCasino(casino);
    }
}
