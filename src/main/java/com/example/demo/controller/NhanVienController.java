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

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    @PostMapping
    public ResponseEntity<NhanVien> taoNhanVien(@Valid @RequestBody NhanVien request) {
        if (request.getHo_ten() == null || request.getHo_ten().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        boolean ok = nhanVienService.themNhanVien(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/return-id")
    public ResponseEntity<Integer> taoNhanVienVaLayId(@Valid @RequestBody NhanVien request) {
        if (request.getHo_ten() == null || request.getHo_ten().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(-1);
        }

        int newId = nhanVienService.themNhanVienVaLayId(request);
        if (newId > 0) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newId);
        }
        return ResponseEntity.badRequest().body(-1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatNhanVien(
            @PathVariable("id") int id,
            @Valid @RequestBody NhanVien request) {

        if (request.getHo_ten() == null || request.getHo_ten().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        request.setId_nhan_vien(id);
        boolean ok = nhanVienService.capNhatNhanVien(request);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNhanVien(@PathVariable("id") int id) {
        boolean ok = nhanVienService.xoaNhanVien(id);
        return ok ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> layTheoId(@PathVariable("id") int id) {
        NhanVien nv = nhanVienService.timTheoId(id);
        return nv != null ? ResponseEntity.ok(nv)
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<NhanVien>> layTatCa() {
        List<NhanVien> list = nhanVienService.layTatCaNhanVien();

        list.removeIf(nv ->
                nv == null ||
                        nv.getHo_ten() == null ||
                        nv.getHo_ten().trim().isEmpty()
        );

        return ResponseEntity.ok(list);
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<NhanVien>> timTheoNgayVaoLam(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(nhanVienService.timTheoNgayVaoLam(date));
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<NhanVien>> timTheoThangVaoLam(
            @RequestParam("month") @NotBlank String month) {

        return ResponseEntity.ok(
                nhanVienService.timTheoThangVaoLam(YearMonth.parse(month))
        );
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<NhanVien>> timTheoNamVaoLam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(nhanVienService.timTheoNamVaoLam(year));
    }
}