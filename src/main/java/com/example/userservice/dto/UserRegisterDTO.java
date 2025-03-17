package com.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank(message = "名不能为空")
    private String firstName;
    @NotBlank(message = "姓不能为空")
    private String lastName;
    @NotBlank(message = "密码不能为空")
    private String password;
    @Email(message = "邮箱格式错误")
    @NotBlank(message = "用户邮箱不能为空")
    private String email;
    @NotNull(message = "用户类型不能为空")
    private Integer roleType;
}
