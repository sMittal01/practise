package first.jersey;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.gson.Gson;

import first.db.DbSource;
import first.db.MyPojo;

@Path("/FirstJersey")
public class FirstJersey {

	private static Gson gson = new Gson();

	@POST
	@Path("/PostMethod")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postStudent(String pojo){
		MyPojo myPojo = gson.fromJson(pojo, MyPojo.class);
		String sql = "insert into world.city values ('"+myPojo.getId()+"','"+myPojo.getName()+"','"+myPojo.getCountryCode()+"','"+myPojo.getDistrict()+"','"+myPojo.getPopulation()+"')";
		try {
			DbSource.getDbInstance().putObject(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(500).entity("Failed").build();
		}
		return Response.ok(201).entity("Created").build();

	}

	@PUT
	@Path("/PutMethod/{Id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putStudent(String pojo, @PathParam("Id") int valId){
		MyPojo myPojo = gson.fromJson(pojo, MyPojo.class);
		String sql = "update world.city set Name='"+myPojo.getName()+"' where ID="+valId;
		try {
			DbSource.getDbInstance().putObject(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok(500).entity("Failed").build();
		}
		return Response.ok(204).entity("Updated").build();
	}

	@DELETE
	@Path("/DeleteMethod/{Id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("Id") int idVal){
		List obj;
		try{  
			obj= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal); 
			if(obj!=null || obj.isEmpty()){
				DbSource.getDbInstance().deleteObject("delete from city where ID="+idVal);
			}else{
				return Response.ok(500).entity("Failed. Record doesn't exist").build();
			}

		}catch(Exception e){ 
			e.printStackTrace();
			return Response.ok(500).entity("Failed").build();
		}

		return Response.ok(204).entity("Deleted").build();
	}



	@GET
	@Path("/FirstMethod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents(){
		List objs = null;
		try{  
			objs= DbSource.getDbInstance().getObjects("select * from city");  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

	@GET
	@Path("/FirstMethod/{Id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOneStudent(@PathParam("Id") int idVal){
		List objs = null;
		try{  
			objs= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal);  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

	@GET
	@Path("/SecondMethod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSecondStudent(@QueryParam("Id") int idVal){
		List objs = null;
		try{  
			objs= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal);  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

	@GET
	@Path("/ThirdMethod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThirddStudent(@HeaderParam("Id") int idVal){
		List objs = null;
		try{  
			objs= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal);  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

	@GET
	@Path("/FourthMethod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFourthdStudent(@Context HttpHeaders headers){
		List objs = null;
		try{  
			int idVal=Integer.parseInt((headers.getRequestHeader("Id").get(0)));
			objs= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal);  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

	@GET
	@Path("/FifthMethod")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFifthdStudent(@Context UriInfo info){
		List objs = null;
		try{  
			int idVal=Integer.parseInt(info.getQueryParameters().getFirst("Id"));
			objs= DbSource.getDbInstance().getObjects("select * from city where ID="+idVal);  
		}catch(Exception e){ e.printStackTrace();}

		return Response.ok(gson.toJson(objs)).build();

	}

}
