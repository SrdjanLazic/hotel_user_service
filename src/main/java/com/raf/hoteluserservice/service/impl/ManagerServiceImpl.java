package com.raf.hoteluserservice.service.impl;

import com.raf.hoteluserservice.domain.Client;
import com.raf.hoteluserservice.domain.Manager;
import com.raf.hoteluserservice.dto.*;
import com.raf.hoteluserservice.exception.CustomException;
import com.raf.hoteluserservice.exception.ErrorCode;
import com.raf.hoteluserservice.exception.NotFoundException;
import com.raf.hoteluserservice.listener.helper.MessageHelper;
import com.raf.hoteluserservice.mapper.ClientMapper;
import com.raf.hoteluserservice.mapper.ManagerMapper;
import com.raf.hoteluserservice.repository.ClientRankRepository;
import com.raf.hoteluserservice.repository.ClientRepository;
import com.raf.hoteluserservice.repository.ManagerRepository;
import com.raf.hoteluserservice.security.service.TokenService;
import com.raf.hoteluserservice.service.ManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientRankRepository clientRankRepository;
    private ClientMapper clientMapper;
    private ManagerMapper managerMapper;
    private JmsTemplate jmsTemplate;
    private ManagerRepository managerRepository;
    private String addManagerDestination;
    private MessageHelper messageHelper;

    public ManagerServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientRankRepository clientRankRepository, ClientMapper clientMapper,
                              ManagerMapper managerMapper, @Value("${destination.addManager}") String addManagerDestination, ManagerRepository managerRepository,
                              JmsTemplate jmsTemplate, MessageHelper messageHelper) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientRankRepository = clientRankRepository;
        this.clientMapper = clientMapper;
        this.managerMapper = managerMapper;
        this.managerRepository = managerRepository;
        this.addManagerDestination = addManagerDestination;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
    }



    @Override
    public ManagerDto addManager(ManagerCreateDto managerCreateDto) {
        Manager manager = managerMapper.managerCreateDtoToManager(managerCreateDto);
        managerRepository.save(manager);
        jmsTemplate.convertAndSend(addManagerDestination, messageHelper.createTextMessage(managerCreateDto));
        // TODO: slanje mejla
        return managerMapper.managerToManagerDto(manager);
    }


    @Override
    public Page<ManagerDto> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable)
                .map(managerMapper::managerToManagerDto);
    }

    @Override
    public ManagerDto findById(Long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new NotFoundException("Client with requested id not found"));
        return managerMapper.managerToManagerDto(manager);
    }

    @Override
    public ManagerDto getManager(Long id) {
        Manager manager = managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found. ", id)));
        return managerMapper.managerToManagerDto(manager);
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        Manager manager = managerRepository
                .findManagerByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));

        // TODO: treba implementirati ovu proveru da li je client verifikovan
        if (!manager.isVerifiedMail())
            throw new CustomException("Please verify your e-mail address before logging in", ErrorCode.EMAIL_NOT_VERIFIED, HttpStatus.PRECONDITION_FAILED);

        //Create token payload

        if (!manager.isAccess())
            throw new CustomException("Your account is banned. Contact customer support.", ErrorCode.ACCESS_DENIED, HttpStatus.PRECONDITION_FAILED);


        Claims claims = Jwts.claims();
        claims.put("id", manager.getId());
        claims.put("role", manager.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public void banManager(Long id) {
        Manager manager = managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));

        manager.setAccess(false);
        managerRepository.save(manager);
    }

    @Override
    public void unbanManager(Long id) {
        Manager manager = managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));

        manager.setAccess(true);
        managerRepository.save(manager);
    }

    @Override
    public ManagerDto updateManagerProfile(Long id, ManagerUpdateDto managerUpdateDto) {
        Manager manager = managerRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Manager with id: %d not found.", id)));
        // TODO: proveriti da li je korisnik uneo neki novi mejl, ako jeste, poslati mu verifikaciju na novi mejl,
        // ako nije, samo nastavljamo dalje

        manager.setFirstName(managerUpdateDto.getFirstName());
        manager.setLastName((managerUpdateDto.getLastName()));
        manager.setUsername(managerUpdateDto.getUsername());
        manager.setEmail((managerUpdateDto.getEmail()));
        manager.setPhoneNumber((managerUpdateDto.getPhoneNumber()));
        manager.setPassword((managerUpdateDto.getPassword()));
        return managerMapper.managerToManagerDto(managerRepository.save(manager));
    }

    @Override
    public void verifyMail(String email) {
       Manager manager = managerRepository
                .findManagerByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("User with email: %s not found.", email)));

        manager.setVerifiedMail(true);
        managerRepository.save(manager);
    }
}
