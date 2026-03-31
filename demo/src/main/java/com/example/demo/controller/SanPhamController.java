package com.example.demo.controller;

import com.example.demo.model.SanPham;
import com.example.demo.service.SanPhamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham")
@Validated
public class SanPhamController {

    private final SanPhamService sanPhamService;

    // ✅ Constructor injection – ĐÚNG CHUẨN SPRING BOOT
    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    // ========================== CRUD ==========================

    /** Tạo mới sản phẩm */
    @PostMapping
    public ResponseEntity<SanPham> taoSanPham(
            @Valid @RequestBody SanPham request) {

        boolean ok = sanPhamService.themSanPham(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.badRequest().build();
    }

    /** Tạo mới và trả về id */
    @PostMapping("/return-id")
    public ResponseEntity<Integer> taoSanPhamVaLayId(
            @Valid @RequestBody SanPham request) {

        int id = sanPhamService.themSanPhamVaLayId(request);
        if (id > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        }
        return ResponseEntity.badRequest().body(-1);
    }

    /** Cập nhật sản phẩm */
    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatSanPham(
            @PathVariable int id,
            @Valid @RequestBody SanPham request) {

        request.setIdSp(id); // ✅ đúng
        boolean ok = sanPhamService.capNhatSanPham(request);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    /** Xoá sản phẩm */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaSanPham(@PathVariable int id) {
        boolean ok = sanPhamService.xoaSanPham(id);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    // ========================== QUERY ==========================

    /** Lấy theo id */
    @GetMapping("/{id}")
    public ResponseEntity<SanPham> layTheoId(@PathVariable int id) {
        SanPham sp = sanPhamService.timTheoId(id);
        return sp != null ? ResponseEntity.ok(sp)
                          : ResponseEntity.notFound().build();
    }

    /** Lấy tất cả */
    @GetMapping
    public ResponseEntity<List<SanPham>> layTatCa() {
        return ResponseEntity.ok(sanPhamService.layTatCa());
    }

    /** Tìm theo loại */
    @GetMapping("/by-loai")
    public ResponseEntity<List<SanPham>> timTheoLoai(
            @RequestParam @Min(1) int idLoai) {

        return ResponseEntity.ok(sanPhamService.timTheoLoai(idLoai));
    }

    /** Tìm theo tên */
    @GetMapping("/search")
    public ResponseEntity<List<SanPham>> timTheoTen(
            @RequestParam("q") @NotBlank String ten) {

        return ResponseEntity.ok(sanPhamService.timTheoTen(ten));
    }

    /** Tìm theo khoảng giá */
    @GetMapping("/by-khoang-gia")
    public ResponseEntity<List<SanPham>> timTheoKhoangGia(
            @RequestParam @Min(0) double min,
            @RequestParam @Min(0) double max) {

        if (max < min) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(
                sanPhamService.timTheoKhoangGia(min, max)
        );
    }

    /** Kiểm tra tồn tại */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> tonTaiTheoId(@PathVariable int id) {
        return ResponseEntity.ok(sanPhamService.tonTaiTheoId(id));
    }

    // ========================== TỒN KHO ==========================

    @PatchMapping("/{id}/tong-so-luong")
    public ResponseEntity<Void> capNhatTongSoLuong(
            @PathVariable int id,
            @RequestBody @Valid CapNhatTongSoLuongRequest req) {

        boolean ok = sanPhamService.capNhatTongSoLuong(id, req.getTongSoLuongMoi());
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/tang-so-luong")
    public ResponseEntity<Void> tangSoLuong(
            @PathVariable int id,
            @RequestBody @Valid DieuChinhSoLuongRequest req) {

        boolean ok = sanPhamService.tangSoLuong(id, req.getSoLuong());
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/giam-so-luong")
    public ResponseEntity<Void> giamSoLuong(
            @PathVariable int id,
            @RequestBody @Valid DieuChinhSoLuongRequest req) {

        boolean ok = sanPhamService.giamSoLuong(id, req.getSoLuong());
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    // ========================== DTO ==========================

    public static class CapNhatTongSoLuongRequest {
        @NotNull
        @Min(0)
        private Integer tongSoLuongMoi;

        public Integer getTongSoLuongMoi() {
            return tongSoLuongMoi;
        }

        public void setTongSoLuongMoi(Integer tongSoLuongMoi) {
            this.tongSoLuongMoi = tongSoLuongMoi;
        }
    }

    public static class DieuChinhSoLuongRequest {
        @NotNull
        @Min(1)
        private Integer soLuong;

        public Integer getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(Integer soLuong) {
            this.soLuong = soLuong;
        }
    }
}