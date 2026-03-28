package cit.ctu.Client;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import cit.ctu.QLDTAN.ItemGioHang;
import cit.ctu.QLDTAN.MonAn;

import static cit.ctu.Constants.BASE_API_URL_MON_AN;

/**
 * Servlet implementation class ThemGioHang
 */
@WebServlet("/ThemGioHang")
public class ThemGioHang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    private static final URI uri =
            UriBuilder.fromUri(BASE_API_URL_MON_AN).build();
    ClientConfig config = new ClientConfig();
    Client client = ClientBuilder.newClient(config);
    WebTarget target = client.target(uri);
    public ThemGioHang() {
        super();
        // TODO Auto-generated constructor stub
    }
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String from = request.getParameter("from");

        if (idStr == null || idStr.trim().isEmpty()) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        MonAn mon = target
                .path("rest")
                .path("monan")
                .path("TimMon")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<MonAn>() {});

        if (mon == null) {
            if ("cart".equals(from)) {
                response.sendRedirect("XemGioHang");
            } else {
                response.sendRedirect("DanhSachMonAn");
            }
            return;
        }

        HttpSession session = request.getSession();
        List<ItemGioHang> cart = (List<ItemGioHang>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean tonTai = false;

        for (ItemGioHang g : cart) {
            if (g.getMon() != null && g.getMon().getId() == id) {
                g.setSoLuong(g.getSoLuong() + 1);
                tonTai = true;
                break;
            }
        }
        if (!tonTai) {
            ItemGioHang gh = new ItemGioHang(mon, 1);
            cart.add(gh);
        }

        session.setAttribute("cart", cart);

        if ("cart".equals(from)) {
            response.sendRedirect("XemGioHang");
        } else {
            response.sendRedirect("DanhSachMonAn");
        }
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
