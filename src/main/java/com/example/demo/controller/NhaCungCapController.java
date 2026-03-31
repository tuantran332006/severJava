package com.example.demo.controller;

import com.example.demo.model.NhaCungCap;
import com.example.demo.service.NhaCungCapService;
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
@RequestMapping("/api/nha-cung-cap")
@Validated
public class NhaCungCapController {

    private final NhaCungCapService nhaCungCapService;

    // ✅ Constructor injection – ĐÚNG CHUẨN SPRING BOOT
    public NhaCungCapController(NhaCungCapService nhaCungCapService) {
        this.nhaCungCapService = nhaCungCapService;
    }

    // ========================== CRUD ==========================

    @PostMapping
    public ResponseEntity<NhaCungCap> taoNhaCungCap(
            @Valid @RequestBody NhaCungCap request) {

        boolean ok = nhaCungCapService.themNhaCungCap(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> capNhatNhaCungCap(
            @PathVariable("id") int id,
            @Valid @RequestBody NhaCungCap request) {

        request.setId_ncc(id);
        boolean ok = nhaCungCapService.capNhatNhaCungCap(request);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaNhaCungCap(@PathVariable("id") int id) {
        boolean ok = nhaCungCapService.xoaNhaCungCap(id);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }

    // ========================== QUERY ==========================

    @GetMapping("/{id}")
    public ResponseEntity<NhaCungCap> layTheoId(@PathVariable("id") int id) {
        NhaCungCap ncc = nhaCungCapService.timTheoId(id);
        return ncc != null ? ResponseEntity.ok(ncc)
                           : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<NhaCungCap>> layTatCa() {
        return ResponseEntity.ok(nhaCungCapService.layTatCa());
    }

    // ========================== THEO THỜI GIAN ==========================

    @GetMapping("/by-date")
    public ResponseEntity<List<NhaCungCap>> timTheoNgayHopTac(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(nhaCungCapService.timTheoNgayHopTac(date));
    }

    @GetMapping("/by-month")
    public ResponseEntity<List<NhaCungCap>> timTheoThangHopTac(
            @RequestParam("month") @NotBlank String month) {

        return ResponseEntity.ok(
                nhaCungCapService.timTheoThangHopTac(YearMonth.parse(month))
        );
    }

    @GetMapping("/by-year")
    public ResponseEntity<List<NhaCungCap>> timTheoNamHopTac(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {

        return ResponseEntity.ok(nhaCungCapService.timTheoNamHopTac(year));
    }
}