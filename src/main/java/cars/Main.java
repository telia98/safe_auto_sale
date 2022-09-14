package cars;

import gui.IndexFrame;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class Main {
    public static final String addressContract = "0xead9a7e4f15dCaD0e9990D3177E459CF287c0C1F";
    public static final BigInteger GAS_LIMIT = new BigInteger("200000");
    public static final BigInteger GAS_PRICE = Convert.toWei("2", Convert.Unit.GWEI).toBigInteger();
    private static final ContractGasProvider contractGasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    public static Web3j web3j;
    public static Credentials credentials;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println("Connecting to Ethereum ...");
        web3j = Web3j.build(new HttpService("http://localhost:7545"));
        System.out.println("Successfuly connected to Ethereum");

        try {
            //credentials = Credentials.create(Keys.createEcKeyPair());
            credentials = Credentials.create("3c632fd037992618541489a59a1987d7161e3097845612d10a035ab50457869d");
            TransactionManager tm = new RawTransactionManager(web3j, credentials, 200, 500);
            Cars contract = Cars.load(addressContract, web3j, tm, contractGasProvider);

            IndexFrame index= new IndexFrame(contract);

            /*
                ISTRUZIONI PER AGGIORNARE IL CONTRATTO
                contract.setStateCar(BigInteger.valueOf(0),"graffio paraurti posteriore",BigInteger.valueOf(5000),BigInteger.valueOf(26000));
                System.out.println("action done");
            */
            /*
                ISTRUZIONI PER LEGGERE IL CONTRATTO
                String carInfo = contract.getCarInfo(BigInteger.valueOf(0)).send();
                System.out.println(carInfo);
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println("Unconnecting to Ethereum ...");
        //web3j.shutdown();
    }
}
