package entity;

import java.util.Date;
import java.util.Objects;

public class KhachHang {
	private String maKhachHang;
	private String tenKhachHang;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String soDienThoai;
	private	int diemTichLuy;
	public String getIdKhachHang() {
		return maKhachHang;
	}
	public void setIdKhachHang(String idKhachHang) {
		this.maKhachHang = idKhachHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public boolean isGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public Date getNgaySinh() {
		return ngaySinh;
	}
	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public int getDiemTichLuy() {
		return diemTichLuy;
	}
	public void setDiemTichLuy(int diemTichLuy) {
		this.diemTichLuy = diemTichLuy;
	}
	public String getHang() {
		if (diemTichLuy<50)
			return "E";
		else if (diemTichLuy<100)
			return "D";
		else if (diemTichLuy<200)
			return "C";
		else if (diemTichLuy <500)
			return "B";
		else if (diemTichLuy <1000)
			return "A";
		else if (diemTichLuy>=1000)
			return "S";
		else return "Error";
	}

	public KhachHang(String idKhachHang, String tenKhachHang, boolean gioiTinh, Date ngaySinh, String soDienThoai,
			int diemTichLuy) {
		super();
		this.maKhachHang = idKhachHang;
		this.tenKhachHang = tenKhachHang;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.diemTichLuy = diemTichLuy;
	}
	public KhachHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public int hashCode() {
		return Objects.hash(maKhachHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKhachHang, other.maKhachHang);
	}
	@Override
	public String toString() {
		return "KhachHang [maKhachHang=" + maKhachHang + ", tenKhachHang=" + tenKhachHang + ", gioiTinh=" + gioiTinh
				+ ", ngaySinh=" + ngaySinh + ", soDienThoai=" + soDienThoai + ", diemTichLuy=" + diemTichLuy
				+ ", getHang()=" + getHang() + "]";
	}	
}
