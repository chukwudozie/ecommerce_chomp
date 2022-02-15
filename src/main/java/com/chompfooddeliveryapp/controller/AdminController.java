package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.payload.AdminInfoResponse;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AdminController {

    private final UserRepository repo;
    private final AdminService adminService;

    public AdminController(UserRepository repo, AdminService adminService) {
        this.repo = repo;
        this.adminService = adminService;
    }


    @GetMapping("/account")
    public ResponseEntity<AdminInfoResponse> displayAdminInfo(@RequestParam("email") String email){

        return adminService.getAdminInfo(email);
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getAppStatistics(@RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "3") Integer pageSize
    ){
        return ResponseEntity.ok(adminService.getStatistics(pageNo, pageSize));
    }

    @GetMapping("/statistics/orders")
    public ResponseEntity<?> getOrdersByOrderStatus(@RequestParam(defaultValue = "DELIVERED") String status
    ){
        return ResponseEntity.ok(adminService.ordersByStatus(status));
    }

    @GetMapping("/statistics/demographic")
    public ResponseEntity<?> getUsersByGender(@RequestParam(defaultValue = "FEMALE") String gender
    ){
        return ResponseEntity.ok(adminService.allUsersByGender(gender));
    }
}
