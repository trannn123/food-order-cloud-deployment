package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.ItemGioHang;
import cit.ctu.QLDTAN.MonAn;
import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class DanhSachMonAn
 */
@WebServlet("/DanhSachMonAn")
public class DanhSachMonAn extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public DanhSachMonAn() {
        super();
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null) {
            response.sendRedirect("DangNhap");
            return;
        }

        Response res = target
                .path("rest")
                .path("monan")
                .path("LayDanhSachMonCon")
                .request(MediaType.APPLICATION_JSON)
                .get();
        
        List<MonAn> ds = null;
        if (res.getStatus() == 200) {
            ds = res.readEntity(new GenericType<List<MonAn>>() {});
        }
        res.close();

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        NguoiDung nd = (NguoiDung) session.getAttribute("user");
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");

        int tongSoLuongTrongGio = 0;
        if (cart != null) {
            for (ItemGioHang g : cart) {
                tongSoLuongTrongGio += g.getSoLuong();
            }
        }

        NumberFormat vnd = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
        out.println("<title>Danh sách món ăn</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css' rel='stylesheet'>");

        out.println("<style>");
        out.println(".disabled-plus { opacity: 0.4 !important; pointer-events: none; cursor: not-allowed; filter: grayscale(100%); }");
        out.println("body{background:linear-gradient(135deg,#fff8f5,#fff);font-family:system-ui,-apple-system,'Segoe UI',sans-serif;}");
        out.println(".navbar-custom{background:#fff;box-shadow:0 2px 12px rgba(0,0,0,0.06);}");
        out.println(".brand-text{color:#ff6b2c;font-size:1.4rem;font-weight:700;text-decoration:none;}");
        out.println(".page-title{color:#dc3545;font-weight:700;}");
        out.println(".food-card{border:none;border-radius:18px;overflow:hidden;transition:all .25s ease;box-shadow:0 6px 18px rgba(0,0,0,0.06);height:100%;}");
        out.println(".food-card:hover{transform:translateY(-6px);box-shadow:0 14px 32px rgba(0,0,0,0.12);}");
        out.println(".food-icon{height:160px;display:flex;align-items:center;justify-content:center;background:linear-gradient(135deg,#fff1eb,#ffe3d3);font-size:56px;color:#ff6b2c;}");
        out.println(".price-text{font-size:1.1rem;font-weight:700;color:#dc3545;}");
        out.println(".desc-text{min-height:48px;color:#6c757d;}");
        out.println(".cart-btn{position:relative;border-radius:12px;font-weight:600;padding-left:16px;padding-right:16px;}");
        out.println(".cart-badge{position:absolute;top:-8px;right:-8px;background:#dc3545;color:#fff;border-radius:999px;padding:4px 8px;font-size:12px;font-weight:700;min-width:24px;text-align:center;box-shadow:0 2px 8px rgba(220,53,69,.35);}");
        out.println(".welcome-text{color:#dc3545;font-weight:500;}");
        out.println(".empty-box{background:#fff;border-radius:16px;padding:30px;box-shadow:0 6px 18px rgba(0,0,0,0.06);}");
        out.println(".invoice-btn{background:#fff0f0;color:#dc3545;border:1px solid #f5c2c7;border-radius:999px;font-weight:600;padding:6px 14px;}");
        out.println(".invoice-btn:hover{background:#dc3545;color:#fff;border-color:#dc3545;}");
        out.println(".qty-control{display:flex;align-items:center;justify-content:center;gap:10px;margin-top:8px;}");
        out.println(".qty-btn{width:42px;height:42px;border:none;border-radius:12px;font-size:20px;font-weight:700;display:flex;align-items:center;justify-content:center;text-decoration:none;}");
        out.println(".btn-minus{background:#ffe5e5;color:#dc3545;}");
        out.println(".btn-minus:hover{background:#ffd4d4;color:#bb2d3b;}");
        out.println(".btn-plus{background:#ff6b2c;color:#fff;}");
        out.println(".btn-plus:hover{background:#e85a1f;color:#fff;}");
        out.println(".qty-number{min-width:48px;height:42px;display:flex;align-items:center;justify-content:center;background:#fff3cd;border:1px solid #ffe69c;border-radius:12px;font-weight:700;color:#856404;}");
        out.println(".add-first-btn{background:#ff6b2c;color:#fff;border:none;border-radius:12px;padding:10px 16px;font-weight:600;text-decoration:none;display:inline-block;}");
        out.println(".add-first-btn:hover{background:#e85a1f;color:#fff;}");
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
        if(!nd.getVaiTro().equals("nhanvien")){
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
        if (nd != null) {
            out.println("<span class='welcome-text ms-2'>Xin chào, <b>" + nd.getHoTen() + "</b></span>");
            out.println("<a href='DangXuat' class='btn btn-outline-secondary btn-sm rounded-pill px-3 ms-2'>");
            out.println("<i class='bi bi-box-arrow-right me-1'></i>Đăng xuất");
            out.println("</a>");
        }
        out.println("</div>");
        out.println("</div>");
        out.println("</nav>");

        out.println("<div class='container'>");
        out.println("<div class='text-center mb-4'>");
        out.println("<h2 class='page-title'><i class='bi bi-grid-fill me-2'></i>Danh sách món ăn</h2>");
        out.println("<p class='text-muted mb-0'>Chọn món yêu thích và tăng giảm số lượng ngay tại đây</p>");
        out.println("</div>");

        if (ds != null && !ds.isEmpty()) {
            out.println("<div class='row g-4'>");
            for (MonAn m : ds) {
                int soLuongDaThem = 0;

                if (cart != null) {
                    for (ItemGioHang g : cart) {
                        if (g.getMon() != null && g.getMon().getId() == m.getId()) {
                            soLuongDaThem = g.getSoLuong();
                            break;
                        }
                    }
                }

                out.println("<div class='col-md-6 col-lg-4'>");
                out.println("<div class='card food-card'>");
                out.println("<div class='food-icon'>");
                out.println("<i class='bi bi-cup-hot-fill'></i>");
                out.println("</div>");

                out.println("<div class='card-body d-flex flex-column'>");
                out.println("<h5 class='card-title fw-bold mb-2'>" + m.getTenMon() + "</h5>");

                String moTa = (m.getMoTa() != null && !m.getMoTa().trim().isEmpty())
                        ? m.getMoTa()
                        : "Món ăn thơm ngon, hấp dẫn, phù hợp cho bữa ăn nhanh.";
                out.println("<p class='desc-text'>" + moTa + "</p>");

                out.println("<div class='price-text mb-3'>Giá: " + vnd.format(m.getGia()) + "</div>");
                out.println("<div class='mt-auto text-center'>");

                if (soLuongDaThem > 0) {
                    out.println("<div class='qty-control'>");
                    out.println("<a href='GiamGioHang?id=" + m.getId() + "&from=menu' class='qty-btn btn-minus'>-</a>");
                    out.println("<div class='qty-number'>" + soLuongDaThem + "</div>");
                    if (soLuongDaThem < m.getSoLuong()) {
                    	out.println("<a href='ThemGioHang?id=" + m.getId() + "&from=menu' class='qty-btn btn-plus'>+</a>");
                    } else {
                        out.println("<span class='qty-btn btn-plus disabled-plus'>+</span>");
                    }
                    out.println("</div>");
                } else {
                    out.println("<a href='ThemGioHang?id=" + m.getId() + "' class='add-first-btn'>");
                    out.println("<i class='bi bi-cart-plus-fill me-1'></i>Thêm vào giỏ");
                    out.println("</a>");
                }
              
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
            }
            out.println("</div>");
        } else {
            out.println("<div class='empty-box text-center'>");
            out.println("<i class='bi bi-exclamation-circle text-warning' style='font-size:48px;'></i>");
            out.println("<h5 class='mt-3'>Hiện chưa có món ăn nào</h5>");
            out.println("<p class='text-muted mb-0'>Vui lòng quay lại sau để xem thêm món mới.</p>");
            out.println("</div>");
        }
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
