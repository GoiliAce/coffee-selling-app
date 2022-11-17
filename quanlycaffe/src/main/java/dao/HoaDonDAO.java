package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.HoaDon;

public class HoaDonDAO {
	private Connection con;
	private KhachHangDAO khachHangDao;
	private NhanVienDAO nhanVienDao;
	private BanDAO banDao;
	public HoaDonDAO() {
		con = connectDb.ConnectDB.getInstance().getConnection();
		khachHangDao = new KhachHangDAO();
		nhanVienDao = new NhanVienDAO();
		banDao = new BanDAO();
	}
	
	public HoaDon timHoaDon(String maHD) throws Exception {
		String sql = "select * from HoaDon where maHoaDon = ? ";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maHD);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao"),khachHangDao.timKhachHang(rs.getString("maNhanVien")),nhanVienDao.timNhanVien(rs.getString("maKhachHang")), rs.getDouble("thanhTien"), rs.getDouble("tienKhachTra"), banDao.timBan(Integer.parseInt(rs.getString("maBan"))));
		}
		return null;
	}
	public boolean themHoaDon(HoaDon hd) throws Exception {
		if (timHoaDon(hd.getMaHoaDon()) != null)
			return false;
		String sql = ""
				+ "INSERT INTO [dbo].[HoaDon]"
				+ "           ([maHoaDon]"
				+ "           ,[ngayTao]"
				+ "           ,[maKhachHang]"
				+ "           ,[maNhanVien]"
				+ "           ,[thanhTien]"
				+ "           ,[tienKhachTra]"
				+ "           ,[maBan])"
				+ "     VALUES"
				+ "           (?,?,?,?,?,?,?)";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, hd.getMaHoaDon());
			stmt.setDate(2, new Date(hd.getNgayTao().getTime()));
			stmt.setString(3, hd.getKhachHang().getIdKhachHang());
			stmt.setString(4, hd.getNhanVien().getMaNhanVien());
			stmt.setDouble(5, hd.getThanhTien());
			stmt.setDouble(6, hd.getTienTra());
			stmt.setInt(7, hd.getBan().getMaBan());
			n = stmt.executeUpdate();
		}
		return n>0;
	}
	public boolean xoaHoaDon(String maHD) throws Exception {
		if (timHoaDon(maHD) == null)
			return false;
		String sql = "DELETE FROM [dbo].[HoaDon] "
				+ "      WHERE maHoaDon = ?";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maHD);
			n = stmt.executeUpdate();
		}
		return n>0;		
	}
	public boolean updateHoaDon(HoaDon hd) throws Exception {
		if (timHoaDon(hd.getMaHoaDon())==null)
			return false;
		String sql = "UPDATE [dbo].[HoaDon]"
				+ "   SET [ngayTao] = ? "
				+ "      ,[maKhachHang] = ? "
				+ "      ,[maNhanVien] = ? "
				+ "      ,[thanhTien] = ? "
				+ "      ,[tienKhachTra] = ? "
				+ "      ,[maBan] = ? "
				+ " WHERE maHoaDon = ? ";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(7, hd.getMaHoaDon());
			stmt.setDate(1, new Date(hd.getNgayTao().getTime()));
			stmt.setString(2, hd.getKhachHang().getIdKhachHang());
			stmt.setString(3, hd.getNhanVien().getMaNhanVien());
			stmt.setDouble(4, hd.getThanhTien());
			stmt.setDouble(5, hd.getTienTra());
			stmt.setInt(6, hd.getBan().getMaBan());
			n = stmt.executeUpdate();
		}
		return n>0;
	}
	public List<HoaDon> getAllHoaDon() throws Exception {
		List<HoaDon> dshd = new ArrayList<>();
		String sql = "select * from HoaDon";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next())
				dshd.add( new HoaDon(rs.getString("maHoaDon"), rs.getDate("ngayTao"),khachHangDao.timKhachHang(rs.getString("maNhanVien")),nhanVienDao.timNhanVien(rs.getString("maKhachHang")), rs.getDouble("thanhTien"), rs.getDouble("tienKhachTra"), banDao.timBan(Integer.parseInt(rs.getString("maBan")))));
		}
		return dshd;
		
	}
}
