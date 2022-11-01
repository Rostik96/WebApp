package com.rost.srg.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30")
    private String name;

    @Min(value = 0, message = "Age should be greater or equals than 0")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    //@Email(message = "Email should be valid")
    private String email;
}
