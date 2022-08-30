package com.example.jackpot.web;

import com.example.jackpot.handler.BetHandler;
import com.example.jackpot.handler.ResponseHandler;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.model.dto.ResponseDto;
import com.example.jackpot.model.dto.ResponseDtoLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class BetController {
    private final ResponseHandler responseHandler;
    private final BetHandler betHandler;
    private final ResponseDtoLoader responseDtoLoader;

    public BetController(final ResponseHandler responseHandler,
                         final BetHandler betHandler, final ResponseDtoLoader responseDtoLoader) {
        this.responseHandler = responseHandler;
        this.betHandler = betHandler;
        this.responseDtoLoader = responseDtoLoader;
    }

    @PostMapping("/bet/{id}")
    public ResponseEntity<ResponseDto> makeBet(@PathVariable final String id, @RequestBody final BetDto bet) {
        betHandler.handleBetRequest(bet);
        ResponseDto dto = responseDtoLoader.loadResponseDto(bet);
        responseHandler.notifyUser(id, bet);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(dto).toUri()
        ).body(dto);
    }
}
