package com.raf.hoteluserservice.service;

import com.raf.hoteluserservice.dto.ClientDto;
import com.raf.hoteluserservice.dto.ClientRankDto;
import com.raf.hoteluserservice.dto.ClientRankUpdateDto;
import com.raf.hoteluserservice.dto.ClientUpdateDto;

public interface ClientRankService {
    ClientRankDto updateClientRank(String name, ClientRankUpdateDto clientRankUpdateDto);
}
