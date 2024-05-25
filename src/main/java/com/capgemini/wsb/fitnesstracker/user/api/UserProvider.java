package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserProvider {

    /**
     * Retrieves a user based on their ID.
     * If the user with given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param userId id of the user to be searched
     * @return An {@link Optional} containing the located user, or {@link Optional#empty()} if not found
     */
    Optional<User> getUser(Long userId);

    /**
     * Retrieves a user based on their email.
     *
     * @param email The email to be filtered by
     * @return An {@link List} containing the found users
     */
    List<User> getUsersByEmail(String email);

    /**
     * Retrieves a users based on their age being greater than provided.
     *
     * @param after {@link LocalDate} date to compare
     * @return An {@link List} containing the found users
     */
    List<User> getUsersBornAfter(LocalDate after);

    /**
     * Retrieves all users.
     *
     * @return An {@link Optional} containing the all users,
     */
    List<User> findAllUsers();

}
