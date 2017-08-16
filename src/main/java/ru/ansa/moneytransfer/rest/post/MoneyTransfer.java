package ru.ansa.moneytransfer.rest.post;

import ru.ansa.moneytransfer.service.Transfer;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/transfer/v.1")
@ManagedBean
public class MoneyTransfer {

    @Inject
    private Transfer transfer;

    @POST
    @Path("/a2a")
    public Response transfer(TransferInfo transferInfo){

        try {

            return Response.status(200).entity(transfer.transfer(transferInfo.getAccountFromId(),transferInfo.getAccountToId(),transferInfo.getTransferMoney())).build();

        } catch (Exception e){
            return Response.status(400).entity(e).build();
        }
    }

}
