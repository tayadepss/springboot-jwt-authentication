package com.parent.userservice.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parent.userservice.entity.UserInfo;
import com.parent.userservice.repository.UserInfoRepository;
import com.parent.userservice.userUtils.UserInfoDetails;

import lombok.extern.slf4j.Slf4j; 
  
@Service
@Slf4j
public class UserInfoService implements UserDetailsService { 
  
    @Autowired
    private UserInfoRepository repository; 
  
    @Autowired
    private PasswordEncoder encoder; 
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
  
        Optional<UserInfo> optionalUser = repository.findByName(username); 
        log.info("load user by username exicuted");
        // Converting userDetail to UserDetails 
        return optionalUser.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
  
    public String addUser(UserInfo userInfo) { 
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo); 
        return "User Added Successfully"; 
    } 
  
  
} 