package com.example.demo.controller;

import com.example.demo.model.SanPham;
import com.example.demo.model.SanPhamRequest;
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

    public SanPhamController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }

    @PostMapping
    public ResponseEntity<?> taoSanPham(@RequestBody SanPhamRequest request) {
        SanPham sp = new SanPham();
        sp.setTenSp(request.getTenSp());
        sp.setGiaSp(request.getGiaSp());
        sp.setTongSoLuongSpTrongKho(request.getTongSoLuongSpTrongKho());
        sp.setIdLoai(request.getIdLoai());
        sp.setDonViTinh(request.getDonViTinh());
        sp.setMoTa(request.getMoTa());

        boolean ok = sanPhamService.themSanPham(sp);

        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(sp);
        }
        return ResponseEntity.badRequest().body("Thêm sản phẩm thất bại");
    }

    @PostMapping("/return-id")
    public ResponseEntity<Integer> taoSanPhamVaLayId(@RequestBody SanPhamRequest request) {
        SanPham sp = new SanPham();
        sp.setTenSp(request.getTenSp());
        sp.setGiaSp(request.getGiaSp());
        sp.setTongSoLuongSpTrongKho(request.getTongSoLuongSpTrongKho());
        sp.setIdLoai(request.getIdLoai());
        sp.setDonViTinh(request.getDonViTinh());
        sp.setMoTa(request.getMoTa());

        int id = sanPhamService.themSanPhamVaLayId(sp);
        if (id > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        }
        return ResponseEntity.badRequest().body(-1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatSanPham(
            @PathVariable int id,
            @RequestBody SanPhamRequest request) {

        SanPham sp = new SanPham();
        sp.setIdSp(id);
        sp.setTenSp(request.getTenSp());
        sp.setGiaSp(request.getGiaSp());
        sp.setTongSoLuongSpTrongKho(request.getTongSoLuongSpTrongKho());
        sp.setIdLoai(request.getIdLoai());
        sp.setDonViTinh(request.getDonViTinh());
        sp.setMoTa(request.getMoTa());

        boolean ok = sanPhamService.capNhatSanPham(sp);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaSanPham(@PathVariable int id) {
        boolean ok = sanPhamService.xoaSanPham(id);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SanPham> layTheoId(@PathVariable int id) {
        SanPham sp = sanPhamService.timTheoId(id);
        return sp != null ? ResponseEntity.ok(sp)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<SanPham>> layTatCa() {
        return ResponseEntity.ok(sanPhamService.layTatCa());
    }

    @GetMapping("/by-loai")
    public ResponseEntity<List<SanPham>> timTheoLoai(@RequestParam @Min(1) int idLoai) {
        return ResponseEntity.ok(sanPhamService.timTheoLoai(idLoai));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SanPham>> timTheoTen(@RequestParam("q") @NotBlank String ten) {
        return ResponseEntity.ok(sanPhamService.timTheoTen(ten));
    }

    @GetMapping("/by-khoang-gia")
    public ResponseEntity<List<SanPham>> timTheoKhoangGia(
            @RequestParam @Min(0) double min,
            @RequestParam @Min(0) double max) {

        if (max < min) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sanPhamService.timTheoKhoangGia(min, max));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> tonTaiTheoId(@PathVariable int id) {
        return ResponseEntity.ok(sanPhamService.tonTaiTheoId(id));
    }

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
        return ok ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

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