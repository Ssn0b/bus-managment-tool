package com.snob.busmanagmenttool;

import com.snob.busmanagmenttool.auth.RegisterRequest;
import com.snob.busmanagmenttool.auth.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.snob.busmanagmenttool.model.entity.user.Role.ADMIN;
import static com.snob.busmanagmenttool.model.entity.user.Role.MANAGER;

@SpringBootApplication
@EnableScheduling
public class BusManagmentToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusManagmentToolApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("admin@mail.com")
//                    .password("admin")
//                    .username("admin")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequest.builder()
//                    .firstname("manager")
//                    .lastname("manager")
//                    .email("manager@mail.com")
//                    .password("manager")
//                    .username("manager")
//                    .role(MANAGER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
    //}
}
