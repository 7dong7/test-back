package org.myback.testback.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.myback.testback.controller.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class TestController {

// ===================== 응답 테스트 =============================
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

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 응답
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


// ===================== 요청 테스트 =============================

    @PostMapping("/api/requestData")
    public ResponseEntity<User> requestDataTest(@RequestBody User user) {
        log.info("user= {}",user); // 정보 출력

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK); // 200 응답
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 응답
        }
    }


    @GetMapping("/api/path/{id}")
    public ResponseEntity<Long> requestDataTest(@PathVariable("id") Long id) {
        log.info("id: {}",id);

        return new ResponseEntity<>(id, HttpStatus.OK); // 200 응답
    }
    
// ============== header & cookie 응답 =================

    @PostMapping("/api/HeaderAndCookie")
    public ResponseEntity<Long> responseHeaderAndCookie(HttpServletRequest request, HttpServletResponse response) {
        log.info("HeaderAndCookie 요청 들어옴");
        // 응답 헤더 설정
        response.setHeader("access", "ExamJwtAccessToken");
        // 쿠키 추가
        response.addCookie(createCookie());
        return new ResponseEntity<>(123L, HttpStatus.OK);
    }

    @PostMapping("/api/RequestHeader")
    public ResponseEntity<Long> requestHeader(HttpServletRequest request, HttpServletResponse response) {
        log.info("RequestHeader 요청 들어옴");
        String access = request.getHeader("access");
        log.info("access: {}",access);

        return new ResponseEntity<>(123L, HttpStatus.OK);
    }


    // === 쿠키 생성 기능 ====
    public Cookie createCookie() {
        Cookie cookie = new Cookie("refresh", "newCreateCookieExamRefreshToken"); // 쿠키 만들기
        cookie.setMaxAge(24*60*60); // 쿠키 유효 기간 설정 (초 단위)
        //cookie.setSecure(true); // HTTPS 의 보안 상태에서만 쿠키 유효 설정
        //cookie.setPath("/");  // 애플리케이션내의 모든 경로에서 쿠키가 유효하게 설정
        cookie.setHttpOnly(true); // HttpOnly 쿠키가 클라이언트 측 스크립트에서 접근할 수 없게 된다 (XSS) 공격 보호 설정

        return cookie;
    }
}

