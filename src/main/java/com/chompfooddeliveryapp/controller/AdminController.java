package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ChangePasswordDto;
import com.chompfooddeliveryapp.payload.AdminInfoResponse;
import com.chompfooddeliveryapp.repository.UserRepository;
import com.chompfooddeliveryapp.service.serviceImpl.UserServiceImpl;
import com.chompfooddeliveryapp.service.serviceInterfaces.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository repo;
    private final AdminService adminService;
    private final UserServiceImpl userService;

    public AdminController(UserRepository repo, AdminService adminService, UserServiceImpl userService) {
        this.repo = repo;
        this.adminService = adminService;
        this.userService = userService;
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

    @PostMapping("/change_password/{id}")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordDto changePasswordDto, @PathVariable Long id){
        userService.changePassword(changePasswordDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
