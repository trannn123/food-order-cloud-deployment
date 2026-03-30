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
/**
 * Servlet implementation class CapNhatThongTin
 */
@WebServlet("/CapNhatThongTin")
public class CapNhatThongTin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
    Client client = ClientBuilder.newClient(new ClientConfig());
    WebTarget target = client.target(uri);
    public CapNhatThongTin() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung userSession = (NguoiDung) session.getAttribute("user");

        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        NguoiDung user = target.path("rest")
                .path("nguoidung")
                .path("id")
                .path(String.valueOf(userSession.getId()))
                .request(MediaType.APPLICATION_JSON)
                .get(NguoiDung.class);

        String hoTen = user.getHoTen() != null ? user.getHoTen() : "";
        String email = user.getEmail() != null ? user.getEmail() : "";
        String sdt = user.getSoDienThoai() != null ? user.getSoDienThoai() : "";
        String diaChi = user.getDiaChi() != null ? user.getDiaChi() : "";

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Cập nhật thông tin</title>");

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
        out.println(".form-card{max-width:760px;margin:0 auto;background:#fff;border:none;border-radius:20px;box-shadow:0 6px 18px rgba(0,0,0,0.08);}");
        out.println(".card-header-custom{background:linear-gradient(135deg,#ff6b2c,#ff8a50);color:#fff;padding:22px 28px;}");
        out.println(".card-body-custom{padding:30px 28px;}");
        out.println(".form-label{font-weight:600;color:#444;margin-bottom:8px;}");
        out.println(".form-control{border-radius:12px;padding:12px 14px;}");
        out.println(".btn-save{background:#ff6b2c;border:none;color:#fff;}");
        out.println(".btn-save:hover{background:#e85d22;color:#fff;}");
        out.println(".btn-cancel{background:#6c757d;color:#fff;}");
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

        if(!userSession.getVaiTro().equals("nhanvien")){
            out.println("<a href='HoaDonDaDat' class='btn invoice-btn btn-sm me-2'>");
            out.println("<i class='bi bi-receipt-cutoff me-1'></i>Hóa đơn đã đặt");
            out.println("</a>");

            out.println("<a href='XemGioHang' class='btn btn-danger btn-sm cart-btn'>");
            out.println("<i class='bi bi-cart3 me-1'></i>Giỏ hàng");

            if (tongSoLuongTrongGio > 0) {
                out.println("<span class='cart-badge'>" + tongSoLuongTrongGio + "</span>");
            }

            out.println("</a>");
        }


        out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + user.getHoTen() + "</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
        out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='form-card'>");

        out.println("<div class='card-header-custom'>");
        out.println("<h3><i class='bi bi-person-lines-fill me-2'></i>Cập nhật thông tin cá nhân</h3>");
        out.println("</div>");

        out.println("<div class='card-body-custom'>");
        out.println("<form method='post'>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Họ tên</label>");
        out.println("<input type='text' class='form-control' name='hoten' value='" + hoTen + "' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Email</label>");
        out.println("<input type='email' class='form-control' name='email' value='" + email + "' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Số điện thoại</label>");
        out.println("<input type='text' class='form-control' name='sdt' value='" + sdt + "' required>");
        out.println("</div>");

        out.println("<div class='mb-4'>");
        out.println("<label class='form-label'>Địa chỉ</label>");
        out.println("<input type='text' class='form-control' name='diachi' value='" + diaChi + "' required>");
        out.println("</div>");

        out.println("<div class='d-flex gap-2'>");
        out.println("<button type='submit' class='btn btn-save'>Cập nhật</button>");
        out.println("<a href='XemThongTin' class='btn btn-cancel'>Hủy</a>");
        out.println("</div>");

        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung user = (NguoiDung) session.getAttribute("user");

        String hoTen = request.getParameter("hoten");
        String email = request.getParameter("email");
        String sdt = request.getParameter("sdt");
        String diaChi = request.getParameter("diachi");

        NguoiDung ndUpdate = new NguoiDung();
        ndUpdate.setId(user.getId());
        ndUpdate.setTenDangNhap(user.getTenDangNhap());
        ndUpdate.setHoTen(hoTen);
        ndUpdate.setEmail(email);
        ndUpdate.setSoDienThoai(sdt);
        ndUpdate.setDiaChi(diaChi);

        try {
            Response res = target.path("rest")
                    .path("nguoidung")
                    .path("CapNhat")
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(ndUpdate, MediaType.APPLICATION_JSON));

            if (res.getStatus() != 200) {
                session.setAttribute("error", "Cập nhật thất bại!");
                response.sendRedirect("CapNhatThongTin");
                return;
            }

            res.close();

            NguoiDung userMoi = target.path("rest")
                    .path("nguoidung")
                    .path("id")
                    .path(String.valueOf(user.getId()))
                    .request(MediaType.APPLICATION_JSON)
                    .get(NguoiDung.class);

            session.setAttribute("user", userMoi);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống!");
            response.sendRedirect("CapNhatThongTin");
            return;
        }

        response.sendRedirect("XemThongTin");
    }
}