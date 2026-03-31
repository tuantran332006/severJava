package com.example.demo.controller;

import com.example.demo.model.KhachHang;
import com.example.demo.service.KhachHangService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
@Validated
public class KhachHangController {

    private final KhachHangService khachHangService;


public KhachHangController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }


    // ========================== CRUD ==========================

    /** Tạo mới khách hàng; nếu thành công entity trả về sẽ có id (nếu DAO set). */
    @PostMapping
    public ResponseEntity<KhachHang> taoKhachHang(@Valid @RequestBody KhachHang request) {
        boolean ok = khachHangService.themKhachHang(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /** Cập nhật khách hàng theo id (path). */
    @PutMapping("/{id}")
    public ResponseEntity<Void> suaKhachHang(@PathVariable("id") @Min(1) int id,
                                             @Valid @RequestBody KhachHang request) {
        // đảm bảo update đúng bản ghi theo id
        request.setId_kh(id);
        boolean ok = khachHangService.suaKhachHang(request);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /** Xoá khách hàng theo id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaKhachHang(@PathVariable("id") @Min(1) int id) {
        boolean ok = khachHangService.xoaKhachHang(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ========================== TRUY VẤN ==========================

    /** Lấy chi tiết khách hàng theo id. */
    @GetMapping("/{id}")
    public ResponseEntity<KhachHang> timTheoId(@PathVariable("id") @Min(1) int id) {
        KhachHang kh = khachHangService.timKhachHangTheoId(id);
        return kh != null ? ResponseEntity.ok(kh) : ResponseEntity.notFound().build();
    }

    /** Lấy tất cả khách hàng. */
    @GetMapping
    public ResponseEntity<List<KhachHang>> layTatCa() {
        return ResponseEntity.ok(khachHangService.layTatCaKhachHang());
    }

    // ========================== BATCH (tuỳ chọn) ==========================
    // Batch insert đơn giản (không gom transaction vì service/DAO tự mở/đóng connection).
    // Nếu cần all-or-nothing, mình có thể refactor để dùng Connection như các service khác của bạn.

    @PostMapping("/batch/insert")
    public ResponseEntity<BatchInsertResponse> batchInsert(@RequestBody @NotEmpty List<@Valid KhachHang> list) {
        int okCount = 0;
        for (KhachHang kh : list) {
            if (khachHangService.themKhachHang(kh)) {
                okCount++;
            }
        }
        boolean success = okCount == list.size();
        String msg = success ? "Thêm tất cả thành công" : ("Thêm một phần (" + okCount + "/" + list.size() + ")");
        HttpStatus status = success ? HttpStatus.OK : HttpStatus.MULTI_STATUS;
        return ResponseEntity.status(status).body(new BatchInsertResponse(success, okCount, msg));
    }

    // ========================== DTO PHẢN HỒI ==========================

    public static class BatchInsertResponse {
        private boolean success;
        private int soBanGhiThanhCong;
        private String message;

        public BatchInsertResponse() {}
        public BatchInsertResponse(boolean success, int soBanGhiThanhCong, String message) {
            this.success = success;
            this.soBanGhiThanhCong = soBanGhiThanhCong;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public int getSoBanGhiThanhCong() { return soBanGhiThanhCong; }
        public void setSoBanGhiThanhCong(int soBanGhiThanhCong) { this.soBanGhiThanhCong = soBanGhiThanhCong; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}