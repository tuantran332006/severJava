package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.NhanVien;
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

        UserView userView = dangNhapService.dangNhap(
                request.getUsername(),
                request.getPassword()
        );

        if (userView == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Sai username hoặc password");
        }

        return ResponseEntity.ok(userView);
    }

    @PostMapping("/register")
    public ResponseEntity<?> dangKy(
            @RequestBody User user,
            @RequestBody NhanVien nv,
            @RequestParam String password) {

        boolean success = dangNhapService.dangKy(user,nv, password);

        if (!success) {
            return ResponseEntity
                    .badRequest()
                    .body("Username đã tồn tại");
        }

        return ResponseEntity.ok("Đăng ký thành công");
    }

    @GetMapping("/exists/{username}")
    public ResponseEntity<?> kiemTraUsername(@PathVariable String username) {
        boolean exists = dangNhapService.kiemTraTonTaiUsername(username);
        return ResponseEntity.ok(exists);
    }
}