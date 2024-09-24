package co.vinni.kafka.SBProveedor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SbProveedorController {

    @Autowired
    private SbProveedorApplication pa;
    private String MensajeError;

    @PostMapping("/enviarSMSoEMAIL")
    public ResponseEntity<Map<String, String>> sendMessageToKafka(@RequestBody Request request) {
        Map<String, String> response = new HashMap<>();

        if(esPeticionValida(request)){
            try{
                if(request.getTipo().equals("EquipoKafka-sms")){
                    pa.sendMessage(request.getSms(), request.getTipo());
                    response.put("msg", "Procesando sms");
                }
                if(request.getTipo().equals("EquipoKafka-email")){
                    pa.sendMessage(request.getEmail(), request.getTipo());
                    response.put("msg", "Procesando email");
                }
                response.put("status", "true");

            } catch (Exception e) {
                response.put("status", "false");
                response.put("msg", "No se puede enviar la petición: " + e.getMessage());
                MensajeError = "No se puede enviar la petición: " + e.getMessage();
            }
        }else{
            response.put("status", "false");
            response.put("msg", "La petición es incorrecta: " + MensajeError);
        }
        return ResponseEntity.ok(response);
    }

    private boolean esPeticionValida(Request request) {
        try{
            if(request.getTipo().equals("EquipoKafka-sms")){
                if(request.getSms().getNumeroCel().isEmpty() || request.getSms().equals("")){
                    MensajeError = "Debe enviar un mensaje para el tipo de petición sms";
                    return false;
                }
                if(request.getEmail() != null){
                    MensajeError = "No debe enviar un email para el tipo de petición sms";
                    return false;
                }
            } else if (request.getTipo().equals("EquipoKafka-email")) {
                if(request.getSms() != null){
                    MensajeError = "No debe enviar un sms para el tipo de petición Email";
                    return false;
                }
                if(request.getEmail().getDestinatario().isEmpty() || request.getEmail().getDestinatario().equals("")){
                    MensajeError = "Debe informar a quíen quiere enviar el correo";
                    return false;
                }
                if(request.getEmail().getAsunto().isEmpty() || request.getEmail().getAsunto().equals("")){
                    MensajeError = "Debe informar el asunto del correo";
                    return false;
                }
                if(request.getEmail().getContenido().isEmpty() || request.getEmail().getContenido().equals("")){
                    MensajeError = "Debe informar el contenido del correo";
                    return false;
                }
            }else {
                MensajeError = "Debe indicar un tipo de mensaje correcto";
                return false;
            }
            return true;
        } catch (Exception e) {
            MensajeError = "Error -> " + e.getMessage();
            return false;
        }
    }
}
