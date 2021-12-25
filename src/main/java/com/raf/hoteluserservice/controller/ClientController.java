package com.raf.hoteluserservice.controller;

import com.raf.hoteluserservice.dto.*;
import com.raf.hoteluserservice.security.CheckSecurity;
import com.raf.hoteluserservice.service.ClientService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Get all clients")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number do you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<ClientDto>> getAllClients(@RequestHeader("Authorization") String authorization,
                                                         Pageable pageable) {

        return new ResponseEntity<>(clientService.findAll(pageable), HttpStatus.OK);
    }

    // localhost:8080/client/1/discount

    // znaci ovo ce zapravo biti ruta client/1/discount
    // za clienta sa id-jem 1 dohvati discount
    // pathvariable uzima id iz {id} i smesta u promenljivu id
    // i onda pozovemo funkciju findDiscount, koja za klijenta racuna popust
    @GetMapping("/{id}/discount")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.findDiscount(id), HttpStatus.OK);
    }

    // TODO: da li put ili get?
    @PutMapping("/{id}/ban")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> banClient(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        clientService.banClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: videti da li moze da bude i samo /id
    @PutMapping("/{id}/update")
    @CheckSecurity(roles = {"ROLE_CLIENT", "ROLE_ADMIN"})
    public ResponseEntity<ClientDto> updateClientProfile(@PathVariable("id") Long id, @RequestBody @Valid ClientUpdateDto clientUpdateDto) {
        clientService.updateClientProfile(id, clientUpdateDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: da li put ili get?
    @PutMapping("/{id}/unban")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> unbanClient(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        clientService.unbanClient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Register client")
    @PostMapping
    public ResponseEntity<ClientDto> saveClient(@RequestBody @Valid ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(clientService.addClient(clientCreateDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginClient(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(clientService.login(tokenRequestDto), HttpStatus.OK);
    }



}