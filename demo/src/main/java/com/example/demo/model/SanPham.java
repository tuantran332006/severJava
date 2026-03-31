package com.example.demo.model;

public class SanPham {
    private int idSp;
    private String tenSp;
    private double giaSp;
    private int tongSoLuongSpTrongKho;
    private int idLoai;
    private String donViTinh;
    private String moTa;


    public SanPham() {
    }


	public SanPham(int idSp, String tenSp, double giaSp, int tongSoLuongSpTrongKho, int idLoai, String donViTinh,
			String moTa) {
		super();
		this.idSp = idSp;
		this.tenSp = tenSp;
		this.giaSp = giaSp;
		this.tongSoLuongSpTrongKho = tongSoLuongSpTrongKho;
		this.idLoai = idLoai;
		this.donViTinh = donViTinh;
		this.moTa = moTa;
	}


	public int getIdSp() {
		return idSp;
	}


	public void setIdSp(int idSp) {
		this.idSp = idSp;
	}


	public String getTenSp() {
		return tenSp;
	}


	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}


	public double getGiaSp() {
		return giaSp;
	}


	public void setGiaSp(double giaSp) {
		this.giaSp = giaSp;
	}


	public int getTongSoLuongSpTrongKho() {
		return tongSoLuongSpTrongKho;
	}


	public void setTongSoLuongSpTrongKho(int tongSoLuongSpTrongKho) {
		this.tongSoLuongSpTrongKho = tongSoLuongSpTrongKho;
	}


	public int getIdLoai() {
		return idLoai;
	}


	public void setIdLoai(int idLoai) {
		this.idLoai = idLoai;
	}


	public String getDonViTinh() {
		return donViTinh;
	}


	public void setDonViTinh(String donViTinh) {
		this.donViTinh = donViTinh;
	}


	public String getMoTa() {
		return moTa;
	}


	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

}