package com.raf.hoteluserservice.service.impl;
import com.raf.hoteluserservice.domain.ClientRank;
import com.raf.hoteluserservice.dto.ClientDto;
import com.raf.hoteluserservice.dto.ClientRankDto;
import com.raf.hoteluserservice.dto.ClientRankUpdateDto;
import com.raf.hoteluserservice.exception.NotFoundException;
import com.raf.hoteluserservice.mapper.ClientRankMapper;
import com.raf.hoteluserservice.repository.ClientRankRepository;
import com.raf.hoteluserservice.service.ClientRankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientRankServiceImpl implements ClientRankService {

    private ClientRankRepository clientRankRepository;
    private ClientRankMapper clientRankMapper;


    public ClientRankServiceImpl(ClientRankRepository clientRankRepository, ClientRankMapper clientRankMapper) {
        this.clientRankRepository = clientRankRepository;
        this.clientRankMapper = clientRankMapper;

    }

    @Override
    public ClientRankDto updateClientRank(String name, ClientRankUpdateDto clientRankUpdateDto) {
        ClientRank clientRank = clientRankRepository
                .findRankByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Client rank with name: %s not found.", name)));

        clientRank.setMaxNumberOfReservations(clientRankUpdateDto.getMaxNumberOfReservations());
        clientRank.setMinNumberOfReservations(clientRankUpdateDto.getMinNumberOfReservations());
        clientRank.setDiscount(clientRankUpdateDto.getDiscount());
        return clientRankMapper.clientRankToClientRankDto(clientRankRepository.save(clientRank));
    }

    @Override
    public Page<ClientRankDto> findAll(Pageable pageable) {
        return clientRankRepository.findAll(pageable)
                .map(clientRankMapper::clientRankToClientRankDto);
    }
}
