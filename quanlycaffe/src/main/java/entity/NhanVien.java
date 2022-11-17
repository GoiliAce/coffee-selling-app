package entity;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class NhanVien {
	private String maNhanVien;
	private String tenNhanVien;
	private boolean gioiTinh;
	private Date ngaySinh;
	private Date ngayVaoLam;
	private String soDienThoai;
	private String chucVu;
	private DiaChi diaChi;
	private TaiKhoan taiKhoan;
	private String linkAnhNV;
	public String getMaNhanVien() {
		return maNhanVien;
	}
	public void setMaNhanVien(String maNhanVien) {
		this.maNhanVien = maNhanVien;
	}
	public String getTenNhanVien() {
		return tenNhanVien;
	}
	public void setTenNhanVien(String tenNhanVien) {
		this.tenNhanVien = tenNhanVien;
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
	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public DiaChi getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(DiaChi diaChi) {
		this.diaChi = diaChi;
	}
	public TaiKhoan getTaiKhoan() {
		return taiKhoan;
	}
	public void setTaiKhoan(TaiKhoan taiKhoan) {
		this.taiKhoan = taiKhoan;
	}
	public String getLinkAnhNV() {
		return linkAnhNV;
	}
	public void setLinkAnhNV(String linkAnhNV) {
		this.linkAnhNV = linkAnhNV;
	}
	public double getLuongCoBan() {
		return chucVu.equalsIgnoreCase("Nhân viên") ? 7000000.0 : 9000000.0;
	}
	public double getHeSoLuong() {
		long dateBeforeInMs = ngayVaoLam.getTime();
		long dateAfterInMs = new Date().getTime();

		long timeDiff = Math.abs(dateAfterInMs - dateBeforeInMs);

		long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
		return chucVu.equalsIgnoreCase("Nhân Viên")? 1.0 + (((daysDiff/60)*10)/100.0): 1.25*(((daysDiff/60)*20)/100.0);

	}
	public double getLuong() {
		return getHeSoLuong() *getLuongCoBan();
	}
	@Override
	public int hashCode() {
		return Objects.hash(maNhanVien);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNhanVien, other.maNhanVien);
	}
	public NhanVien(String maNhanVien, String tenNhanVien, boolean gioiTinh, Date ngaySinh, Date ngayVaoLam,
			String soDienThoai, String chucVu, DiaChi diaChi, TaiKhoan taiKhoan, String linkAnhNV) {
		super();
		this.maNhanVien = maNhanVien;
		this.tenNhanVien = tenNhanVien;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.ngayVaoLam = ngayVaoLam;
		this.soDienThoai = soDienThoai;
		this.chucVu = chucVu;
		this.diaChi = diaChi;
		this.taiKhoan = taiKhoan;
		this.linkAnhNV = linkAnhNV;
	}
	public NhanVien() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NhanVien [maNhanVien=" + maNhanVien + ", tenNhanVien=" + tenNhanVien + ", gioiTinh=" + gioiTinh
				+ ", ngaySinh=" + ngaySinh + ", ngayVaoLam=" + ngayVaoLam + ", soDienThoai=" + soDienThoai + ", chucVu="
				+ chucVu + ", diaChi=" + diaChi + ", taiKhoan=" + taiKhoan + ", linkAnhNV=" + linkAnhNV
				+ ", getLuongCoBan()=" + getLuongCoBan() + ", getHeSoLuong()=" + getHeSoLuong() + ", getLuong()="
				+ getLuong() + "]";
	}
}
