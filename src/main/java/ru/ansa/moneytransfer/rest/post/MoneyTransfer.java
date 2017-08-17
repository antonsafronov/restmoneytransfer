package ru.ansa.moneytransfer.rest.post;

import org.joda.money.Money;
import ru.ansa.moneytransfer.exceptions.NotEnoughMoney;
import ru.ansa.moneytransfer.service.Transfer;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.math.BigInteger;

@Path("/transfer/v.1")
@ManagedBean
public class MoneyTransfer {

    @Inject
    private Transfer transfer;

    @POST
    @Path("/a2a")

    /**
     * The API for transfer money.
     *
     * @param {@link TransferInfo} the response status.
     * @return a new {@link Response}.
     *
     */
    public Response transfer(TransferInfo transferInfo){

        try {
            String result = transfer.transfer(new BigInteger(transferInfo.getAccountFromId()),new BigInteger(transferInfo.getAccountToId()),Money.parse(transferInfo.getTransferMoney()));
            return Response.status(200).entity(result).build();

        } catch (NotEnoughMoney nem){
            return Response.status(403).entity(nem).build();
        }
        catch (Exception e){
            return Response.status(500).entity(e).build();
        }
    }

}
