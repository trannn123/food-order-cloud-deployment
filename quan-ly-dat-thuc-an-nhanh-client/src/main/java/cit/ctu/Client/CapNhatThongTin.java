package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import cit.ctu.QLDTAN.NguoiDung;
import org.glassfish.jersey.client.ClientConfig;

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
        out.println(".custom-navbar{background:#fff;box-shadow:0 4px 14px rgba(0,0,0,0.06);padding:14px 0;}");
        out.println(".navbar-brand{color:#ff6b2c !important;font-weight:800;font-size:1.35rem;letter-spacing:.3px;}");
        out.println(".brand-icon{margin-right:8px;font-size:1.2rem;}");
        out.println(".nav-btn{border-radius:10px;padding:8px 14px;font-weight:600;transition:all .2s ease;}");
        out.println(".btn-home{border:1px solid #ff6b2c;color:#ff6b2c;background:#fff;}");
        out.println(".btn-home:hover{background:#ff6b2c;color:#fff;}");
        out.println(".btn-back{border:1px solid #0d6efd;color:#0d6efd;background:#fff;}");
        out.println(".btn-back:hover{background:#0d6efd;color:#fff;}");
        out.println(".btn-logout{background:#dc3545;color:#fff;border:1px solid #dc3545;}");
        out.println(".btn-logout:hover{background:#bb2d3b;color:#fff;}");
        out.println(".page-wrap{padding:40px 0;}");
        out.println(".form-card{max-width:760px;margin:0 auto;background:#fff;border:none;border-radius:20px;box-shadow:0 6px 18px rgba(0,0,0,0.08);overflow:hidden;}");
        out.println(".card-header-custom{background:linear-gradient(135deg,#ff6b2c,#ff8a50);color:#fff;padding:22px 28px;}");
        out.println(".card-header-custom h3{margin:0;font-size:1.45rem;font-weight:700;}");
        out.println(".card-header-custom p{margin:6px 0 0;opacity:.95;}");
        out.println(".card-body-custom{padding:30px 28px;}");
        out.println(".form-label{font-weight:600;color:#444;margin-bottom:8px;}");
        out.println(".form-control{border-radius:12px;padding:12px 14px;border:1px solid #dee2e6;box-shadow:none;}");
        out.println(".form-control:focus{border-color:#ff6b2c;box-shadow:0 0 0 0.2rem rgba(255,107,44,.15);}");
        out.println(".btn-action{border-radius:12px;padding:10px 18px;font-weight:600;}");
        out.println(".btn-save{background:#ff6b2c;border:none;color:#fff;}");
        out.println(".btn-save:hover{background:#e85d22;color:#fff;}");
        out.println(".btn-cancel{background:#6c757d;border:none;color:#fff;}");
        out.println(".btn-cancel:hover{background:#5c636a;color:#fff;}");
        out.println("</style>");
        
        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar custom-navbar mb-4'>");
        out.println("<div class='container d-flex justify-content-between align-items-center'>");
        out.println("<a class='navbar-brand m-0' href='TrangChu'><i class='bi bi-basket2-fill brand-icon'></i>Quản Lý Đặt Thức Ăn</a>");
        out.println("<div class='d-flex gap-2'>");
        out.println("<a href='TrangChu' class='btn nav-btn btn-home'><i class='bi bi-house-door me-1'></i>Trang chủ</a>");
        out.println("<a href='XemThongTin' class='btn nav-btn btn-back'><i class='bi bi-arrow-left me-1'></i>Quay lại</a>");
        out.println("<a href='DangXuat' class='btn nav-btn btn-logout'><i class='bi bi-box-arrow-right me-1'></i>Đăng xuất</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container page-wrap'>");
        out.println("<div class='form-card'>");
        out.println("<div class='card-header-custom'>");
        out.println("<h3><i class='bi bi-person-lines-fill me-2'></i>Cập nhật thông tin cá nhân</h3>");
        out.println("<p>Chỉnh sửa thông tin tài khoản của bạn bên dưới</p>");
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
        out.println("<button type='submit' class='btn btn-action btn-save'><i class='bi bi-check-circle me-1'></i>Cập nhật</button>");
        out.println("<a href='XemThongTin' class='btn btn-action btn-cancel'><i class='bi bi-x-circle me-1'></i>Hủy</a>");
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
            
            session.setAttribute("user",userMoi);
        } catch (Exception e) {
        	e.printStackTrace();
            session.setAttribute("error", "Lỗi hệ thống!");
            response.sendRedirect("CapNhatThongTin");
            return;
        }
        response.sendRedirect("XemThongTin");
    }
}
