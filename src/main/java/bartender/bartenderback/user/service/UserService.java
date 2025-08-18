package bartender.bartenderback.user.service;

import bartender.bartenderback.user.domain.User;
import bartender.bartenderback.user.domain.UserRepository;
import bartender.bartenderback.user.dto.request.UserRequest;
import bartender.bartenderback.user.dto.response.UserResponse;
import bartender.bartenderback.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest request) {
        User user = User.of(request);

        User saveUser = userRepository.save(user);
        return UserResponse.from(saveUser);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponse::from)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다."));
    }
}
