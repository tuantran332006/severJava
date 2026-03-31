package com.example.demo.controller;

import com.example.demo.model.LoSanPham;
import com.example.demo.service.LoSanPhamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/lo-san-pham")
@Validated
public class LoSanPhamController {

    private final LoSanPhamService loSanPhamService;

    // ✅ Constructor injection – ĐÚNG CHUẨN
    public LoSanPhamController(LoSanPhamService loSanPhamService) {
        this.loSanPhamService = loSanPhamService;
    }

    // ========================= CRUD =========================

    @PostMapping
    public ResponseEntity<LoSanPham> taoLo(@Valid @RequestBody LoSanPham request) {
        boolean ok = loSanPhamService.themLoSanPham(request);
        if (ok) return ResponseEntity.status(HttpStatus.CREATED).body(request);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatLo(
            @PathVariable("id") int id,
            @Valid @RequestBody LoSanPham request) {

        request.setId_lo(id);
        boolean ok = loSanPhamService.capNhatLoSanPham(request);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaLo(@PathVariable("id") int id) {
        boolean ok = loSanPhamService.xoaLoSanPham(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoSanPham> layTheoId(@PathVariable("id") int id) {
        LoSanPham lo = loSanPhamService.timLoTheoId(id);
        return lo != null ? ResponseEntity.ok(lo) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<LoSanPham>> layTatCa() {
        return ResponseEntity.ok(loSanPhamService.layTatCaLoSanPham());
    }

    // ========================= THEO NGÀY =========================

    @GetMapping("/by-date")
    public ResponseEntity<List<LoSanPham>> timTheoNgayNhap(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(loSanPhamService.timTheoNgayNhap(date));
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<LoSanPham>> timTheoThangNhap(
            @RequestParam("month") String month) {
        return ResponseEntity.ok(loSanPhamService.timTheoThangNhap(YearMonth.parse(month)));
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<LoSanPham>> timTheoNamNhap(
            @RequestParam("year") int year) {
        return ResponseEntity.ok(loSanPhamService.timTheoNamNhap(year));
    }

    // ========================= HẠN SỬ DỤNG =========================

    @GetMapping("/expiry/on")
    public ResponseEntity<List<LoSanPham>> timTheoHanSuDung(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(loSanPhamService.timTheoHanSuDung(date));
    }

    @GetMapping("/expiry/between")
    public ResponseEntity<List<LoSanPham>> timTheoKhoangHan(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        if (end.isBefore(start)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(loSanPhamService.timTheoKhoangHan(start, end));
    }

    @GetMapping("/expiry/within")
    public ResponseEntity<List<LoSanPham>> loSapHetHanTrong(
            @RequestParam("days") @Min(1) int soNgay) {
        return ResponseEntity.ok(loSanPhamService.loSapHetHanTrong(soNgay));
    }

    @GetMapping("/expiry/expired")
    public ResponseEntity<List<LoSanPham>> loHetHan() {
        return ResponseEntity.ok(loSanPhamService.loHetHan());
    }

    // ========================= THEO SẢN PHẨM =========================

    @GetMapping("/by-sp/{idSp}")
    public ResponseEntity<List<LoSanPham>> timTheoSanPham(@PathVariable int idSp) {
        return ResponseEntity.ok(loSanPhamService.timTheoSanPham(idSp));
    }

    @GetMapping("/by-sp/{idSp}/con-hang")
    public ResponseEntity<List<LoSanPham>> timTheoSanPhamConHang(@PathVariable int idSp) {
        return ResponseEntity.ok(loSanPhamService.timTheoSanPhamConHang(idSp));
    }

    @GetMapping("/by-sp/{idSp}/sap-het-han")
    public ResponseEntity<List<LoSanPham>> timTheoSanPhamSapHetHan(@PathVariable int idSp) {
        return ResponseEntity.ok(loSanPhamService.timTheoSanPhamSapHetHan(idSp));
    }

    @GetMapping("/by-pn/{idPn}")
    public ResponseEntity<List<LoSanPham>> timTheoPhieuNhap(@PathVariable int idPn) {
        return ResponseEntity.ok(loSanPhamService.timTheoPhieuNhap(idPn));
    }

    // ========================= TỒN KHO =========================

    @GetMapping("/con-hang")
    public ResponseEntity<List<LoSanPham>> layLoConHang() {
        return ResponseEntity.ok(loSanPhamService.layLoConHang());
    }

    @PatchMapping("/{id}/so-luong-con")
    public ResponseEntity<Void> capNhatSoLuongCon(
            @PathVariable("id") int idLo,
            @RequestBody @Valid UpdateSoLuongRequest req) {

        boolean ok = loSanPhamService.capNhatSoLuongCon(idLo, req.getSoLuongMoi());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/tang-so-luong")
    public ResponseEntity<Void> tangSoLuongCon(
            @PathVariable("id") int idLo,
            @RequestBody @Valid DieuChinhSoLuongRequest req) {

        boolean ok = loSanPhamService.tangSoLuongCon(idLo, req.getSoLuong());
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/giam-so-luong")
    public ResponseEntity<Void> giamSoLuongCon(
            @PathVariable("id") int idLo,
            @RequestBody @Valid DieuChinhSoLuongRequest req) {

        boolean ok = loSanPhamService.giamSoLuongCon(idLo, req.getSoLuong());
        return ok ? ResponseEntity.noContent().build()
                 : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // ========================= DTO =========================

    public static class UpdateSoLuongRequest {
        @NotNull
        @Min(0)
        private Integer soLuongMoi;

        public Integer getSoLuongMoi() { return soLuongMoi; }
        public void setSoLuongMoi(Integer soLuongMoi) { this.soLuongMoi = soLuongMoi; }
    }

    public static class DieuChinhSoLuongRequest {
        @NotNull
        @Min(1)
        private Integer soLuong;

        public Integer getSoLuong() { return soLuong; }
        public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    }
}