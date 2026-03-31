package com.example.demo.controller;

import com.example.demo.model.LoaiSanPham;
import com.example.demo.service.LoaiSanPhamService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loai-san-pham")
@Validated
public class LoaiSanPhamController {

    private final LoaiSanPhamService loaiSanPhamService;


    public LoaiSanPhamController(LoaiSanPhamService loaiSanPhamService) {
           this.loaiSanPhamService = loaiSanPhamService;
       }


    // ========================== CRUD ==========================

    /** Tạo mới loại sản phẩm; nếu thành công entity trả về sẽ có id (nếu DAO set). */
    @PostMapping
    public ResponseEntity<LoaiSanPham> taoLoaiSanPham(@Valid @RequestBody LoaiSanPham request) {
        boolean ok = loaiSanPhamService.themLoaiSanPham(request);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /** Cập nhật loại sản phẩm theo id. */
    @PutMapping("/{id}")
    public ResponseEntity<Void> suaLoaiSanPham(@PathVariable("id") @Min(1) int id,
                                               @Valid @RequestBody LoaiSanPham request) {
        // đảm bảo update đúng bản ghi theo id path
        request.setId_loai(id);
        boolean ok = loaiSanPhamService.suaLoaiSanPham(request);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /** Xoá loại sản phẩm theo id. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaLoaiSanPham(@PathVariable("id") @Min(1) int id) {
        boolean ok = loaiSanPhamService.xoaLoaiSanPham(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ========================== TRUY VẤN ==========================

    /** Lấy chi tiết loại sản phẩm theo id. */
    @GetMapping("/{id}")
    public ResponseEntity<LoaiSanPham> timTheoId(@PathVariable("id") @Min(1) int id) {
        LoaiSanPham loai = loaiSanPhamService.timLoaiSanPhamTheoId(id);
        return loai != null ? ResponseEntity.ok(loai) : ResponseEntity.notFound().build();
    }

    /** Lấy tất cả loại sản phẩm. */
    @GetMapping
    public ResponseEntity<List<LoaiSanPham>> layTatCa() {
        return ResponseEntity.ok(loaiSanPhamService.layTatCaLoaiSanPham());
    }

    // ========================== BATCH (tuỳ chọn) ==========================
    // Batch insert đơn giản (không gom transaction ở đây vì service/DAO tự mở/đóng connection).
    // Nếu bạn muốn gom transaction, mình có thể refactor để truyền Connection tương tự các service khác của bạn.

    @PostMapping("/batch/insert")
    public ResponseEntity<BatchInsertResponse> batchInsert(@RequestBody @NotEmpty List<@Valid LoaiSanPham> list) {
        int okCount = 0;
        for (LoaiSanPham lsp : list) {
            if (loaiSanPhamService.themLoaiSanPham(lsp)) {
                okCount++;
            } else {
                // Không rollback vì không có transaction gom nhóm ở Service này
                // Có thể trả về danh sách lỗi chi tiết nếu cần
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