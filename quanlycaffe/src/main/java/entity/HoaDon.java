package entity;

import java.util.Date;
import java.util.Objects;

public class HoaDon {
	private String maHoaDon;
	private Date ngayTao;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private double thanhTien;
	private double tienKhachTra;
	private Ban ban;
	@Override
	public int hashCode() {
		return Objects.hash(maHoaDon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHoaDon, other.maHoaDon);
	}
	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayTao=" + ngayTao + ", khachHang=" + khachHang + ", nhanVien="
				+ nhanVien + ", thanhTien=" + thanhTien + ", tienTra=" + tienKhachTra + ", ban=" + ban + "]";
	}
	public HoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HoaDon(String maHoaDon, Date ngayTao, KhachHang khachHang, NhanVien nhanVien, double thanhTien,
			double tienTra, Ban ban) {
		super();
		this.maHoaDon = maHoaDon;
		this.ngayTao = ngayTao;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.thanhTien = thanhTien;
		this.tienKhachTra = tienTra;
		this.ban = ban;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public Date getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(Date ngayTao) {
		this.ngayTao = ngayTao;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}
	public double getTienTra() {
		return tienKhachTra;
	}
	public void setTienTra(double tienTra) {
		this.tienKhachTra = tienTra;
	}
	public Ban getBan() {
		return ban;
	}
	public void setBan(Ban ban) {
		this.ban = ban;
	}
}
