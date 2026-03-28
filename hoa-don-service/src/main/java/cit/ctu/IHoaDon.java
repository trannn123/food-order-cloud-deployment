package cit.ctu;

import java.util.List;

public interface IHoaDon {
    int taoHoaDon(HoaDon hd);
    boolean themChiTietHoaDon(ChiTietHoaDon ct);
    List<HoaDon> layDanhSachHoaDon();
    List<ChiTietHoaDon> layChiTietHoaDon(int hoaDonId);
    List<HoaDon> layHoaDonTheoNguoiDung(int nguoiDungId);
    boolean capNhatSoLuongMonAnTrongChiTietHoaDon(int idChiTietHoaDon, int idMonAn, int soLuongMoi);
    boolean capNhatTrangThaiHoaDon(int id, String trangThai);
    boolean xoaHoaDon(int hoaDonId);
    boolean xoaChiTietHoaDon(int chiTietId);
    double tinhTongTienHoaDon(int hoaDonId);
    int tinhTongSoLuongHoaDon(int hoaDonId);
}