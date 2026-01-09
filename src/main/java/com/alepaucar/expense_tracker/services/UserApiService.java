package com.alepaucar.expense_tracker.services;

import com.alepaucar.expense_tracker.DTO.UserReqDTO;
import com.alepaucar.expense_tracker.DTO.UserResDTO;
import com.alepaucar.expense_tracker.exceptions.NotFoundException;
import com.alepaucar.expense_tracker.model.User;
import com.alepaucar.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserApiService {
    private final UserRepository userRepository;

    public UserApiService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    private UserResDTO toUserDTO(User user){
        return new UserResDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserResDTO getUserFromId(Long userId) {
        return toUserDTO(getUser(userId));
    }

    public List<UserResDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> toUserDTO(user))
                .toList();
    }

    public UserResDTO addNewUser(UserReqDTO userReqDTO) {
        User user = new User(
                userReqDTO.getUsername(),
                userReqDTO.getEmail(),
                userReqDTO.getFirstName(),
                userReqDTO.getLastName()
        );
        userRepository.save(user);
        return toUserDTO(user);
    }

    public UserResDTO updateExistingUser(Long userId, UserReqDTO userReqDTO) {
        User user = getUser(userId);
        user.setUsername(userReqDTO.getUsername());
        user.setEmail(userReqDTO.getEmail());
        user.setFirstName(userReqDTO.getFirstName());
        user.setLastName(userReqDTO.getLastName());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return toUserDTO(user);
    }

    public void deleteUserFromId(Long userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }
}
