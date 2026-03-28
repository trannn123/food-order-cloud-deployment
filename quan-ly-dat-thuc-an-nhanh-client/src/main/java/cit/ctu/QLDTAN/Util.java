package cit.ctu.QLDTAN;

public class Util {
	public static double getTongTienCuaHoaDon(HoaDon hoaDon) {
        double tong = 0;
        for (ChiTietHoaDon c : hoaDon.getDanhSachChiTietHoaDon()) {
        	if(c != null)
        		tong += c.getDonGiaTaiThoiDiemTaoHoaDon() * c.getSoLuong();
        }
        return tong;
    }
    
    public static int getTongSoLuongCuaHoaDon(HoaDon hoaDon) {
        int tong = 0;
        for (ChiTietHoaDon c : hoaDon.getDanhSachChiTietHoaDon()) {
        	if( c!=null )
             tong += c.getSoLuong();
        }
        return tong;
    }
}