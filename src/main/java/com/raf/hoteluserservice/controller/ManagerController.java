package com.raf.hoteluserservice.controller;


import com.raf.hoteluserservice.dto.*;
import com.raf.hoteluserservice.security.CheckSecurity;
import com.raf.hoteluserservice.service.ManagerService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/manager")

public class ManagerController {


    private ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @ApiOperation(value = "Get all managers")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "What page number you want", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Number of items to return", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN", "ROLE_CLIENT", "ROLE_MANAGER"})
    public ResponseEntity<Page<ManagerDto>> getAllManagers(@RequestHeader("Authorization") String authorization,
                                                           Pageable pageable) {

        return new ResponseEntity<>(managerService.findAll(pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}/ban")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> banManager(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        managerService.banManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/unban")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Void> unbanManager(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long id){
        managerService.unbanManager(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update-password")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<ClientDto> updateManagerPassword(@RequestHeader("Authorization") String authorization,
                                                          @PathVariable("id") Long id, @RequestBody @Valid ManagerPasswordDto managerPasswordDto) {
        System.out.println("\nU metodi updateManagerPassword u Manager kontroleru");
        managerService.changePassword(id, managerPasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> updateManagerProfile(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable("id") Long id, @RequestBody @Valid ManagerUpdateDto managerUpdateDto) {
        return ResponseEntity.ok(managerService.updateManagerProfile(id, managerUpdateDto));
    }

    @ApiOperation(value = "Register manager")
    @PostMapping
    public ResponseEntity<ManagerDto> saveManager(@RequestBody @Valid ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.addManager(managerCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/verifyMail/{email}")
    public ResponseEntity<Void> verifyMail(@PathVariable("email") String email, HttpServletResponse httpServletResponse) throws IOException {
        managerService.verifyMail(email);
        httpServletResponse.getWriter().println("Email successfully verified.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginManager(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(managerService.login(tokenRequestDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerDto> findManagerById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(managerService.findById(id), HttpStatus.OK);
    }

    @GetMapping("changePassword/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable("id") Long id, HttpServletResponse httpServletResponse) throws IOException {
        managerService.saveNewPassword(id);
        httpServletResponse.getWriter().println("Password successfully changed.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
