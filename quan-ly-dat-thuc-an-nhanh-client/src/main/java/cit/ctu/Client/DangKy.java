package cit.ctu.Client;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.NguoiDung;

import static cit.ctu.Constants.BASE_API_URL_NGUOI_DUNG;

/**
 * Servlet implementation class DangKy
 */
@WebServlet("/DangKy")
public class DangKy extends HttpServlet {

    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public DangKy() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Đăng ký tài khoản</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css' rel='stylesheet'>");

        out.println("<style>");
        out.println("body{background:#f8f9fa;height:100vh;display:flex;align-items:center;justify-content:center;font-family:system-ui;}");
        out.println(".register-card{width:420px;border:none;border-radius:6px;box-shadow:0 10px 25px rgba(0,0,0,0.08);}");
        out.println(".title{color:#ff6b2c;font-weight:700;text-align:center;margin-bottom:20px;}");
        out.println(".btn-main{background:#ff6b2c;color:white;border:none;}");
        out.println(".btn-main:hover{background:#ff5722;}");
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='card register-card p-4'>");
        out.println("<h3 class='title'>Đăng ký tài khoản</h3>");
        out.println("<form method='post'>");
  
	    out.println("<div class='mb-3'>");
	    out.println("<input class='form-control' name='tenDangNhap' placeholder='Tên đăng nhập' "
	             + "required minlength='4' maxlength='20' pattern='[a-zA-Z0-9]+' "
	             + "title='Tên đăng nhập 4-20 ký tự, chỉ gồm chữ và số'>");
	    out.println("</div>");

    
	    out.println("<div class='mb-3'>");
	    out.println("<input type='password' class='form-control' name='matKhau' placeholder='Mật khẩu' "
	             + "required minlength='6' title='Mật khẩu tối thiểu 6 ký tự'>");
	    out.println("</div>");

	    out.println("<div class='mb-3'>");
	    out.println("<input class='form-control' name='hoTen' placeholder='Họ tên' "
             + "required pattern='[A-Za-zÀ-ỹ ]+' "
             + "title='Họ tên chỉ được chứa chữ'>");
	    out.println("</div>");

	    out.println("<div class='mb-3'>");
	    out.println("<input type='email' class='form-control' name='email' placeholder='Email' required>");
	    out.println("</div>");

	    out.println("<div class='mb-3'>");
	    out.println("<input class='form-control' name='soDienThoai' placeholder='Số điện thoại' "
	             + "required pattern='[0-9]{10}' title='Số điện thoại phải có 10 số'>");
	    out.println("</div>");

	    out.println("<div class='mb-3'>");
	    out.println("<textarea class='form-control' name='diaChi' placeholder='Địa chỉ' "
	             + "required minlength='5'></textarea>");
	    out.println("</div>");

	    out.println("<button class='btn btn-main w-100'>Đăng ký</button>");
	    out.println("</form>");

        out.println("<div class='text-center mt-3'>");
        out.println("Đã có tài khoản? <a href='DangNhap'>Đăng nhập</a>");
        out.println("</div>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        String tenDangNhap = request.getParameter("tenDangNhap");
        String matKhau = request.getParameter("matKhau");
        String hoTen = request.getParameter("hoTen");
        String email = request.getParameter("email");
        String soDienThoai = request.getParameter("soDienThoai");
        String diaChi = request.getParameter("diaChi");

    	NguoiDung nd = new NguoiDung();
    	nd.setTenDangNhap(tenDangNhap);
    	nd.setMatKhau(matKhau);
    	nd.setHoTen(hoTen);
    	nd.setEmail(email);
    	nd.setSoDienThoai(soDienThoai);
    	nd.setDiaChi(diaChi);
    	
        Response ketQua = target
                .path("rest")
                .path("nguoidung")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(nd, MediaType.APPLICATION_JSON));
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (ketQua.getStatus() == 200) {
            response.sendRedirect("DangNhap");
        } else {
            out.println("<html><body>");
            out.println("<h3 style='color:red'>Đăng ký thất bại</h3>");
            out.println("<a href='DangKy'>Quay lại</a>");
            out.println("</body></html>");
        }
    }
}