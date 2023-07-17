package com.hughtran.videoplatform.service;


import com.hughtran.videoplatform.dto.UserInfoDTO;
import com.hughtran.videoplatform.model.User;
import com.hughtran.videoplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;

    public void register(UserInfoDTO userInfoDTO) {
        Optional<User> existingUserOpt = userRepository.findByEmailAddress(userInfoDTO.getEmail());
        if (existingUserOpt.isPresent()) {
            userInfoDTO.setId(existingUserOpt.get().getId());
            return;
        }
        var user = new User();
        user.setSub(userInfoDTO.getSub());
        user.setEmailAddress(userInfoDTO.getEmail());
        user.setFirstName(userInfoDTO.getGivenName());
        user.setLastName(userInfoDTO.getFamilyName());
        user.setFullName(userInfoDTO.getName());
        user.setPicture(userInfoDTO.getPicture());
        user.setPicture(userInfoDTO.getPicture());
        userRepository.save(user);
    }
}