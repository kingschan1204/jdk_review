package com.kingschan1204.jdk.review.stream.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kingschan
 * 2024-7-25
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    Integer no;
    String name;
    Integer age;
    String sex;
}
