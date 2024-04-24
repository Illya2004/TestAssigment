package com.kolis1on.testassigment.service;

import com.kolis1on.testassigment.entity.User;
import com.kolis1on.testassigment.exception.DatesAreNotValidException;
import com.kolis1on.testassigment.exception.UserIsYoungerException;
import com.kolis1on.testassigment.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class UserService {

    @Value("${validation.minimal-age}")
    private String minimalAge;

    @Autowired
    private UserRepository userRepository;


    // We are getting dates in this format: yyyy-MM-dd, and then parse it
    public List<User> findByBirthRange(String from, String to) throws Exception {
        LocalDate fromDate = parseDateFromString(from);
        LocalDate toDate = parseDateFromString(to);

        if(!fromDate.isBefore(toDate))
            throw new DatesAreNotValidException("Please, be sure that dates are valid!");

        return userRepository.getUsers().stream().
                filter(user ->
                        parseDateFromString(user.getBirthDate()).isAfter(fromDate)
                            && parseDateFromString(user.getBirthDate()).isBefore(toDate)
                )
                .toList();

    }
    public void createUser(User user) throws Exception {
        if (!ifUserOlder(user.getBirthDate())) {
            throw new UserIsYoungerException("User should be older than " + minimalAge);
        }

        userRepository.getUsers().add(user);
    }

    public void updateUser(String email, User user) {
        userRepository.getUsers().removeIf(u -> u.getEmail().equals(email));
        userRepository.getUsers().add(user);
    }
    public void deleteUserByEmail(String email) {
        userRepository.getUsers().removeIf(user -> user.getEmail().equals(email));
    }


    private boolean ifUserOlder(String birthDate){
        LocalDate currentDate = LocalDate.now();

        System.out.println(birthDate);
        Period period = Period.between(parseDateFromString(birthDate), currentDate);

        return period.getYears() >= 18;
    }

    private LocalDate parseDateFromString(String date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (date == null) {
            throw new IllegalArgumentException("Input date string is null");
        }
        return LocalDate.parse(date, formatter);
    }

}



