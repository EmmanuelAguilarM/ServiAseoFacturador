/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Model.DetalleFacturaDTO;
import java.util.List;

/**
 *
 * @author aguilarje
 */
public class Json {
    public String convertirListaDetalleFacturaAJson(List<DetalleFacturaDTO> detalles) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < detalles.size(); i++) {
            DetalleFacturaDTO detalle = detalles.get(i);
            json.append("{")
                .append("\"ProductoID\":").append(detalle.getProductoId()).append(",")
                .append("\"TipoLimpiezaID\":").append(detalle.getTipoLimpiezaId()).append(",")
                .append("\"Cantidad\":").append(detalle.getCantidad()).append(",")
                .append("\"Subtotal\":").append(detalle.getSubtotal())
                .append("}");
            if (i < detalles.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }
}
