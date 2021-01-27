package org.wso2.carbon.apimgt.rest.api.admin.v1;

import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.dto.VHostListDTO;
import org.wso2.carbon.apimgt.rest.api.admin.v1.VhostsApiService;
import org.wso2.carbon.apimgt.rest.api.admin.v1.impl.VhostsApiServiceImpl;
import org.wso2.carbon.apimgt.api.APIManagementException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;

import io.swagger.annotations.*;
import java.io.InputStream;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
@Path("/vhosts")

@Api(description = "the vhosts API")




public class VhostsApi  {

  @Context MessageContext securityContext;

VhostsApiService delegate = new VhostsApiServiceImpl();


    @GET
    
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Get all created VHosts", notes = "This operation can be used to retrieve a list of VHost with the gateway environments it is attached to. ", response = VHostListDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations"),
            @AuthorizationScope(scope = "apim:vhost_read", description = "Retrieve VHosts")
        })
    }, tags={ "VHost (Collection)",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. VHost list returned. ", response = VHostListDTO.class) })
    public Response vhostsGet() throws APIManagementException{
        return delegate.vhostsGet(securityContext);
    }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Add a VHost", notes = "Add a new VHost ", response = VHostDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations"),
            @AuthorizationScope(scope = "apim:vhost_manage", description = "Manage VHosts")
        })
    }, tags={ "VHost",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created. Successful response with the newly created object as entity in the body. ", response = VHostDTO.class),
        @ApiResponse(code = 400, message = "Bad Request. Invalid request or validation error.", response = ErrorDTO.class) })
    public Response vhostsPost(@ApiParam(value = "VHost object that should to be added " ,required=true) VHostDTO vhostDTO) throws APIManagementException{
        return delegate.vhostsPost(vhostDTO, securityContext);
    }

    @DELETE
    @Path("/{vhostId}")
    
    @Produces({ "application/json" })
    @ApiOperation(value = "Delete a VHost", notes = "Delete a VHost by VHost Id ", response = Void.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations"),
            @AuthorizationScope(scope = "apim:vhost_manage", description = "Manage VHosts")
        })
    }, tags={ "VHost",  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. VHost successfully deleted. ", response = Void.class),
        @ApiResponse(code = 404, message = "Not Found. The specified resource does not exist.", response = ErrorDTO.class) })
    public Response vhostsVhostIdDelete(@ApiParam(value = "VHost ID ",required=true) @PathParam("vhostId") String vhostId) throws APIManagementException{
        return delegate.vhostsVhostIdDelete(vhostId, securityContext);
    }

    @PUT
    @Path("/{vhostId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Update a VHosts", notes = "Update a VHosts by VHosts Id ", response = VHostDTO.class, authorizations = {
        @Authorization(value = "OAuth2Security", scopes = {
            @AuthorizationScope(scope = "apim:admin", description = "Manage all admin operations"),
            @AuthorizationScope(scope = "apim:vhost_manage", description = "Manage VHosts")
        })
    }, tags={ "VHosts" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK. VHost updated. ", response = VHostDTO.class),
        @ApiResponse(code = 400, message = "Bad Request. Invalid request or validation error.", response = ErrorDTO.class),
        @ApiResponse(code = 404, message = "Not Found. The specified resource does not exist.", response = ErrorDTO.class) })
    public Response vhostsVhostIdPut(@ApiParam(value = "VHost ID ",required=true) @PathParam("vhostId") String vhostId, @ApiParam(value = "VHost object with updated information " ,required=true) VHostDTO vhostDTO) throws APIManagementException{
        return delegate.vhostsVhostIdPut(vhostId, vhostDTO, securityContext);
    }
}
