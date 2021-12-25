package com.raf.hoteluserservice.runner;


import com.raf.hoteluserservice.domain.Admin;
import com.raf.hoteluserservice.domain.ClientRank;
import com.raf.hoteluserservice.domain.Rank;
import com.raf.hoteluserservice.domain.Role;
import com.raf.hoteluserservice.repository.AdminRepository;
import com.raf.hoteluserservice.repository.ClientRankRepository;
import com.raf.hoteluserservice.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private AdminRepository adminRepository;
    private ClientRankRepository clientRankRepository;

    public TestDataRunner(RoleRepository roleRepository, AdminRepository adminRepository, ClientRankRepository clientRankRepository) {
        this.roleRepository = roleRepository;
        this.adminRepository = adminRepository;
        this.clientRankRepository = clientRankRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleUser = new Role("ROLE_CLIENT", "User role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
        Role roleManager = new Role("ROLE_MANAGER", "Manager role");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleManager);
        //Insert admin
        Admin admin = new Admin();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        adminRepository.save(admin);
        //User statuses
        clientRankRepository.save(new ClientRank("GOLD", Rank.GOLD, 20, Integer.MAX_VALUE, 0.6));
        clientRankRepository.save(new ClientRank("SILVER", Rank.SILVER, 10, 19, 0.8));
        clientRankRepository.save(new ClientRank("BRONZE", Rank.BRONZE, 0, 9, 1.0));
    }
}
