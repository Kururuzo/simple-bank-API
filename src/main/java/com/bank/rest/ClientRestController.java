package com.bank.rest;


import com.bank.model.Client;
import com.bank.model.to.ClientTo;
import com.bank.service.ClientService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("client")
public class ClientRestController {

    ClientService service = new ClientService();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Client got it!";
    }

//    @GET
//    @Path("/{id}")
////    public Response get(@PathParam("id") long id) {
//    public String get(@PathParam("id") long id) {
//        return "Client " + id + ", got it!";
//    }

    @GET
    @Path("/{id}")
    public ClientTo get(@PathParam("id") int id) {
        ClientTo clientTo = new ClientTo(service.getById(id));
        return clientTo;
    }

    @GET
    @Path("/all")
    public List<ClientTo> get() {
        List<Client> all = service.getAll();
        List<ClientTo> tos = all.stream().map(ClientTo::new).collect(Collectors.toList());
        return tos;
    }

    //    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Track getTrackInJSON() {
//
//        Track track = new Track();
//        track.setTitle("Enter Sandman");
//        track.setSinger("Metallica");
//
//        return track;
//
//    }
//    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Client getTrackInJSON() {
//        Client client = new Client();
//        client.setName("su");
//        client.setEmail("12@mail.ru");
//        return client;
//
//    }
//
//    @POST
//    @Path("/post")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response createTrackInJSON(Client client) {
//        String result = "Track saved : " + client;
//        return Response.status(201).entity(result).build();
//
//    }
}
