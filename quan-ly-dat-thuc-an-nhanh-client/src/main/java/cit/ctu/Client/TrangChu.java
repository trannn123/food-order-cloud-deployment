package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import cit.ctu.QLDTAN.ItemGioHang;
import cit.ctu.QLDTAN.NguoiDung;
/**
 * Servlet implementation class TrangChu
 */
@WebServlet("/TrangChu")
public class TrangChu extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrangChu() {
        super();
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        NguoiDung nd = (NguoiDung) session.getAttribute("user");
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");

        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        String vaiTro = nd.getVaiTro(); 

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Trang chủ</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{");
        out.println("background:#f8f9fa;");
        out.println("min-height:100vh;");
        out.println("font-family:system-ui;");
        out.println("}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;box-shadow:0 2px 8px rgba(220,53,69,.35);}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");
        out.println(".dashboard-card{");
        out.println("background:white;");
        out.println("border:1px solid #eee;");
        out.println("transition:all 0.25s ease;");
        out.println("border-radius:14px;");
        out.println("cursor:pointer;");
        out.println("}");
        out.println(".dashboard-card:hover{");
        out.println("transform:translateY(-6px);");
        out.println("box-shadow:0 10px 25px rgba(0,0,0,0.08);");
        out.println("border-color:#ff6b2c;");
        out.println("}");
        out.println(".icon-box{");
        out.println("font-size:34px;");
        out.println("color:#ff6b2c;");
        out.println("transition:0.2s;");
        out.println("}");
        out.println(".dashboard-card:hover .icon-box{");
        out.println("transform:scale(1.15);");
        out.println("}");
        out.println(".btn-outline-danger{");
        out.println("border-color:#ff6b2c;");
        out.println("color:#ff6b2c;");
        out.println("}");
        out.println(".btn-outline-danger:hover{");
        out.println("background:#ff6b2c;");
        out.println("color:white;");
        out.println("}");
        out.println(".logout-btn:hover{");
        out.println("background:#ff6b2c !important;");
        out.println("color:white !important;");
        out.println("}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<nav class='navbar navbar-expand-lg navbar-custom mb-4'>");
        out.println("<div class='container py-2'>");
        out.println("<a class='brand-text' href='TrangChu'>");
        out.println("<i class='bi bi-bag-heart-fill me-2'></i>Quản Lý Đặt Thức Ăn");
        out.println("</a>");
        out.println("<div class='d-flex align-items-center gap-2 flex-wrap'>");

        if ("khachhang".equals(vaiTro)) {
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

        out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");

        out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
        out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container d-flex justify-content-center align-items-center' style='min-height:80vh;'>");
        out.println("<div class='bg-white p-5 rounded shadow' style='max-width:700px;width:100%;'>");
        out.println("<h4 class='text-center mb-4 fw-bold'>TRANG CHỦ</h4>");
        out.println("<div class='row g-4'>");

        if ("khachhang".equals(vaiTro)) {
            out.println("<div class='col-md-6'>");
            out.println("<a href='DanhSachMonAn' class='text-decoration-none text-dark'>");
            out.println("<div class='dashboard-card border p-4 text-center'>");
            out.println("<div class='icon-box'><i class='bi bi-basket'></i></div>");
            out.println("<div class='mt-2 fw-semibold'>Đặt món</div>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");

            out.println("<div class='col-md-6'>");
            out.println("<a href='QuanLyNguoiDung' class='text-decoration-none text-dark'>");
            out.println("<div class='dashboard-card border p-4 text-center'>");
            out.println("<div class='icon-box'><i class='bi bi-person-circle'></i></div>");
            out.println("<div class='mt-2 fw-semibold'>Thông tin cá nhân</div>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");
        }

        if ("nhanvien".equals(vaiTro)) {

            out.println("<div class='col-md-4'>");
            out.println("<a href='QuanLyNguoiDung' class='text-decoration-none text-dark'>");
            out.println("<div class='dashboard-card border p-4 text-center'>");
            out.println("<div class='icon-box'><i class='bi bi-people'></i></div>");
            out.println("<div class='mt-2 fw-semibold'>Thông tin cá nhân</div>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");

            out.println("<div class='col-md-4'>");
            out.println("<a href='QuanLyHoaDon' class='text-decoration-none text-dark'>");
            out.println("<div class='dashboard-card border p-4 text-center'>");
            out.println("<div class='icon-box'><i class='bi bi-receipt'></i></div>");
            out.println("<div class='mt-2 fw-semibold'>Hóa đơn</div>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");

            out.println("<div class='col-md-4'>");
            out.println("<a href='QuanLyMon' class='text-decoration-none text-dark'>");
            out.println("<div class='dashboard-card border p-4 text-center'>");
            out.println("<div class='icon-box'><i class='bi bi-egg-fried'></i></div>");
            out.println("<div class='mt-2 fw-semibold'>Món ăn</div>");
            out.println("</div>");
            out.println("</a>");
            out.println("</div>");
        }

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
    	// TODO Auto-generated method stub
    	doGet(request, response);
    }
}
