package com.example.demo.controller;

import com.example.demo.model.NhanVien;
import com.example.demo.service.NhanVienService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/nhan-vien")
@Validated
public class NhanVienController {

    private final NhanVienService nhanVienService;

    // ✅ Constructor injection – ĐÚNG CHUẨN SPRING BOOT
    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    // ========================== CRUD ==========================

    /** Tạo nhân viên; nếu thành công entity trả về sẽ có id. */
    @PostMapping
    public ResponseEntity<NhanVien> taoNhanVien(
            @Valid @RequestBody NhanVien request) {

        boolean ok = nhanVienService.themNhanVien(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.badRequest().build();
    }

    /** Tạo nhân viên và trả về ID tự tăng. */
    @PostMapping("/return-id")
    public ResponseEntity<Integer> taoNhanVienVaLayId(
            @Valid @RequestBody NhanVien request) {

        int newId = nhanVienService.themNhanVienVaLayId(request);
        if (newId > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newId);
        }
        return ResponseEntity.badRequest().body(-1);
    }

    /** Cập nhật nhân viên theo id path. */
    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatNhanVien(
            @PathVariable("id") int id,
            @Valid @RequestBody NhanVien request) {

        request.setId_nhan_vien(id);
        boolean ok = nhanVienService.capNhatNhanVien(request);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    /** Xóa nhân viên. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNhanVien(@PathVariable("id") int id) {
        boolean ok = nhanVienService.xoaNhanVien(id);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    // ========================== QUERY ==========================

    /** Lấy chi tiết nhân viên theo id. */
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> layTheoId(@PathVariable("id") int id) {
        NhanVien nv = nhanVienService.timTheoId(id);
        return nv != null ? ResponseEntity.ok(nv)
                          : ResponseEntity.notFound().build();
    }

    /** Lấy tất cả nhân viên. */
    @GetMapping
    public ResponseEntity<List<NhanVien>> layTatCa() {
        return ResponseEntity.ok(nhanVienService.layTatCaNhanVien());
    }

    // ========================== THEO THỜI GIAN ==========================

    /** Tìm theo ngày vào làm (yyyy-MM-dd). */
    @GetMapping("/by-date")
    public ResponseEntity<List<NhanVien>> timTheoNgayVaoLam(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(nhanVienService.timTheoNgayVaoLam(date));
    }

    /** Tìm theo tháng vào làm (yyyy-MM). */
    @GetMapping("/by-month")
    public ResponseEntity<List<NhanVien>> timTheoThangVaoLam(
            @RequestParam("month") @NotBlank String month) {

        return ResponseEntity.ok(
                nhanVienService.timTheoThangVaoLam(YearMonth.parse(month))
        );
    }

    /** Tìm theo năm vào làm. */
    @GetMapping("/by-year")
    public ResponseEntity<List<NhanVien>> timTheoNamVaoLam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(nhanVienService.timTheoNamVaoLam(year));
    }
}
