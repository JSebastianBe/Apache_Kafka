package co.jsbm.kafka.SBProveedorController;

import co.vinni.kafka.SBProveedor.SbProveedorApplication;
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

    @PostMapping("/enviarSMSoEMAIL")
    public ResponseEntity<Map<String, String>> sendMessageToKafka(@RequestBody Request request) {
        pa.sendMessage(request);

        Map<String, String> response = new HashMap<>();
        response.put("status", "Enviado exitosamente");

        return ResponseEntity.ok(response);
    }
}
