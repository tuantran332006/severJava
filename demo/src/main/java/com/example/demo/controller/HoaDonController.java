package com.example.demo.controller;

import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
import com.example.demo.model.LoSanPham;
import com.example.demo.service.HoaDonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hoa-don")
@Validated
public class HoaDonController {

    private final HoaDonService hoaDonService;

    public HoaDonController() {
        this.hoaDonService = new HoaDonService(null, null, null, null); // service tự quản lý transaction khi tạo hóa đơn
    }

    // ========================== TẠO HÓA ĐƠN (TRANSACTION) ==========================

    /**
     * Tạo hóa đơn + chi tiết + cập nhật tồn kho (sản phẩm & lô) trong 1 transaction.
     * - req.loGiamList: danh sách lô cần TRỪ số lượng (theo id lô và số lượng giảm).
     * - Do HoaDonService hiện đang dùng tạm so_luong_nhap như "số lượng giảm",
     *   controller sẽ map soLuongGiam -> lo.setSo_luong_nhap(soLuongGiam) trước khi gọi service.
     */
    @PostMapping
    public ResponseEntity<ApiResult> taoHoaDon(@Valid @RequestBody CreateHoaDonRequest req) {
        // Map loGiamList -> List<LoSanPham> theo "hack" của service hiện tại:
        List<LoSanPham> dsLoUpdate = null;
        if (req.getLoGiamList() != null) {
            dsLoUpdate = req.getLoGiamList().stream().map(loReq -> {
                LoSanPham lo = new LoSanPham();
                lo.setId_lo(loReq.getIdLo());
                // ⚠️ Mapping theo placeholder trong Service: dùng so_luong_nhap để chứa "số lượng cần trừ"
                lo.setSo_luong_nhap(loReq.getSoLuongGiam());
                return lo;
            }).collect(Collectors.toList());
        }

        boolean ok = hoaDonService.taoHoaDon(req.getHoaDon(), req.getChiTietList(), dsLoUpdate);
        if (ok) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResult.success("Tạo hóa đơn thành công"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResult.fail("Tạo hóa đơn thất bại. Vui lòng kiểm tra dữ liệu đầu vào."));
    }

    // ========================== CẬP NHẬT / XOÁ ==========================

    @PutMapping("/{id}")
    public ResponseEntity<Void> suaHoaDon(@PathVariable("id") int id,
                                          @Valid @RequestBody HoaDon request) {
        request.setId_hoa_don(id); // đảm bảo cập nhật đúng bản ghi
        boolean ok = hoaDonService.suaHoaDon(request);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaHoaDon(@PathVariable("id") int id) {
        boolean ok = hoaDonService.xoaHoaDon(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ========================== TRUY VẤN CƠ BẢN ==========================

    @GetMapping("/{id}")
    public ResponseEntity<HoaDon> timTheoId(@PathVariable("id") int id) {
        HoaDon hd = hoaDonService.timHoaDonTheoId(id);
        return hd != null ? ResponseEntity.ok(hd) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<HoaDon>> layTatCa() {
        return ResponseEntity.ok(hoaDonService.layTatCaHoaDon());
    }

    // ========================== TRUY VẤN THEO THỜI GIAN ==========================

    /** Lấy hoá đơn theo ngày (yyyy-MM-dd). */
    @GetMapping("/by-date")
    public ResponseEntity<List<HoaDon>> layTheoNgay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoNgay(date));
    }

    /** Lấy hoá đơn theo tháng (yyyy-MM). */
    @GetMapping("/by-month")
    public ResponseEntity<List<HoaDon>> layTheoThang(@RequestParam("month") String month) {
        YearMonth ym = YearMonth.parse(month);
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoThang(ym));
    }

    /** Lấy hoá đơn theo năm. */
    @GetMapping("/by-year")
    public ResponseEntity<List<HoaDon>> layTheoNam(
            @RequestParam("year") @Min(1970) @Max(3000) int year) {
        return ResponseEntity.ok(hoaDonService.layHoaDonTheoNam(year));
    }

    // ========================== THỐNG KÊ DOANH THU ==========================

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

    // ========================== THỐNG KÊ SỐ LƯỢNG BÁN ==========================

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

    // ========================== DTOs ==========================

    public static class CreateHoaDonRequest {
        @NotNull @Valid
        private HoaDon hoaDon;

        @NotEmpty @Valid
        private List<ChiTietHoaDon> chiTietList;

        // Danh sách lô cần trừ tồn (id lô + số lượng giảm)
        @Valid
        private List<LoGiamRequest> loGiamList;

        public HoaDon getHoaDon() { return hoaDon; }
        public void setHoaDon(HoaDon hoaDon) { this.hoaDon = hoaDon; }

        public List<ChiTietHoaDon> getChiTietList() { return chiTietList; }
        public void setChiTietList(List<ChiTietHoaDon> chiTietList) { this.chiTietList = chiTietList; }

        public List<LoGiamRequest> getLoGiamList() { return loGiamList; }
        public void setLoGiamList(List<LoGiamRequest> loGiamList) { this.loGiamList = loGiamList; }
    }

    public static class LoGiamRequest {
        @Min(1)
        private int idLo;

        @Min(1)
        private int soLuongGiam;

        public int getIdLo() { return idLo; }
        public void setIdLo(int idLo) { this.idLo = idLo; }
        public int getSoLuongGiam() { return soLuongGiam; }
        public void setSoLuongGiam(int soLuongGiam) { this.soLuongGiam = soLuongGiam; }
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

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}