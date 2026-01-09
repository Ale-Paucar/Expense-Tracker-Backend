package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserApiService {
    private final UserRepository userRepository;

    public UserApiService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
