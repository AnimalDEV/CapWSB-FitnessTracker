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
     * Query searching users by age greater than provided.
     *
     * @param ageGtThan age to be filtered by
     * @return {@link List<User>} containing found users
     */
    default List<User> findByAgeGreaterThan(Integer ageGtThan) {
        return findAll().stream()
                .filter(user -> ChronoUnit.YEARS.between(user.getBirthdate(), LocalDate.now()) > ageGtThan).toList();
    }

    /**
     * Update user firstName by id.
     *
     * @param id id of the user to update
     * @param firstName firstName value to set
     * @return {@link Optional} containing updated user or {@link Optional#empty()} if user was not found
     */
    default Optional<User> updateUserFirstName(Long id, String firstName) {
        Optional<User> user = findById(id);

        if(user.isEmpty()) {
            return user;
        }

        user.get().setFirstName(firstName);

        return Optional.of(this.saveAndFlush(user.get()));
    }

}
