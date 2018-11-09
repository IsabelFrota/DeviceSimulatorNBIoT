//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package wudashan;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.coap.CoAP.ResponseCode;
import org.eclipse.californium.core.coap.CoAP.Type;
import org.eclipse.californium.core.server.resources.CoapExchange;
import wudashan.Protocol.ProtocolAdapterImplement;
import wudashan.Protocol.ProtocolAdapterInterface;

public class SimulatorResource extends CoapResource {
    private boolean isHandleCommand = true;
    private EDRXSimulator edrxSimulator;
    private boolean isRegister = true;
    private ProtocolAdapterInterface protocolAdapter = new ProtocolAdapterImplement();

    public void setIsRegister(boolean isRegister) {
        this.isRegister = isRegister;
    }

    public void setIsHandleCommand(boolean isHandleCommand) {
        this.isHandleCommand = isHandleCommand;
    }

    public SimulatorResource(String name, EDRXSimulator edrxSimulator) {
        super(name);
        this.edrxSimulator = edrxSimulator;
        this.setObservable(true);
    }

    public void handleGET(CoapExchange exchange) {
        if (this.isRegister) {
            Response response = new Response(ResponseCode.CHANGED);
            response.setType(Type.ACK);
            exchange.respond(response);
        } else {
            int i = this.edrxSimulator.getTabbedPane().getSelectedIndex();
            byte[] payload;
            switch(i) {
                case 0:
                    payload = HexParser.parseHexStr2Byte(this.edrxSimulator.getHexMsgArea().getText());
                    break;
                case 1:
                    payload = this.convertMsgToByte(this.edrxSimulator.getJsonMsgArea().getText());
                    break;
                default:
                    payload = null;
            }

            Response response = new Response(ResponseCode.CONTENT);
            response.setType(Type.NON);
            response.setPayload(payload);
            exchange.respond(response);
            this.edrxSimulator.getLogTextArea().printLog("CIG IP：%s，PORT：%s", new Object[]{exchange.getSourceAddress().getHostAddress(), exchange.getSourcePort()});
            this.edrxSimulator.getLogTextArea().printLog("The data reported by the simulator is as follows:%s", new Object[]{HexParser.parseByte2HexStr(response.getPayload())});
        }

    }

    public void handlePOST(CoapExchange exchange) {
        String payload = new String(exchange.getRequestPayload(), StandardCharsets.UTF_8);
        this.edrxSimulator.getLogTextArea().printLog("CIG IP：%s，PORT：%s", new Object[]{exchange.getSourceAddress().getHostAddress(), exchange.getSourcePort()});
        this.edrxSimulator.getLogTextArea().printLog("The data delivered by the CIG is as follows:%s", new Object[]{payload});
        JOptionPane.showMessageDialog(null,"A mensagem recebida pela OC foi "+payload);
        ObjectNode commandNode = this.protocolAdapter.decode(exchange.advanced().getRequest().getPayload());
        if (commandNode == null) {
            Response response = new Response(ResponseCode.CHANGED);
            response.setType(Type.ACK);
            exchange.respond(response);
        } else if (this.isHandleCommand) {
            Object[] options = new Object[]{"Yes(Y)", "No(N)"};
            int reponse = JOptionPane.showOptionDialog(this.edrxSimulator, "The downlink message from the platform is received. Respond?", "Confirm", 0, 3, (Icon)null, options, options[0]);
            if (reponse == 0) {
                Response response = new Response(ResponseCode.CHANGED);
                response.setType(exchange.advanced().getRequest().getType());
                exchange.respond(response);
            }
        }

    }

    private byte[] convertMsgToByte(String payload) {
        try {
            return payload.getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }
}
