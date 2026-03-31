package com.example.demo.controller;

import com.example.demo.service.ThongKeService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/thong-ke")
@Validated
public class ThongKeController {

    private final ThongKeService thongKeService;

    // ✅ Constructor injection – ĐÚNG CHUẨN
    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    // ========================== DOANH THU ==========================

    @GetMapping("/doanh-thu/ngay")
    public ResponseEntity<Double> doanhThuNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(thongKeService.tinhDoanhThuNgay(date));
    }

    @GetMapping("/doanh-thu/thang")
    public ResponseEntity<Double> doanhThuThang(
            @RequestParam @NotBlank String month) {

        return ResponseEntity.ok(
                thongKeService.tinhDoanhThuThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/doanh-thu/nam")
    public ResponseEntity<Double> doanhThuNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(thongKeService.tinhDoanhThuNam(year));
    }

    // ========================== TIỀN NHẬP ==========================

    @GetMapping("/nhap/ngay")
    public ResponseEntity<Double> tienNhapNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(thongKeService.tinhTienNhapNgay(date));
    }

    @GetMapping("/nhap/thang")
    public ResponseEntity<Double> tienNhapThang(
            @RequestParam @NotBlank String month) {

        return ResponseEntity.ok(
                thongKeService.tinhTienNhapThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/nhap/nam")
    public ResponseEntity<Double> tienNhapNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(thongKeService.tinhTienNhapNam(year));
    }

    // ========================== SỐ LƯỢNG BÁN ==========================

    @GetMapping("/so-luong-ban/ngay")
    public ResponseEntity<Integer> soLuongBanNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(thongKeService.tinhSoLuongBanNgay(date));
    }

    @GetMapping("/so-luong-ban/thang")
    public ResponseEntity<Integer> soLuongBanThang(
            @RequestParam @NotBlank String month) {

        return ResponseEntity.ok(
                thongKeService.tinhSoLuongBanThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/so-luong-ban/nam")
    public ResponseEntity<Integer> soLuongBanNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(thongKeService.tinhSoLuongNhapNam(year));
    }

    // ========================== SỐ LƯỢNG NHẬP ==========================

    @GetMapping("/so-luong-nhap/ngay")
    public ResponseEntity<Integer> soLuongNhapNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(thongKeService.tinhSoLuongNhapNgay(date));
    }

    @GetMapping("/so-luong-nhap/thang")
    public ResponseEntity<Integer> soLuongNhapThang(
            @RequestParam @NotBlank String month) {

        return ResponseEntity.ok(
                thongKeService.tinhSoLuongNhapThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/so-luong-nhap/nam")
    public ResponseEntity<Integer> soLuongNhapNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(thongKeService.tinhSoLuongNhapNam(year));
    }

    // ========================== LỢI NHUẬN ==========================

    @GetMapping("/loi-nhuan/ngay")
    public ResponseEntity<Double> loiNhuanNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(thongKeService.tinhLoiNhuanNgay(date));
    }

    @GetMapping("/loi-nhuan/thang")
    public ResponseEntity<Double> loiNhuanThang(
            @RequestParam @NotBlank String month) {

        return ResponseEntity.ok(
                thongKeService.tinhLoiNhuanThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/loi-nhuan/nam")
    public ResponseEntity<Double> loiNhuanNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(thongKeService.tinhLoiNhuanNam(year));
    }
}