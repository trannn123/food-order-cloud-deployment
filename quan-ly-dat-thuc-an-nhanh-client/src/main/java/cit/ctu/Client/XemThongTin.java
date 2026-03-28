package cit.ctu.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.NguoiDung;
import cit.ctu.QLDTAN.ItemGioHang;

import static cit.ctu.Constants.BASE_API_URL_NGUOI_DUNG;

@WebServlet("/XemThongTin")
public class XemThongTin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();

    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung user = (NguoiDung) session.getAttribute("user");
        String tenDangNhap = user.getTenDangNhap();

        // ===== GIỎ HÀNG =====
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        NguoiDung nd = null;

        try {
            Response res = target.path("rest")
                    .path("nguoidung")
                    .path(tenDangNhap)
                    .request(MediaType.APPLICATION_JSON)
                    .get();

            if (res.getStatus() != 200) {
                res.close();
                out.println("<h3>Không lấy được thông tin người dùng</h3>");
                return;
            }

            nd = res.readEntity(NguoiDung.class);
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (nd == null) {
            out.println("<h3>Không lấy được thông tin người dùng</h3>");
            return;
        }

        int id = nd.getId();
        String vaiTro = nd.getVaiTro();
        String trangThai = nd.getTrangThai();
        String hoTen = nd.getHoTen();
        String email = nd.getEmail();
        String soDienThoai = nd.getSoDienThoai();
        String diaChi = nd.getDiaChi();

        String vaiTroHienThi = "";
        if ("nhanvien".equals(vaiTro)) vaiTroHienThi = "Nhân viên";
        else if ("khachhang".equals(vaiTro)) vaiTroHienThi = "Khách hàng";

        String trangThaiHienThi = "";
        if ("hoatdong".equals(trangThai)) trangThaiHienThi = "Hoạt động";
        else trangThaiHienThi = "Khóa";

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Thông tin tài khoản</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");
        out.println(".info-card{background:white;border-radius:12px;padding:30px;box-shadow:0 8px 20px rgba(0,0,0,0.08);}");
        out.println(".info-row{padding:10px 0;border-bottom:1px solid #eee;}");
        out.println(".label{font-weight:600;color:#444;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar navbar-expand-lg navbar-custom mb-4'>");
        out.println("<div class='container py-2'>");

        out.println("<a class='brand-text' href='TrangChu'>");
        out.println("<i class='bi bi-bag-heart-fill me-2'></i>Quản Lý Đặt Thức Ăn");
        out.println("</a>");

        out.println("<div class='d-flex align-items-center gap-2 flex-wrap'>");

        out.println("<a href='TrangChu' class='btn btn-outline-danger btn-sm rounded-pill px-3'>");
        out.println("<i class='bi bi-house-door-fill me-1'></i>Trang chủ");
        out.println("</a>");

        out.println("<a href='HoaDonDaDat' class='btn invoice-btn btn-sm me-2'>");
        out.println("<i class='bi bi-receipt-cutoff me-1'></i>Hóa đơn đã đặt");
        out.println("</a>");

        out.println("<a href='XemGioHang' class='btn btn-danger btn-sm cart-btn'>");
        out.println("<i class='bi bi-cart3 me-1'></i>Giỏ hàng");

        if (tongSoLuongTrongGio > 0) {
            out.println("<span class='cart-badge'>" + tongSoLuongTrongGio + "</span>");
        }

        out.println("</a>");

        out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + hoTen + "</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
        out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");
        out.println("<div class='info-card' style='max-width:520px;width:100%;'>");

        out.println("<h4 class='text-center mb-4 fw-bold'>Thông tin tài khoản</h4>");

        out.println("<div class='info-row'><span class='label'>ID:</span> " + id + "</div>");
        out.println("<div class='info-row'><span class='label'>Tên đăng nhập:</span> " + tenDangNhap + "</div>");
        out.println("<div class='info-row'><span class='label'>Họ tên:</span> " + hoTen + "</div>");
        out.println("<div class='info-row'><span class='label'>Email:</span> " + email + "</div>");
        out.println("<div class='info-row'><span class='label'>Số điện thoại:</span> " + soDienThoai + "</div>");
        out.println("<div class='info-row'><span class='label'>Địa chỉ:</span> " + diaChi + "</div>");
        out.println("<div class='info-row'><span class='label'>Vai trò:</span> " + vaiTroHienThi + "</div>");
        out.println("<div class='info-row'><span class='label'>Trạng thái:</span> " + trangThaiHienThi + "</div>");

        out.println("<div class='text-center mt-4'>");

        out.println("<a href='CapNhatThongTin' class='btn btn-warning me-2'>");
        out.println("<i class='bi bi-pencil'></i> Chỉnh sửa");
        out.println("</a>");

        out.println("<a href='DoiMatKhau' class='btn btn-warning me-2'>");
        out.println("<i class='bi bi-key'></i> Đổi mật khẩu");
        out.println("</a>");

        out.println("<a href='TrangChu' class='btn btn-danger'>Quay lại</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}