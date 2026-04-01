package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.User;
import com.example.demo.service.DangNhapService;
import com.example.demo.service.DangNhapService.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class DangNhapController {

    private final DangNhapService dangNhapService;

    public DangNhapController(DangNhapService dangNhapService) {
        this.dangNhapService = dangNhapService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> dangNhap(@RequestBody LoginRequest request) {
        try {
            System.out.println("username = " + request.getUsername());
            System.out.println("password = " + request.getPassword());

            UserView userView = dangNhapService.dangNhap(
                    request.getUsername(),
                    request.getPassword()
            );

            System.out.println("userView = " + userView);

            if (userView == null) {
                return ResponseEntity
                        .badRequest()
                        .body("Sai username hoặc password");
            }

            return ResponseEntity.ok(userView);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi đăng nhập: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> dangKy(@RequestBody User user) {
        try {
            System.out.println("register user = " + user);
            System.out.println("register username = " + user.getUsername());
            System.out.println("register password = " + user.getPassword());

            if (user.getUsername() == null || user.getUsername().isBlank()
                    || user.getPassword() == null || user.getPassword().isBlank()) {
                return ResponseEntity
                        .badRequest()
                        .body("Username hoặc password không được để trống");
            }

            boolean success = dangNhapService.dangKy(user, user.getPassword());

            if (!success) {
                return ResponseEntity
                        .badRequest()
                        .body("Username đã tồn tại");
            }

            return ResponseEntity.ok("Đăng ký thành công");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi đăng ký: " + e.getMessage());
        }
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<?> kiemTraUsername(@PathVariable String username) {
        try {
            boolean exists = dangNhapService.kiemTraTonTaiUsername(username);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Lỗi kiểm tra username: " + e.getMessage());
        }
    }
}