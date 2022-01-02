package com.raf.hoteluserservice.service;

import com.raf.hoteluserservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClientService {
    //TODO - dodati funkcionalnosti ako je potrebno

    Page<ClientDto> findAll(Pageable pageable);

    ClientDto findById(Long id);

    ClientDto addClient(ClientCreateDto clientCreateDto);

    ClientDto getClient(Long id);

    DiscountDto findDiscount(Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void banClient(Long id);

    void unbanClient(Long id);

    ClientDto updateClientProfile(Long id, ClientUpdateDto clientUpdateDto);

    void verifyMail(String email);

}
