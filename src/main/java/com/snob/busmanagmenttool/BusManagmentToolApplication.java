package com.snob.busmanagmenttool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
//                    .password("password")
//                    .username("admin")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
//                    .username("manager")
//                    .role(MANAGER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
//    }
}
