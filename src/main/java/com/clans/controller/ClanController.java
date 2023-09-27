package com.clans.controller;

import com.clans.dto.ClanDto;
import com.clans.entity.Clan;
import com.clans.service.ClanManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clan")
public class ClanController {

    private final ClanManager clanManager;

    @GetMapping("/increment/{clanId}")
    public ResponseEntity<ClanDto> incGold(@PathVariable long clanId, @RequestParam int gold) {
        ClanDto clanDto = clanManager.incrementGold(clanId, gold);
        return new ResponseEntity<>(clanDto, HttpStatus.OK);
    }

    @GetMapping("/decrement/{clanId}")
    public ResponseEntity<ClanDto> remGold(@PathVariable long clanId, @RequestParam int gold) {
        ClanDto clanDto = clanManager.decrementGold(clanId, gold);
        return new ResponseEntity<>(clanDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ClanDto createClan(@RequestBody Clan clan) {
        ClanDto clanDto = clanManager.createClan(clan);
        return clanDto;
    }
}
