package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Query searching users by email address.
     *
     * @param email email to be filtered by
     * @return {@link List<User>} containing found users
     */
    default List<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> user.getEmail().toUpperCase().contains(email.toUpperCase())).toList();
    }

    /**
     * Query searching users by birthday after provided date
     *
     * @param after {@link LocalDate} date to compare
     * @return {@link List<User>} containing found users
     */
    default List<User> findBornAfter(LocalDate after) {
        return findAll().stream()
                .filter(user -> after.isAfter(user.getBirthdate())).toList();
    }

    /**
     * Update user.
     *
     * @param id id of the user to update
     * @param user {@link User } value to set
     * @return {@link Optional} containing updated user or {@link Optional#empty()} if user was not found
     */
    default Optional<User> updateUser(Long id, User user) {
        Optional<User> userFromDb = findById(id);

        if(userFromDb.isEmpty()) {
            return userFromDb;
        }

        userFromDb.get().setFirstName(user.getFirstName());
        userFromDb.get().setLastName(user.getLastName());
        userFromDb.get().setBirthdate(user.getBirthdate());
        userFromDb.get().setEmail(user.getEmail());

        return Optional.of(this.saveAndFlush(userFromDb.get()));
    }

}
