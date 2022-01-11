package com.raf.hoteluserservice.controller;

import com.raf.hoteluserservice.dto.ClientDto;
import com.raf.hoteluserservice.dto.ClientRankDto;
import com.raf.hoteluserservice.dto.ClientRankUpdateDto;
import com.raf.hoteluserservice.dto.ClientUpdateDto;
import com.raf.hoteluserservice.security.CheckSecurity;
import com.raf.hoteluserservice.service.ClientRankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientrank")
public class ClientRankController {

    private ClientRankService clientRankService;

    public ClientRankController(ClientRankService clientRankService) {
        this.clientRankService = clientRankService;
    }

    @PutMapping("/{name}/update")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<ClientRankDto> updateClientRank(@RequestHeader("Authorization") String authorization,
                                                          @PathVariable("name") String name, @RequestBody @Valid ClientRankUpdateDto clientRankUpdateDto) {
        return ResponseEntity.ok(clientRankService.updateClientRank(name, clientRankUpdateDto));
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<ClientRankDto>> getAllClientRanks(@RequestHeader("Authorization") String authorization,
                                                         Pageable pageable) {

        return new ResponseEntity<>(clientRankService.findAll(pageable), HttpStatus.OK);
    }


}
