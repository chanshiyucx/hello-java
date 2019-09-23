package com.chanshiyu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiyuNettyApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {
        List<String> l = Stream.of("a","b","c","b")
                .distinct()
                .collect(Collectors.toList());
        System.out.println(l); //[a, b, c]
    }



}
