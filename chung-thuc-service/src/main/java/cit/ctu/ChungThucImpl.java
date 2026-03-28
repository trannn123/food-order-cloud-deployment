package cit.ctu;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import static cit.ctu.Constant.BASE_API_URL_NGUOI_DUNG;

public class ChungThucImpl implements IChungThuc{
	private static final URI URI_NGUOIDUNG = UriBuilder.fromUri(BASE_API_URL_NGUOI_DUNG).build();
	ClientConfig config = new ClientConfig();
	Client client = ClientBuilder.newClient(config);
	WebTarget target = client.target(URI_NGUOIDUNG);
	
	@Override
	public NguoiDung dangNhap(String tenDangNhap, String matKhau) {
	    try {
	        NguoiDung nd = target
	        		.path("rest")
	        		.path("nguoidung")
	        		.path("auth")
	        		.path(tenDangNhap)
	        		.request(MediaType.APPLICATION_JSON)
	        		.get(NguoiDung.class);
	        
	        if(nd!=null) {
	        	String matKhauHash = PasswordUtil.hashPassword(matKhau);
	        	if(matKhauHash.equals(nd.getMatKhau()) && "hoatdong".equals(nd.getTrangThai())) {
	        		NguoiDung kq = new NguoiDung();
	                kq.setId(nd.getId());
	                kq.setTenDangNhap(nd.getTenDangNhap());
	                kq.setHoTen(nd.getHoTen());
	                kq.setVaiTro(nd.getVaiTro());
	                kq.setTrangThai(nd.getTrangThai());
	                return kq;
	        	}
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
