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
 * Servlet implementation class DoiMatKhau
 */
@WebServlet("/DoiMatKhau")
public class DoiMatKhau extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
    Client client = ClientBuilder.newClient(new ClientConfig());
    WebTarget target = client.target(uri);
    public DoiMatKhau() {
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

        NguoiDung user = (NguoiDung) session.getAttribute("user");

        // ===== GIỎ HÀNG =====
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");
        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        String error = null;
        if (session.getAttribute("error") != null) {
            error = (String) session.getAttribute("error");
            session.removeAttribute("error");
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Đổi mật khẩu</title>");

        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        // ===== CSS CHUẨN =====
        out.println("<style>");
        out.println("body{background:#f8f9fa;font-family:system-ui;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");
        out.println(".card-custom{border:none;border-radius:18px;box-shadow:0 4px 14px rgba(0,0,0,0.08);}");
        out.println(".page-title{font-weight:700;color:#333;}");
        out.println(".btn-orange{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-orange:hover{background:#e85d22;color:white;}");
        out.println(".form-label{font-weight:600;color:#444;}");
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

        if(!user.getVaiTro().equals("nhanvien")){
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
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8 col-lg-6'>");
        out.println("<div class='card card-custom p-4'>");

        out.println("<h3 class='mb-4 text-center page-title'><i class='bi bi-key'></i> Đổi mật khẩu</h3>");

        if (error != null) {
            out.println("<div class='alert alert-danger text-center'>" + error + "</div>");
        }

        out.println("<form method='post'>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Mật khẩu cũ</label>");
        out.println("<input type='password' class='form-control' name='oldpass' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Mật khẩu mới</label>");
        out.println("<input type='password' class='form-control' name='newpass' required>");
        out.println("</div>");

        out.println("<div class='mb-3'>");
        out.println("<label class='form-label'>Nhập lại mật khẩu</label>");
        out.println("<input type='password' class='form-control' name='renewpass' required>");
        out.println("</div>");

        out.println("<div class='d-flex justify-content-center gap-2 mt-4'>");
        out.println("<button type='submit' class='btn btn-orange'><i class='bi bi-check-circle'></i> Đổi mật khẩu</button>");
        out.println("<a href='XemThongTin' class='btn btn-secondary'><i class='bi bi-x-circle'></i> Hủy</a>");
        out.println("</div>");

        out.println("</form>");

        out.println("</div>");
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

        String matKhauCu = request.getParameter("oldpass");
        String matKhauMoi = request.getParameter("newpass");
        String nhapLai = request.getParameter("renewpass");

        if (!matKhauMoi.equals(nhapLai)) {
            session.setAttribute("error", "Mật khẩu mới và nhập lại không khớp!");
            response.sendRedirect("DoiMatKhau");
            return;
        }

        try {
            NguoiDung ndUpdate = new NguoiDung();
            ndUpdate.setTenDangNhap(user.getTenDangNhap());
            ndUpdate.setMatKhau(matKhauCu);
            ndUpdate.setMatKhauMoi(matKhauMoi);

            Response res = target.path("rest")
                    .path("nguoidung")
                    .path("DoiMatKhau")
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(ndUpdate, MediaType.APPLICATION_JSON));

            int status = res.getStatus();

            if (status == 200) {
                res.close();
                session.invalidate();
                response.sendRedirect("DangNhap");
                return;
            } else if (status == 401) {
                session.setAttribute("error", "Mật khẩu cũ không đúng!");
            } else {
                session.setAttribute("error", "Đổi mật khẩu thất bại!");
            }

            response.sendRedirect("DoiMatKhau");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đổi mật khẩu thất bại!");
            response.sendRedirect("DoiMatKhau");
        }
    }
}