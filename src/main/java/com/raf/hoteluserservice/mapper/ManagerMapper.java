package com.raf.hoteluserservice.mapper;

import com.raf.hoteluserservice.domain.Manager;
import com.raf.hoteluserservice.dto.ManagerCreateDto;
import com.raf.hoteluserservice.dto.ManagerDto;
import com.raf.hoteluserservice.repository.ManagerRepository;
import com.raf.hoteluserservice.repository.RoleRepository;
import org.springframework.stereotype.Component;


@Component
public class ManagerMapper {

    private RoleRepository roleRepository;

    public ManagerMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public ManagerDto managerToManagerDto(Manager manager) {

        ManagerDto managerDto = new ManagerDto();
        managerDto.setEmail(manager.getEmail());
        managerDto.setId(manager.getId());
        managerDto.setFirstName(manager.getFirstName());
        managerDto.setLastName(manager.getLastName());
        managerDto.setUsername(manager.getUsername());
        managerDto.setPhoneNumber(manager.getPhoneNumber());
        managerDto.setBirthday(manager.getBirthday());
        managerDto.setHotel(manager.getHotel());
        managerDto.setAccess(manager.isAccess());
        return managerDto;
    }

    public Manager managerCreateDtoToManager(ManagerCreateDto managerCreateDto) {

        Manager manager = new Manager();
        manager.setEmail(managerCreateDto.getEmail());
        manager.setFirstName(managerCreateDto.getFirstName());
        manager.setLastName(managerCreateDto.getLastName());
        manager.setUsername(managerCreateDto.getUsername());
        manager.setPassword(managerCreateDto.getPassword());
        manager.setPhoneNumber(managerCreateDto.getPhoneNumber());
        manager.setBirthday(managerCreateDto.getBirthday());
        manager.setEmploymentDate(managerCreateDto.getEmploymentDate());
        manager.setRole(roleRepository.findRoleByName("ROLE_MANAGER").get());
        manager.setHotel(managerCreateDto.getHotel());
        manager.setAccess(true);
        manager.setVerifiedMail(false);
        return manager;
    }
}
