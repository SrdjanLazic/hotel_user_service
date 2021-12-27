package com.raf.hoteluserservice.service.impl;

import com.raf.hoteluserservice.domain.Client;
import com.raf.hoteluserservice.domain.ClientRank;
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
import com.raf.hoteluserservice.service.ClientService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private TokenService tokenService;
    private ClientRepository clientRepository;
    private ClientRankRepository clientRankRepository;
    private ClientMapper clientMapper;
    private JmsTemplate jmsTemplate;
    private String addClientDestination;
    private MessageHelper messageHelper;

    public ClientServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientRankRepository clientRankRepository, ClientMapper clientMapper,
                             ManagerMapper managerMapper, @Value("${destination.addClient}") String addClientDestination, ManagerRepository managerRepository, JmsTemplate jmsTemplate,
                             MessageHelper messageHelper) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientRankRepository = clientRankRepository;
        this.clientMapper = clientMapper;
        this.jmsTemplate = jmsTemplate;
        this.addClientDestination = addClientDestination;
        this.messageHelper = messageHelper;
    }

    @Override
    public Page<ClientDto> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::clientToClientDto);
    }


    @Override
    public ClientDto addClient(ClientCreateDto clientCreateDto) {
        Client client = clientMapper.clientCreateDtoToClient(clientCreateDto);
        clientRepository.save(client);
        // TODO: slanje mejla
        jmsTemplate.convertAndSend(addClientDestination, messageHelper.createTextMessage(clientCreateDto.getEmail()));
        return clientMapper.clientToClientDto(client);
    }



    @Override
    public ClientDto getClient(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d not found.", id)));
        return clientMapper.clientToClientDto(client);
    }



    // Dohvatamo popust koji klijent ima
    @Override
    public DiscountDto findDiscount(Long id) {
        // Dohvati klijenta (instancu klase) na osnovu njegovog id
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String
                .format("User with id: %d not found.", id)));
        // Izlista sve klijentske rankove
        List<ClientRank> clientRankList = clientRankRepository.findAll();
        //get discount
        Double discount = clientRankList.stream()
                .filter(clientRank -> clientRank.getMaxNumberOfReservations() >= client.getNumberOfReservations()
                        && clientRank.getMinNumberOfReservations() <= client.getNumberOfReservations())
                .findAny()
                .get()
                .getDiscount();
        return new DiscountDto(discount); // vraca DiscountDTO, wrapper oko popusta
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials
        Client client = clientRepository
                .findClientByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with username: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));

        // TODO: treba implementirati ovu proveru da li je client verifikovan
//        if (!client.getVerified())
//            throw new CustomException("Please verify your e-mail address before logging in", ErrorCode.EMAIL_NOT_VERIFIED, HttpStatus.PRECONDITION_FAILED);

        //Create token payload

        if (!client.isAccess())
            throw new CustomException("Your account is banned. Contact customer support.", ErrorCode.ACCESS_DENIED, HttpStatus.PRECONDITION_FAILED);


        Claims claims = Jwts.claims();
        claims.put("id", client.getId());
        claims.put("role", client.getRole().getName());
        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }

    @Override
    public void banClient(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));

        client.setAccess(false);
        clientRepository.save(client);
    }

    @Override
    public void unbanClient(Long id) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));

        client.setAccess(true);
        clientRepository.save(client);
    }

    // TODO proveriti sta treba da vraca ova metoda???
    @Override
    public ClientDto updateClientProfile(Long id, ClientUpdateDto clientUpdateDto) {
        Client client = clientRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d not found.", id)));
        // TODO: proveriti da li je korisnik uneo neki novi mejl, ako jeste, poslati mu verifikaciju na novi mejl,
        // ako nije, samo nastavljamo dalje


        client.setFirstName(clientUpdateDto.getFirstName());
        client.setLastName((clientUpdateDto.getLastName()));
        client.setUsername(clientUpdateDto.getUsername());
        client.setEmail((clientUpdateDto.getEmail()));
        client.setPhoneNumber((clientUpdateDto.getPhoneNumber()));
        client.setPassportNumber(clientUpdateDto.getPassportNumber());
        return clientMapper.clientToClientDto(clientRepository.save(client));
    }
}
