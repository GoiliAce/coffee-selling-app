package entity;

import java.util.Objects;

public class MatHang {
	private String maMatHang;
	private String tenMatHang;
	private double donGia;
	private int soLuongTon;
	private boolean trangThai;
	private LoaiHang LoaiHang;
	private String linkAnh;
	public String getMaMatHang() {
		return maMatHang;
	}
	public void setMaMatHang(String maMatHang) {
		this.maMatHang = maMatHang;
	}
	public String getTenMatHang() {
		return tenMatHang;
	}
	public void setTenMatHang(String tenMatHang) {
		this.tenMatHang = tenMatHang;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}
	public int getSoLuongTon() {
		return soLuongTon;
	}
	public void setSoLuongTon(int soLuongTon) {
		this.soLuongTon = soLuongTon;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public LoaiHang getLoaiHang() {
		return LoaiHang;
	}
	public void setLoaiHang(LoaiHang loaiHang) {
		LoaiHang = loaiHang;
	}
	public String getLinkAnh() {
		return linkAnh;
	}
	public void setLinkAnh(String linkAnh) {
		this.linkAnh = linkAnh;
	}
	public MatHang(String maMatHang, String tenMatHang, double donGia, int soLuongTon, boolean trangThai,
			entity.LoaiHang loaiHang, String linkAnh) {
		super();
		this.maMatHang = maMatHang;
		this.tenMatHang = tenMatHang;
		this.donGia = donGia;
		this.soLuongTon = soLuongTon;
		this.trangThai = trangThai;
		LoaiHang = loaiHang;
		this.linkAnh = linkAnh;
	}
	public MatHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MatHang [maMatHang=" + maMatHang + ", tenMatHang=" + tenMatHang + ", donGia=" + donGia + ", soLuongTon="
				+ soLuongTon + ", trangThai=" + trangThai + ", LoaiHang=" + LoaiHang + ", linkAnh=" + linkAnh + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maMatHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatHang other = (MatHang) obj;
		return Objects.equals(maMatHang, other.maMatHang);
	}
	
}
