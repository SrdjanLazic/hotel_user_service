package com.raf.hoteluserservice.service;

import com.raf.hoteluserservice.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerService {

    // TODO

    Page<ManagerDto> findAll(Pageable pageable);

    ManagerDto findById(Long id);

    ManagerDto addManager(ManagerCreateDto managerCreateDto);

    ManagerDto getManager(Long id);

    TokenResponseDto login(TokenRequestDto tokenRequestDto);

    void banManager(Long id);

    void unbanManager(Long id);

    ManagerDto updateManagerProfile(Long id, ManagerUpdateDto managerUpdateDto);

    void verifyMail(String email);

    void changePassword(Long id, ManagerPasswordDto managerPasswordDto);

    void saveNewPassword(Long id);
}
