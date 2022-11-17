package setting;

public interface PathSetting {
	final String PATH_TO_PACKAGE = System.getProperty("user.dir");
	String PATH_IMAGE_SYSTEM = PATH_TO_PACKAGE + "\\Image\\System";
	final String PATH_IMAGE_NHANVIEN = PATH_TO_PACKAGE+ "\\Image\\NhanVien\\";
	final String PATH_IMAGE_SANPHAM = PATH_TO_PACKAGE+ "\\Image\\SanPham\\";
}
