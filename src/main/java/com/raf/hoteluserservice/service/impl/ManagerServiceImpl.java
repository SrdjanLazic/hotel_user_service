package com.raf.hoteluserservice.service.impl;

import com.raf.hoteluserservice.domain.Manager;
import com.raf.hoteluserservice.dto.ManagerCreateDto;
import com.raf.hoteluserservice.dto.ManagerDto;
import com.raf.hoteluserservice.dto.TokenRequestDto;
import com.raf.hoteluserservice.dto.TokenResponseDto;
import com.raf.hoteluserservice.exception.NotFoundException;
import com.raf.hoteluserservice.mapper.ClientMapper;
import com.raf.hoteluserservice.mapper.ManagerMapper;
import com.raf.hoteluserservice.repository.ClientRankRepository;
import com.raf.hoteluserservice.repository.ClientRepository;
import com.raf.hoteluserservice.repository.ManagerRepository;
import com.raf.hoteluserservice.security.service.TokenService;
import com.raf.hoteluserservice.service.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private ManagerRepository managerRepository;

    public ManagerServiceImpl(TokenService tokenService, ClientRepository clientRepository, ClientRankRepository clientRankRepository, ClientMapper clientMapper,
                              ManagerMapper managerMapper, ManagerRepository managerRepository) {
        this.tokenService = tokenService;
        this.clientRepository = clientRepository;
        this.clientRankRepository = clientRankRepository;
        this.clientMapper = clientMapper;
        this.managerMapper = managerMapper;
        this.managerRepository = managerRepository;
    }



    @Override
    public ManagerDto addManager(ManagerCreateDto managerCreateDto) {
        Manager manager = managerMapper.managerCreateDtoToManager(managerCreateDto);
        managerRepository.save(manager);
        // TODO: slanje mejla
        return managerMapper.managerToManagerDto(manager);
    }


    @Override
    public Page<ManagerDto> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable)
                .map(managerMapper::managerToManagerDto);
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
        return null;
    }
}
