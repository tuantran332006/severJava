package com.example.demo.controller;

import com.example.demo.dto.ApiResult;
import com.example.demo.model.ChiTietPhieuNhap;
import com.example.demo.model.PhieuNhap;
import com.example.demo.service.PhieuNhapService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/phieu-nhap")
@Validated
public class PhieuNhapController {

    private final PhieuNhapService phieuNhapService;

    // ✅ Constructor injection
    public PhieuNhapController(PhieuNhapService phieuNhapService) {
        this.phieuNhapService = phieuNhapService;
    }

    // ========================= TẠO PHIẾU NHẬP =========================

    @PostMapping
    public ResponseEntity<ApiResult<Void>> taoPhieuNhap(
            @Valid @RequestBody CreatePhieuNhapRequest req) {

        boolean ok = phieuNhapService.taoPhieuNhap(
                req.getPhieuNhap(),
                req.getChiTietList(),
                req.isTaoLoTuChiTiet()
        );

        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResult.ok("Tạo phiếu nhập thành công"));
        }

        return ResponseEntity.badRequest()
                .body(ApiResult.fail("PHIEU_NHAP_ERROR", "Tạo phiếu nhập thất bại"));
    }

    // ========================= XOÁ =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<Void>> xoaPhieuNhap(
            @PathVariable int id,
            @RequestParam(defaultValue = "true") boolean restoreInventoryOnDelete) {

        boolean ok = phieuNhapService.xoaPhieuNhap(id, restoreInventoryOnDelete);
        if (ok) {
            return ResponseEntity.ok(
                    ApiResult.ok("Đã xoá phiếu nhập id=" + id)
            );
        }
        return ResponseEntity.badRequest()
                .body(ApiResult.fail("PHIEU_NHAP_DELETE_ERROR", "Xoá thất bại"));
    }

    // ========================= QUERY =========================

    @GetMapping("/{id}")
    public ResponseEntity<PhieuNhap> layTheoId(@PathVariable int id) {
        PhieuNhap pn = phieuNhapService.timPhieuNhapTheoId(id);
        return pn != null ? ResponseEntity.ok(pn)
                          : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PhieuNhap>> layTatCa() {
        return ResponseEntity.ok(phieuNhapService.layTatCaPhieuNhap());
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<PhieuNhap>> layTheoNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(phieuNhapService.layPhieuNhapTheoNgay(date));
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<PhieuNhap>> layTheoThang(
            @RequestParam String month) {

        return ResponseEntity.ok(
                phieuNhapService.layPhieuNhapTheoThang(YearMonth.parse(month))
        );
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<PhieuNhap>> layTheoNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(phieuNhapService.layPhieuNhapTheoNam(year));
    }

    // ========================= THỐNG KÊ =========================

    @GetMapping("/sum/amount/day")
    public double tongTienNgay(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return phieuNhapService.tinhTongTienNhapTheoNgay(date);
    }

    @GetMapping("/sum/amount/month")
    public double tongTienThang(@RequestParam String month) {
        return phieuNhapService.tinhTongTienNhapTheoThang(YearMonth.parse(month));
    }

    @GetMapping("/sum/amount/year")
    public double tongTienNam(
            @RequestParam @Min(1970) @Max(3000) int year) {

        return phieuNhapService.tinhTongTienNhapTheoNam(year);
    }

    // ========================= DTO =========================

    public static class CreatePhieuNhapRequest {

        @NotNull
        @Valid
        private PhieuNhap phieuNhap;

        @NotNull
        @Size(min = 1, message = "Danh sách chi tiết không được rỗng")
        @Valid
        private List<ChiTietPhieuNhap> chiTietList;

        private boolean taoLoTuChiTiet;

        public PhieuNhap getPhieuNhap() {
            return phieuNhap;
        }

        public void setPhieuNhap(PhieuNhap phieuNhap) {
            this.phieuNhap = phieuNhap;
        }

        public List<ChiTietPhieuNhap> getChiTietList() {
            return chiTietList;
        }

        public void setChiTietList(List<ChiTietPhieuNhap> chiTietList) {
            this.chiTietList = chiTietList;
        }

        public boolean isTaoLoTuChiTiet() {
            return taoLoTuChiTiet;
        }

        public void setTaoLoTuChiTiet(boolean taoLoTuChiTiet) {
            this.taoLoTuChiTiet = taoLoTuChiTiet;
        }
    }
}