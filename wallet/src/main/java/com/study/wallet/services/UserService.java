package com.study.wallet.services;

import com.study.wallet.domain.user.User;
import com.study.wallet.domain.user.UserType;
import com.study.wallet.dtos.UserDTO;
import com.study.wallet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("Merchant not authorized to make transactions");
        }

        if(sender.getBalance().compareTo(amount) <0 ) {
            throw new Exception("Insufficient Balance");
        }
    }

    public User findUserById(Long id) throws Exception {
       return this.repository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public User createUer(UserDTO data){
        User newUser = new User(data);
        this.saveUser(newUser);
        return  newUser;
    }

    public List<User> getAllUsers(){
        return this.repository.findAll();
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
