package com.chompfooddeliveryapp.dto;

import com.chompfooddeliveryapp.model.users.User;
import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
//    Optional<User> getUserByPassword(String password);


//@PostMapping("/profile/edit")
//    public void updateUserDetails(@RequestBody EditUserDetailsDto userDetailsDto){
//        userService.updateUser(userDetailsDto);
//
//
//    }
//
//    @PostMapping("/profile/edit/change_password")
//    public void updatePassword(@RequestBody ChangePasswordDto changePasswordDto){
//        userService.changePassword(changePasswordDto);
//    }


// private final UserRepository userRepository;
//
//    @Override
//    public void updateUser(EditUserDetailsDto editUserDetailsDto) {
//        Optional<User> loggedInUser = userRepository.findByEmail(editUserDetailsDto.getEmail());
//        if(loggedInUser.isPresent()){
//            loggedInUser.get().setFirstName(editUserDetailsDto.getFirstname());
////            loggedInUser.get().setFirstName(editUserDetailsDto.getFirstname());
//            loggedInUser.get().setLastName(editUserDetailsDto.getLastname());
//            loggedInUser.get().setEmail(editUserDetailsDto.getEmail());
//            loggedInUser.get().setGender(editUserDetailsDto.getGender());
//            loggedInUser.get().setDob(editUserDetailsDto.getDateOfBirth());
//            userRepository.save(loggedInUser.get());
//        }
//    }
//    @Override
//    public void changePassword(ChangePasswordDto changePasswordDto) {
//        Optional<User> currentUser = userRepository.getUserByPassword(changePasswordDto.getOldPassword());
//        String newPassword = changePasswordDto.getNewPassword();
//        String confirmPassword = changePasswordDto.getConfirmPassword();
//        if (currentUser.isPresent() && newPassword.equals(confirmPassword)) {
//            currentUser.get().setPassword(newPassword);
//            userRepository.save(currentUser.get());
//        }
//    }


//    void changePassword(ChangePasswordDto changePasswordDto);
//    void updateUser(EditUserDetailsDto updateUserDtp);