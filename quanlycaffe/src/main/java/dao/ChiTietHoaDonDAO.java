package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.ChiTietHoaDon;

public class ChiTietHoaDonDAO {
	private Connection con;
	private MatHangDAO matHangDao;
	private HoaDonDAO hoaDonDao;
	public ChiTietHoaDonDAO() {
		con = connectDb.ConnectDB.getInstance().getConnection();
		matHangDao = new MatHangDAO();
		hoaDonDao = new HoaDonDAO();
	}
	public ChiTietHoaDon timChiTietHoaDon(String maCTHD) throws Exception {
		String sql = "select * from ChiTietHoaDon where maCTHoaDon = ? ";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, maCTHD);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return new ChiTietHoaDon(rs.getString("maCTHoaDon"), matHangDao.timMatHang(rs.getString("maMatHang")), rs.getInt("soLuong"), hoaDonDao.timHoaDon(rs.getString("maHoaDon")));
			}
		}
		return null;
	}
	public boolean themChiTietHoaDon(ChiTietHoaDon cthd) throws Exception {
		if (timChiTietHoaDon(cthd.getMaCTHoaDon()) != null) 
			return false;
		String sql = "INSERT INTO [dbo].[ChiTietHoaDon]"
				+ "           ([maCTHoaDon] "
				+ "           ,[maMatHang] "
				+ "           ,[soLuong] "
				+ "           ,[maHoaDon]) "
				+ "     VALUES"
				+ "           (?,?,?,?)";
		int n = 0;
		try(PreparedStatement stmt = con.prepareStatement(sql)){	
			stmt.setString(1, cthd.getMaCTHoaDon());
			stmt.setString(2, cthd.getMaMatHang().getMaMatHang());
			stmt.setInt(3, cthd.getSoLuong());
			stmt.setString(4, cthd.getMaHoaDon().getMaHoaDon());
			n = stmt.executeUpdate();
		}
		return n>0;
	}
	public boolean xoaChiTietHoaDon(String maCTHD) throws Exception {
		if (timChiTietHoaDon(maCTHD)==null)
			return false;
		int n = 0;
		String sql = "DELETE FROM [dbo].[ChiTietHoaDon]"
				+ "      WHERE maCTHoaDon = ? ";
		try(PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1,maCTHD );
			n = stmt.executeUpdate();
		}
		return n >0;
	}
	public List<ChiTietHoaDon> getAllChiTietHoaDons() throws Exception {
		List<ChiTietHoaDon> dscthd = new ArrayList<>();
		String sql = "select * from ChiTietHoaDon";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			dscthd.add(new ChiTietHoaDon(rs.getString("maCTHoaDon"), matHangDao.timMatHang(rs.getString("maMatHang")), rs.getInt("soLuong"), hoaDonDao.timHoaDon(rs.getString("maHoaDon"))));
		}
		return dscthd;
	}
}
