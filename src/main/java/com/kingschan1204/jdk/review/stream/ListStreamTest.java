package com.kingschan1204.jdk.review.stream;

import com.kingschan1204.jdk.review.stream.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kingschan
 * 2024-07-25
 */
@DisplayName("list stream 测试")
public class ListStreamTest {

    List<UserDto> list = Arrays.asList(
            new UserDto(2, "张三", 18, "男"),
            new UserDto(1, "李四", 16, "男"),
            new UserDto(4, "貂蝉", 20, "女"),
            new UserDto(3, "吕布", 22, "男"),
            new UserDto(5, "小乔", 17, "女")
    );

    @DisplayName("group by分组")
    @Test
    public void groupTest() {
        // 根据性别分组
        Map<String, List<UserDto>> map = list.stream().collect(Collectors.groupingBy(UserDto::getSex));
        System.out.println(map);
    }

    @DisplayName("根据性别分组 取年龄最大的那个")
    @Test
    public void groupLimitTest() {
        Map<String, UserDto> map = list.stream().collect(Collectors.groupingBy(UserDto::getSex,
                Collectors.collectingAndThen(Collectors.toList(), value -> value.stream().max(Comparator.comparing(UserDto::getAge)).get())
                ));
        System.out.println(map);
    }

    @DisplayName("根据性别分组 过滤年龄小于18岁的")
    @Test
    public void groupFilterTest() {
        Map<String, List<UserDto>> map = list.stream().collect(Collectors.groupingBy(UserDto::getSex,
                Collectors.collectingAndThen(Collectors.toList(), value -> value.stream().filter( u -> u.getAge() >= 18).collect(Collectors.toList()))
        ));
        System.out.println(map);
    }

    @DisplayName("排序")
    @Test
    public void sortAsc(){
        List<UserDto> userList = list.stream().sorted(Comparator.comparing(UserDto::getNo)).collect(Collectors.toList());
        System.out.println(userList);
    }

    @DisplayName("倒序")
    @Test
    public void sortDesc(){
        List<UserDto> userList = list.stream().sorted(Comparator.comparing(UserDto::getNo).reversed()).collect(Collectors.toList());
        System.out.println(userList);
    }

    @DisplayName("按中文排序")
    @Test
    public void sortByCn(){
        list.sort((s1, s2) -> {
            //会按首字母进行排序
            Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);
            return comparator.compare(s1.getName(), s2.getName());
        });
        System.out.println(list);

    }
}
