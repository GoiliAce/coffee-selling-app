package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import connectDb.ConnectDB;
import entity.NhanVien;

public class NhanVienDAO {
	private Connection con;
	private TaiKhoanDAO taiKhoanDao;
	private DiaChiDAO diaChiDao;

	public NhanVienDAO() {
		con = ConnectDB.getInstance().getConnection();
		taiKhoanDao = new TaiKhoanDAO();
		diaChiDao = new DiaChiDAO();
	}

	public String getMaNhanVienCaoNhat() throws Exception {
		String sql = "SELECT MAX(maNhanVien) FROM NhanVien";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getString(1);
		}
		return null;
	}

	public boolean themNhanVien(NhanVien nv) throws Exception {
		if (timNhanVien(nv.getMaNhanVien()) != null) {
			System.out.println("sai");
			return false;
		}
		if (taiKhoanDao.timTaiKhoan(nv.getTaiKhoan().getTenTaiKhoan()) == null)
			taiKhoanDao.themTaiKhoan(nv.getTaiKhoan());
		String sql = "INSERT INTO [dbo].[NhanVien] " + "           ([maNhanVien] " + "           ,[tenNhanVien] "
				+ "           ,[ngaySinh] " + "           ,[gioiTinh] " + "           ,[ngayVaoLam] "
				+ "           ,[soDienThoai] " + "           ,[chucVu] " + "           ,[maDC] "
				+ "           ,[tenTaiKhoan] " + "           ,[linkAnh]) " + "     VALUES (?,?,?,?,?,?,?,?,?,?) ";
		int n = 0;
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, nv.getMaNhanVien());
			stmt.setString(2, nv.getTenNhanVien());
			Date date = null;
			try {
				date = new Date(nv.getNgaySinh().getTime());
			}catch (Exception e) {
			}
			stmt.setDate(3,date);
			stmt.setBoolean(4, nv.isGioiTinh());
			date = null;
			try {
				date = new Date(nv.getNgayVaoLam().getTime());
			}catch (Exception e) {
			}
			stmt.setDate(5,date);
			stmt.setString(6, nv.getSoDienThoai());
			stmt.setString(7, nv.getChucVu());
			stmt.setString(8, nv.getDiaChi().getMaDC());
			stmt.setString(9, nv.getTaiKhoan().getTenTaiKhoan());
			stmt.setString(10, nv.getLinkAnhNV());
			n = stmt.executeUpdate();
		}
		return n > 0;
	}

	public NhanVien timNhanVien(String maNV) throws Exception {
		String sql = "select * from NhanVien where maNhanVien = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, maNV);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
						rs.getBoolean("gioiTinh"), rs.getDate("ngaySinh"), rs.getDate("ngayVaoLam"),
						rs.getString("soDienThoai"), rs.getString("chucVu"),
						diaChiDao.getDiaChiTheoMa(rs.getString("maDC")),
						taiKhoanDao.timTaiKhoan(rs.getString("tenTaiKhoan")), rs.getString("linkAnh"));
				return nv;
			}
		}
		return null;
	}

	public NhanVien timNhanVienTheoTaiKhoan(String maTK) throws Exception {
		String sql = "select * from NhanVien where tenTaiKhoan = ?";

		try (PreparedStatement stmt = con.prepareStatement(sql);) {
			stmt.setString(1, maTK);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
						rs.getBoolean("gioiTinh"), rs.getDate("ngaySinh"),
						rs.getDate("ngayVaoLam"), rs.getString("soDienThoai"), rs.getString("chucVu"),
						diaChiDao.getDiaChiTheoMa(rs.getString("maDC")),
						taiKhoanDao.timTaiKhoan(rs.getString("tenTaiKhoan")), rs.getString("linkAnh"));
				return nv;
			}
		}
		return null;
	}

	public List<NhanVien> layDanhSachNV() throws Exception {
		List<NhanVien> dsNV = new ArrayList<>();
		String sql = "select * from NhanVien";

		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
						rs.getBoolean("gioiTinh"), rs.getDate("ngaySinh"), rs.getDate("ngayVaoLam"),
						rs.getString("soDienThoai"), rs.getString("chucVu"),
						diaChiDao.getDiaChiTheoMa(rs.getString("maDC")),
						taiKhoanDao.timTaiKhoan(rs.getString("tenTaiKhoan")), rs.getString("linkAnh"));
				dsNV.add(nv);
			}
		}
		return dsNV;
	}

	public boolean capNhatNhanVien(NhanVien nv) throws Exception {
		if (timNhanVien(nv.getMaNhanVien()) != null) {

			String sql = "UPDATE [dbo].[NhanVien] " + "   SET " + "      [tenNhanVien] = ? " + "      ,[ngaySinh] = ? "
					+ "      ,[gioiTinh] = ? " + "      ,[ngayVaoLam] = ? " + "      ,[soDienThoai] = ? "
					+ "      ,[chucVu] = ? " + "      ,[maDC] = ? " + "      ,[tenTaiKhoan] = ?"
					+ "      ,[linkAnh] = ? " + " WHERE[maNhanVien] = ?  ";
			int n = 0;
			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				stmt.setString(1, nv.getTenNhanVien());
				Date date = null;
				try {
					date = new Date(nv.getNgaySinh().getTime());
				}catch (Exception e) {
				}
				stmt.setDate(2,date);
				stmt.setBoolean(3, nv.isGioiTinh());
				date = null;
				try {
					date = new Date(nv.getNgayVaoLam().getTime());
				}catch (Exception e) {
				}
				stmt.setDate(4,date);
				stmt.setString(5, nv.getSoDienThoai());
				stmt.setString(6, nv.getChucVu());
				stmt.setString(7, nv.getDiaChi().getMaDC());
				stmt.setString(8, nv.getTaiKhoan().getTenTaiKhoan());
				stmt.setString(9, nv.getLinkAnhNV());
				stmt.setString(10, nv.getMaNhanVien());

				n = stmt.executeUpdate();
			}
			return n > 0;
		}
		return false;
	}

	public boolean deleteNhanVien(String maNV) throws Exception {
		return taiKhoanDao.deleteTaiKhoan(timNhanVien(maNV).getTaiKhoan().getTenTaiKhoan());
//		String sql = "DELETE FROM [dbo].[NhanVien] "
//				+ "      WHERE maNhanVien = ? ";
//		int n = 0;
//		try(PreparedStatement stmt = con.prepareStatement(sql)){
//			stmt.setString(1, maNV);
//			n = stmt.executeUpdate();
//		}
//		return n>0;
	}

	public void timKiemNhanVienTheoNhieuThuocTinh(NhanVien nv) {

	}

	public List<NhanVien> timKiemNhanVienTheoNhieuThuocTinh(String maNV, String tenNV, String gioiTinh,
			java.util.Date ngaySinh, java.util.Date ngayVaoLam, String soDienThoai, String chucVu) throws Exception {
		String sql = "select * from NhanVien ";
		String where = "where ";
		if (maNV != null || tenNV != null || gioiTinh != null || ngaySinh != null || ngayVaoLam != null
				|| soDienThoai != null || chucVu != null) {
			List<NhanVien> dsNV = new ArrayList<>();
			if (maNV != null)
				where += "maNhanVien = '" + maNV + "'";
			else {
				if (tenNV != null)
					where += "and tenNhanVien like N'%"+tenNV.trim()+"%' ";
				if (gioiTinh != null)
					where += "and gioiTinh = '"+(gioiTinh == "Nam"? 1:0)+"' ";
				if (ngaySinh != null)
					where += "and ngaySinh = '"+new Date(ngaySinh.getTime())+"' ";
				if (ngayVaoLam != null)
					where += "and ngayVaoLam = '"+new Date(ngayVaoLam.getTime())+"' ";
				if (soDienThoai != null)
					where += "and soDienThoai like '%"+soDienThoai+"%' ";
				if (chucVu != null)
					where += "and chucVu = '"+chucVu+"' ";
				where = where.replaceFirst("and", "");
			}
			sql += where;
			try (PreparedStatement stmt = con.prepareStatement(sql)) {
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("tenNhanVien"),
							rs.getBoolean("gioiTinh"), rs.getDate("ngaySinh"), rs.getDate("ngayVaoLam"),
							rs.getString("soDienThoai"), rs.getString("chucVu"),
							diaChiDao.getDiaChiTheoMa(rs.getString("maDC")),
							taiKhoanDao.timTaiKhoan(rs.getString("tenTaiKhoan")), rs.getString("linkAnh"));
					dsNV.add(nv);
				}
			}
			return dsNV;
		} else
			return layDanhSachNV();
	}
}
