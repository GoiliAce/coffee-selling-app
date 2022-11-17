package entity;

import java.util.Objects;

public class LoaiHang {
	private String maLoaiHang;
	private String tenLoaiHang;
	public String getMaLoaiHang() {
		return maLoaiHang;
	}
	public void setMaLoaiHang(String maLoaiHang) {
		this.maLoaiHang = maLoaiHang;
	}
	public String getTenLoaiHang() {
		return tenLoaiHang;
	}
	public void setTenLoaiHang(String tenLoaiHang) {
		this.tenLoaiHang = tenLoaiHang;
	}
	public LoaiHang() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoaiHang(String maLoaiHang, String tenLoaiHang) {
		super();
		this.maLoaiHang = maLoaiHang;
		this.tenLoaiHang = tenLoaiHang;
	}
	@Override
	public String toString() {
		return "LoaiHang [maLoaiHang=" + maLoaiHang + ", tenLoaiHang=" + tenLoaiHang + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maLoaiHang);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoaiHang other = (LoaiHang) obj;
		return Objects.equals(maLoaiHang, other.maLoaiHang);
	}
	
}