package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<BasicUserInfoDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicUserInfoDto)
                .toList();
    }

    @GetMapping("/filter-by-email")
    public List<UserEmailInfoDto> filterUsersByEmail(
            @RequestParam() String email
    ) {
        return  userService.getUsersByEmail(email)
                .stream()
                .map(userMapper::toUserEmailInfoDto)
                .toList();
    }

    @GetMapping("/filter-by-age")
    public List<BasicUserInfoDto> filterUsersByAge(
            @RequestParam() Integer ageGtThan
    ) {
        return userService.getUsersByAgeGreaterThan(ageGtThan)
                .stream()
                .map(userMapper::toBasicUserInfoDto)
                .toList();
    }

    @GetMapping( "/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUser(userId);

        return user.map(this.userMapper::toDto).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        final User mappedUser = this.userMapper.toEntity(userDto);
        final User createdUser = this.userService.createUser(mappedUser);

        return this.userMapper.toDto(createdUser);
    }

    @PostMapping( "/{userId}/set-first-name")
    public UserDto updateUserFirstName(@PathVariable Long userId, @RequestBody String firstName) {
        Optional<User> updatedUser = this.userService.updateUserFirstName(userId, firstName);
        return updatedUser.map(this.userMapper::toDto).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}