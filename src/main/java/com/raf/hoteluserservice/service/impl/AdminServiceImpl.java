package com.raf.hoteluserservice.service.impl;

import com.raf.hoteluserservice.domain.Admin;
import com.raf.hoteluserservice.domain.Client;
import com.raf.hoteluserservice.dto.TokenRequestDto;
import com.raf.hoteluserservice.dto.TokenResponseDto;
import com.raf.hoteluserservice.exception.NotFoundException;
import com.raf.hoteluserservice.mapper.ClientMapper;
import com.raf.hoteluserservice.mapper.ManagerMapper;
import com.raf.hoteluserservice.repository.AdminRepository;
import com.raf.hoteluserservice.repository.ClientRankRepository;
import com.raf.hoteluserservice.repository.ClientRepository;
import com.raf.hoteluserservice.repository.ManagerRepository;
import com.raf.hoteluserservice.security.service.TokenService;
import com.raf.hoteluserservice.service.AdminService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientRankRepository clientRankRepository;
    private ClientMapper clientMapper;
    private AdminRepository adminRepository;

    public AdminServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientRankRepository clientRankRepository, ClientMapper clientMapper,
                             ManagerMapper managerMapper, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientRankRepository = clientRankRepository;
        this.clientMapper = clientMapper;
        this.adminRepository = adminRepository;
    }


    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        Admin admin = adminRepository
                .findAdminByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));

        // TODO: treba implementirati ovu proveru da li je client verifikovan
//        if (!client.getVerified())
//            throw new CustomException("Please verify your e-mail address before logging in", ErrorCode.EMAIL_NOT_VERIFIED, HttpStatus.PRECONDITION_FAILED);

        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", admin.getId());
        claims.put("role", admin.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
