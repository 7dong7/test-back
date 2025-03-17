package org.myback.testback.controller;

import org.myback.testback.controller.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TestController {

    // 1. 테스트 연결 확인
    @GetMapping("/api/text")
    public String test() {
        return "연결에 성공했습니다";
    }

    // 2. dto 반환 테스트
    @GetMapping("/api/dto")
    public User dtoTest() {

        User user = new User();
        user.setId(0L);
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");

        return user;
    }

    // 3. map 반환 테스트
    @GetMapping("/api/map")
    public Map<String, String> mapTest() {
        Map<String, String> map = new HashMap<>();

        map.put("id", "0");
        map.put("username", "mapUser");
        map.put("email", "mapUser@gmail.com");

        return map;
    }


    // 4. 텍스트 반환 (상태변환)
    @GetMapping("/api/statusTextOk")
    public ResponseEntity<String> statusTextOk() {
        return new ResponseEntity<>("응답 성공", HttpStatus.OK);
    }
    @GetMapping("/api/statusTextBad")
    public ResponseEntity<String> statusTextBad() {
        return new ResponseEntity<>("응답 실패", HttpStatus.BAD_REQUEST);
    }


    // 5. dto 반환 (상태변환)
    @GetMapping("/api/statusDtoOk")
    public ResponseEntity<User> statusDtoOk() {
        User user = new User();
        user.setId(0L);
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");

        return new ResponseEntity<>(user, HttpStatus.OK); // 200 응답
    }
    @GetMapping("/api/statusDtoBad")
    public ResponseEntity<User> statusDtoBad() {
        User user = new User();
        user.setId(0L);
        user.setUsername("user1");
        user.setEmail("user1@gmail.com");

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 200 응답
    }


    // 6. map 반환 (상태변환)
    @GetMapping("/api/statusMapOk")
    public ResponseEntity<Map<String, Object>> statusMapOk() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Map 응답에 성공했습니다");
        map.put("status", "success");
        return new ResponseEntity<>(map, HttpStatus.OK); // 200 응답
    }
    @GetMapping("/api/statusMapBad")
    public ResponseEntity<Map<String, Object>> statusMapBad() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Map 응답에 실패했습니다");
        map.put("status", "error");
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST); // 200 응답
    }
}
