package com.example.demo.controller;

import com.example.demo.model.HoaDon;
import com.example.demo.model.HoaDonDetailResponse;
import com.example.demo.model.TaoHoaDonRequest;
import com.example.demo.service.HoaDonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/hoa-don")
@Validated
public class HoaDonController {

    private final HoaDonService hoaDonService;

    public HoaDonController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @PostMapping
    public ResponseEntity<ApiResult> taoHoaDon(@Valid @RequestBody TaoHoaDonRequest request) {
        try {
            if (request == null || request.getHoaDon() == null || request.getChiTietList() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResult.fail("Du lieu tao hoa don khong hop le"));
            }

            boolean ok = hoaDonService.taoHoaDon(request.getHoaDon(), request.getChiTietList());

            if (ok) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResult.success("Tao hoa don thanh cong"));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResult.fail("Tao hoa don that bai"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResult.fail(e.getClass().getSimpleName() + ": " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> suaHoaDon(@PathVariable("id") int id,
                                          @Valid @RequestBody HoaDon request) {
        request.setId_hoa_don(id);
        boolean ok = hoaDonService.suaHoaDon(request);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaHoaDon(@PathVariable("id") int id) {
        boolean ok = hoaDonService.xoaHoaDon(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HoaDon> timTheoId(@PathVariable("id") int id) {
        HoaDon hd = hoaDonService.timHoaDonTheoId(id);
        return hd != null ? ResponseEntity.ok(hd) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<HoaDonDetailResponse> layChiTietHoaDon(@PathVariable("id") int id) {
        HoaDonDetailResponse detail = hoaDonService.layChiTietHoaDon(id);
        return detail != null ? ResponseEntity.ok(detail) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<HoaDon>> layTatCa() {
        return ResponseEntity.ok(hoaDonService.layTatCaHoaDon());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<HoaDon>> layTheoNgay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoNgay(date));
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<HoaDon>> layTheoThang(@RequestParam("month") String month) {
        YearMonth ym = YearMonth.parse(month);
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoThang(ym));
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<HoaDon>> layTheoNam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoNam(year));
    }

    @GetMapping("/sum/amount/day")
    public ResponseEntity<Double> tongDoanhThuNgay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(hoaDonService.tinhTongDoanhThuTheoNgay(date));
    }

    @GetMapping("/sum/amount/month")
    public ResponseEntity<Double> tongDoanhThuThang(@RequestParam("month") String month) {
        YearMonth ym = YearMonth.parse(month);
        return ResponseEntity.ok(hoaDonService.tinhTongDoanhThuTheoThang(ym));
    }

    @GetMapping("/sum/amount/year")
    public ResponseEntity<Double> tongDoanhThuNam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {
        return ResponseEntity.ok(hoaDonService.tinhTongDoanhThuTheoNam(year));
    }

    @GetMapping("/sum/quantity/day")
    public ResponseEntity<Integer> tongSoLuongBanNgay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(hoaDonService.tinhTongSoLuongBanTheoNgay(date));
    }

    @GetMapping("/sum/quantity/month")
    public ResponseEntity<Integer> tongSoLuongBanThang(@RequestParam("month") String month) {
        YearMonth ym = YearMonth.parse(month);
        return ResponseEntity.ok(hoaDonService.tinhTongSoLuongBanTheoThang(ym));
    }

    @GetMapping("/sum/quantity/year")
    public ResponseEntity<Integer> tongSoLuongBanNam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {
        return ResponseEntity.ok(hoaDonService.tinhTongSoLuongBanTheoNam(year));
    }

    public static class ApiResult {
        private boolean success;
        private String message;

        public ApiResult() {}
        public ApiResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public static ApiResult success(String msg) { return new ApiResult(true, msg); }
        public static ApiResult fail(String msg) { return new ApiResult(false, msg); }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
