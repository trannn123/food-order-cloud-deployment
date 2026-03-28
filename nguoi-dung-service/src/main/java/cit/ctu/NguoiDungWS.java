package cit.ctu;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/nguoidung")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class NguoiDungWS {
	INguoiDung service = new NguoiDungImpl();
	
	@GET
    @Path("/ping")
	public Response ping() {
		return Response.ok("pong").build();
	}
	
	@POST
	public Response dangKy(NguoiDung nd) {
	    boolean kq = service.dangKy(nd);
	    if (kq) {
	        return Response.ok("Ä�Äƒng kĂ½ thĂ nh cĂ´ng").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("Ä�Äƒng kĂ½ tháº¥t báº¡i")
	                .build();
	    }
	}
	
	@PUT
    @Path("/CapNhat")
    public Response capNhat(NguoiDung nd) {
        boolean kq = service.capNhatNguoiDung(nd);
        if (kq) {
            return Response.ok("Cáº­p nháº­t thĂ nh cĂ´ng").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cáº­p nháº­t tháº¥t báº¡i")
                    .build();
        }
    }
	
	@GET
	@Path("/{tenDangNhap}")
	public Response timThongTinTheoTenDangNhap(@PathParam("tenDangNhap") String tenDangNhap) {
	    NguoiDung nd = service.xemThongTin(tenDangNhap);
	    if (nd != null) {
	    	nd.setMatKhau(null);
	        return Response.ok(nd).build();
	    } else {
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»�i dĂ¹ng")
	                .build();
	    }
	}
	
	@PUT
	@Path("/DoiMatKhau")
	public Response doiMatKhau(NguoiDung nd) {
	    boolean kq = service.doiMatKhau(
	        nd.getTenDangNhap(),
	        nd.getMatKhau(),
	        nd.getMatKhauMoi()
	    );
	    if (kq) {
	        return Response.ok().build();
	    } else {
	        return Response.status(Response.Status.UNAUTHORIZED)
	        		.entity("Máº­t kháº©u cÅ© khĂ´ng Ä‘Ăºng")
	        		.build();
	    }
	}
	
	@DELETE
	@Path("/{tenDangNhap}")
	public Response xoa(@PathParam("tenDangNhap") String tenDangNhap) {
	    boolean kq = service.xoaNguoiDung(tenDangNhap);
	    if (kq) {
	        return Response.ok("XĂ³a thĂ nh cĂ´ng").build();
	    } else {
	        return Response.status(Response.Status.BAD_REQUEST)
	                .entity("XĂ³a tháº¥t báº¡i")
	                .build();
	    }
	}
	
	@GET
	@Path("/id/{id}")
	public Response timThongTinTheoId(@PathParam("id") int id) {
	    NguoiDung nd = service.timTheoId(id);
	    if (nd != null) {
	    	nd.setMatKhau(null);
	        return Response.ok(nd).build();
	    }
	    return Response.status(Response.Status.NOT_FOUND)
	            .entity("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»�i dĂ¹ng")
	            .build();
	}
	
	@GET
	@Path("/auth/{tenDangNhap}")
	public Response chungThucNguoiDung(@PathParam("tenDangNhap") String tenDangNhap) {
	    NguoiDung nd = service.xemThongTin(tenDangNhap);
	    if (nd != null) {
	        return Response.ok(nd).build();
	    } else {
	        return Response.status(Response.Status.NOT_FOUND)
	                .entity("KhĂ´ng tĂ¬m tháº¥y ngÆ°á»�i dĂ¹ng")
	                .build();
	    }
	}
}


