package cit.ctu;

public interface INguoiDung {
    boolean dangKy(NguoiDung nd);
    boolean capNhatNguoiDung(NguoiDung nd);
    NguoiDung xemThongTin(String tenDangNhap);
    boolean doiMatKhau(String tenDangNhap, String matKhauCu, String matKhauMoi);
    boolean xoaNguoiDung(String tenDangNhap);
    NguoiDung timTheoId(int id);
}
