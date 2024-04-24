package com.kolis1on.testassigment.dto;

import com.kolis1on.testassigment.entity.User;
import lombok.Data;

@Data
public class UpdateUserDTO {

   private String email;
   private User user;
}
