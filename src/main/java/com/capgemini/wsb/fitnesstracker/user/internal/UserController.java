package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/simple")
    public List<BasicUserInfoDto> getAllSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toBasicUserInfoDto)
                .toList();
    }

    @GetMapping("/email")
    public List<UserEmailInfoDto> filterUsersByEmail(
            @RequestParam() String email
    ) {
        return userService.getUsersByEmail(email)
                .stream()
                .map(userMapper::toUserEmailInfoDto)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUsersBornAfter(
            @PathVariable() @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") LocalDate time
            ) {
        return userService.getUsersBornAfter(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping( "/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUser(userId);

        return user.map(this.userMapper::toDto).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody UserDto userDto) {
        final User mappedUser = this.userMapper.toEntity(userDto);
        final User createdUser = this.userService.createUser(mappedUser);

        return this.userMapper.toDto(createdUser);
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        final User mappedUser = this.userMapper.toEntity(userDto);
        final Optional<User> createdUser = this.userService.updateUser(userId, mappedUser);

        return this.userMapper.toDto(createdUser.orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
    }
}