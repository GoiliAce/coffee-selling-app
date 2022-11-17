package entity;

import java.util.Objects;

public class ChiTietHoaDon {
	private String maCTHoaDon;
	private MatHang maMatHang;
	private int soLuong;
	private HoaDon maHoaDon;
	@Override
	public int hashCode() {
		return Objects.hash(maCTHoaDon);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChiTietHoaDon other = (ChiTietHoaDon) obj;
		return Objects.equals(maCTHoaDon, other.maCTHoaDon);
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [maCTHoaDon=" + maCTHoaDon + ", maMatHang=" + maMatHang + ", soLuong=" + soLuong
				+ ", maHoaDon=" + maHoaDon + "]";
	}
	public ChiTietHoaDon(String maCTHoaDon, MatHang maMatHang, int soLuong, HoaDon maHoaDon) {
		super();
		this.maCTHoaDon = maCTHoaDon;
		this.maMatHang = maMatHang;
		this.soLuong = soLuong;
		this.maHoaDon = maHoaDon;
	}
	public ChiTietHoaDon() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMaCTHoaDon() {
		return maCTHoaDon;
	}
	public void setMaCTHoaDon(String maCTHoaDon) {
		this.maCTHoaDon = maCTHoaDon;
	}
	public MatHang getMaMatHang() {
		return maMatHang;
	}
	public void setMaMatHang(MatHang maMatHang) {
		this.maMatHang = maMatHang;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public HoaDon getMaHoaDon() {
		return maHoaDon;
	}
	public void setMaHoaDon(HoaDon maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
}
