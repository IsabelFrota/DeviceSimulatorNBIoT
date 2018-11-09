import com.fasterxml.jackson.databind.node.ObjectNode;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.network.Endpoint;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.resources.CoapExchange;
import wudashan.EDRXSimulator;
import wudashan.HexParser;
import wudashan.Protocol.ProtocolAdapterImplement;
import wudashan.Protocol.ProtocolAdapterInterface;
import wudashan.SimulatorResource;
import wudashan.SimulatorServer;


import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;

public class ApplicationRun {

    //    public ApplicationRun(){
    //
    //
    //    }
//    private boolean isHandleCommand = true;
//    private boolean isRegister = false;
//    private static final String IP = "IP";
//    private static final String EP = "EP";
//    private static final String PSK = "PSK";
//    private static final String FILENAME = "setting.properties";
//    private ProtocolAdapterInterface protocolAdapter = new ProtocolAdapterImplement();
//
//
//    public void RegisterDevice(final EDRXSimulator edrxSimulator, String ip, String ep){
//        edrxSimulator.getResource().setIsRegister(true);
//        edrxSimulator.getServer().startServer();
//        int port = edrxSimulator.isUseSecure() ? 5684 : 5683;
//        CoapClient client = (new CoapClient.Builder(ip, port)).query(new String[]{"ep=" + ep}).path(new String[]{"t", "r"}).create();
//        client.setEndpoint((Endpoint)edrxSimulator.getServer().getEndpoints().get(0));
//        CoapResponse response = client.post((byte[])null, 42);
//        if (response == null) {
//            JOptionPane.showMessageDialog(null,"The CIG does not respond. Check whether the CIG service is normal!"+ new Object[0].toString());
//        } else {
//            //JOptionPane.showMessageDialog(null,"CIG response code:%s."+ new Object[]{response.getCode()}.toString());
//            Object[] options;
//            if (!response.isSuccess()) {
//                options = new Object[]{"OK"};
//                JOptionPane.showOptionDialog(null, "Failed to register the device. View CIG logs.", "Confirm", 0, 3, (Icon)null, options, options[0]);
//            } else {
//                edrxSimulator.setHasRegister(true);
//                options = new Object[]{"OK", "Cancel"};
//                JOptionPane.showOptionDialog((Component)null, "The device is registered successfully. It can receive commands or report data now.", "Information", 0, 3, (Icon)null, options, options[0]);
//                isRegister = true;
//            }
//
//        }
//    }
//
//    public void handlePOST(final EDRXSimulator edrxSimulator, CoapExchange exchange, String message) {
//        byte[] payload;
//        if (isRegister) {
//            Response response = new Response(CoAP.ResponseCode.CHANGED);
//            response.setType(CoAP.Type.ACK);
//            exchange.respond(response);
//        } else {
//            payload = HexParser.parseHexStr2Byte(message);
//            Response response = new Response(CoAP.ResponseCode.CONTENT);
//            response.setType(CoAP.Type.NON);
//            response.setPayload(payload);
//            exchange.respond(response);
//        }
//
//
//        String payloa = new String(exchange.getRequestPayload(), StandardCharsets.UTF_8);
//
//        //edrxSimulator.getLogTextArea().printLog("CIG IP：%s，PORT：%s", new Object[]{exchange.getSourceAddress().getHostAddress(), exchange.getSourcePort()});
//        //edrxSimulator.getLogTextArea().printLog("The data delivered by the CIG is as follows:%s", new Object[]{payload});
//        ObjectNode commandNode = protocolAdapter.decode(exchange.advanced().getRequest().getPayload());
//        if (commandNode == null) {
//            Response response = new Response(CoAP.ResponseCode.CHANGED);
//            response.setType(CoAP.Type.ACK);
//            exchange.respond(response);
//        } else if (isHandleCommand) {
//            Object[] options = new Object[]{"Yes(Y)", "No(N)"};
//            int reponse = JOptionPane.showOptionDialog(null, "The downlink message from the platform is received. Respond?", "Confirm", 0, 3, (Icon)null, options, options[0]);
//            if (reponse == 0) {
                Response response = new Response(CoAP.ResponseCode.CHANGED);
//                response.setType(exchange.advanced().getRequest().getType());
//                exchange.respond(response);
//            }
//        }
//
//    }
    public static void main(String[] args) {
        EDRXSimulator edrxSimulator = new EDRXSimulator();
        SimulatorServer coiso = edrxSimulator.getServer();
        coiso = new SimulatorServer(new Testadora("d", edrxSimulator),edrxSimulator);
//        ApplicationRun ap = new ApplicationRun();
//        String ip = "201.163.229.230";
//        String ep = "863703031025203";
//        ap.RegisterDevice(edrxSimulator,ip,ep);
//        ap.handlePOST(edrxSimulator,new CoapExchange(),"01 02 03");


    }
}

class Testadora extends SimulatorResource {

    public Testadora(String name, EDRXSimulator edrxSimulator) {
        super(name, edrxSimulator);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        System.out.println("XABLAU");
        super.handlePOST(exchange);
    }

}