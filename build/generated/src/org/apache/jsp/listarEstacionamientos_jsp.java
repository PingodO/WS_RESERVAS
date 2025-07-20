package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import modelo.Estacionamiento;
import servicio.EstacionamientoService;

public final class listarEstacionamientos_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write('\r');
      out.write('\n');

    EstacionamientoService service = new EstacionamientoService();
    List<Estacionamiento> lista = service.listar();

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head><title>Lista de Estacionamientos</title></head>\r\n");
      out.write("<body>\r\n");
      out.write("    <h2>Estacionamientos Registrados</h2>\r\n");
      out.write("    <table border=\"1\">\r\n");
      out.write("        <tr><th>ID</th><th>Número</th><th>Estado</th><th>Acciones</th></tr>\r\n");
      out.write("        ");

            for (Estacionamiento e : lista) {
        
      out.write("\r\n");
      out.write("        <tr>\r\n");
      out.write("            <td>");
      out.print( e.getCodEsta() );
      out.write("</td>\r\n");
      out.write("            <td>");
      out.print( e.getNumero() );
      out.write("</td>\r\n");
      out.write("            ");

              String cssEstado = "";
              if ("disponible".equalsIgnoreCase(e.getEstado())) {
                cssEstado = "available";
              } else if ("reservado".equalsIgnoreCase(e.getEstado())) {
                cssEstado = "occupied";
              }
            
      out.write("\r\n");
      out.write("            <td class=\"");
      out.print( cssEstado );
      out.write('"');
      out.write('>');
      out.print( e.getEstado() );
      out.write("</td>\r\n");
      out.write("\r\n");
      out.write("            <td>\r\n");
      out.write("              <form action=\"EstacionamientoServlet\" method=\"post\" style=\"display:inline;\"\r\n");
      out.write("                    onsubmit=\"return confirm('¿Estás seguro de que quieres liberar la plaza ");
      out.print( e.getNumero() );
      out.write("?');\">\r\n");
      out.write("                <input type=\"hidden\" name=\"accion\" value=\"eliminar\">\r\n");
      out.write("                <input type=\"hidden\" name=\"id\" value=\"");
      out.print( e.getCodEsta() );
      out.write("\">\r\n");
      out.write("                <input type=\"submit\" value=\"Eliminar\">\r\n");
      out.write("              </form>\r\n");
      out.write("            </td>\r\n");
      out.write("\r\n");
      out.write("        </tr>\r\n");
      out.write("        ");
 } 
      out.write("\r\n");
      out.write("    </table>\r\n");
      out.write("    <a href=\"estacionamiento.jsp\">Registrar nuevo</a>\r\n");
      out.write("        <style>\r\n");
      out.write("        :root {\r\n");
      out.write("            --primary: #2c3e50;\r\n");
      out.write("            --secondary: #3498db;\r\n");
      out.write("            --available: #2ecc71;\r\n");
      out.write("            --occupied: #e74c3c;\r\n");
      out.write("            --selected: #f1c40f;\r\n");
      out.write("            --aisle: #95a5a6;\r\n");
      out.write("            --road: #7f8c8d;\r\n");
      out.write("            --text-dark: #34495e;\r\n");
      out.write("            --text-light: #ecf0f1;\r\n");
      out.write("        }\r\n");
      out.write("        .parking-grid {\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            flex-direction: row;\r\n");
      out.write("            align-items: stretch; /* Cambiado a stretch */\r\n");
      out.write("            justify-content: flex-start;\r\n");
      out.write("            gap: 0;\r\n");
      out.write("            min-height: 420px; /* Ajusta según tus filas */\r\n");
      out.write("        }\r\n");
      out.write("        .parking-rows {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            flex-direction: column;\r\n");
      out.write("            gap: 10px;\r\n");
      out.write("            flex-grow: 1;\r\n");
      out.write("            align-items: stretch; /* Cambia a stretch */\r\n");
      out.write("            width: 100%;\r\n");
      out.write("        }\r\n");
      out.write("        .parking-row {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            gap: 5px;\r\n");
      out.write("            flex-wrap: wrap;\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            justify-content: center; /* Centra los spots en la fila */\r\n");
      out.write("        }\r\n");
      out.write("        .entrance-exit {\r\n");
      out.write("            width: 60px;\r\n");
      out.write("            min-width: 60px;\r\n");
      out.write("            height: 220px; /* Más corto */\r\n");
      out.write("            background-color: var(--secondary);\r\n");
      out.write("            color: white;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            writing-mode: vertical-rl;\r\n");
      out.write("            text-orientation: mixed;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("            border-radius: 12px;\r\n");
      out.write("            font-size: 1.05rem;\r\n");
      out.write("            margin-left: 18px;\r\n");
      out.write("            margin-top: 0;\r\n");
      out.write("            box-shadow: 0 2px 8px rgba(44,62,80,0.08);\r\n");
      out.write("            letter-spacing: 1px;\r\n");
      out.write("            transition: height 0.2s;\r\n");
      out.write("        }\r\n");
      out.write("        .parking-spot {\r\n");
      out.write("            width: 40px;\r\n");
      out.write("            height: 60px;\r\n");
      out.write("            flex: 1 1 40px; /* Permite que se adapten */\r\n");
      out.write("            max-width: 60px;\r\n");
      out.write("            min-width: 28px;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("            cursor: pointer;\r\n");
      out.write("            border-radius: 4px;\r\n");
      out.write("            transition: all 0.2s;\r\n");
      out.write("            position: relative;\r\n");
      out.write("            overflow: hidden;\r\n");
      out.write("            font-size: 12px;\r\n");
      out.write("        }\r\n");
      out.write("        .spot-number {\r\n");
      out.write("            position: absolute;\r\n");
      out.write("            top: 50%;\r\n");
      out.write("            left: 50%;\r\n");
      out.write("            transform: translate(-50%, -50%);\r\n");
      out.write("            font-size: 12px;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("            color: inherit;\r\n");
      out.write("        }\r\n");
      out.write("        .available {\r\n");
      out.write("            background-color: var(--available);\r\n");
      out.write("            color: var(--text-dark);\r\n");
      out.write("            cursor: not-allowed;\r\n");
      out.write("            border: 1px solid #27ae60;\r\n");
      out.write("        }\r\n");
      out.write("        .occupied {\r\n");
      out.write("            background-color: var(--occupied);\r\n");
      out.write("            color: var(--text-light);\r\n");
      out.write("            border: 1px solid #c0392b;\r\n");
      out.write("        }\r\n");
      out.write("        .selected {\r\n");
      out.write("            background-color: var(--selected);\r\n");
      out.write("            color: var(--text-dark);\r\n");
      out.write("            transform: scale(1.05);\r\n");
      out.write("            box-shadow: 0 0 0 2px var(--secondary);\r\n");
      out.write("            border: 1px solid #f39c12;\r\n");
      out.write("        }\r\n");
      out.write("        .aisle {\r\n");
      out.write("            background-color: var(--aisle);\r\n");
      out.write("            min-width: 20px;\r\n");
      out.write("            cursor: default;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            color: white;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("        }\r\n");
      out.write("        .road {\r\n");
      out.write("            background-color: var(--road);\r\n");
      out.write("            height: 40px;\r\n");
      out.write("            width: 100%;\r\n");
      out.write("            margin: 15px 0;\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            justify-content: center;\r\n");
      out.write("            font-size: 1.3rem;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("            color: #fff; /* Mejor contraste */\r\n");
      out.write("            letter-spacing: 2px;\r\n");
      out.write("            border-radius: 6px;\r\n");
      out.write("        }\r\n");
      out.write("        .controls {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            justify-content: space-between;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            margin-bottom: 20px;\r\n");
      out.write("            flex-wrap: wrap;\r\n");
      out.write("            gap: 10px;\r\n");
      out.write("        }\r\n");
      out.write("        .legend {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            gap: 15px;\r\n");
      out.write("            flex-wrap: wrap;\r\n");
      out.write("        }\r\n");
      out.write("        .legend-item {\r\n");
      out.write("            display: flex;\r\n");
      out.write("            align-items: center;\r\n");
      out.write("            gap: 5px;\r\n");
      out.write("            font-size: 14px;\r\n");
      out.write("        }\r\n");
      out.write("        .legend-color {\r\n");
      out.write("            width: 20px;\r\n");
      out.write("            height: 20px;\r\n");
      out.write("            border-radius: 3px;\r\n");
      out.write("        }\r\n");
      out.write("        button {\r\n");
      out.write("            background-color: var(--secondary);\r\n");
      out.write("            color: white;\r\n");
      out.write("            border: none;\r\n");
      out.write("            padding: 10px 20px;\r\n");
      out.write("            border-radius: 5px;\r\n");
      out.write("            cursor: pointer;\r\n");
      out.write("            font-weight: bold;\r\n");
      out.write("            transition: background-color 0.2s;\r\n");
      out.write("        }\r\n");
      out.write("        button:hover {\r\n");
      out.write("            background-color: #2980b9;\r\n");
      out.write("        }\r\n");
      out.write("        button:disabled {\r\n");
      out.write("            background-color: #95a5a6;\r\n");
      out.write("            cursor: not-allowed;\r\n");
      out.write("        }\r\n");
      out.write("        .selection-info {\r\n");
      out.write("            background-color: white;\r\n");
      out.write("            padding: 20px;\r\n");
      out.write("            border-radius: 8px;\r\n");
      out.write("            box-shadow: 0 2px 10px rgba(0,0,0,0.1);\r\n");
      out.write("            margin-top: 20px;\r\n");
      out.write("        }\r\n");
      out.write("        @media (max-width: 768px) {\r\n");
      out.write("            .parking-spot {\r\n");
      out.write("                width: 30px;\r\n");
      out.write("                height: 45px;\r\n");
      out.write("                font-size: 10px;\r\n");
      out.write("            }\r\n");
      out.write("            .spot-number {\r\n");
      out.write("                font-size: 7px;\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("        @media (max-width: 900px) {\r\n");
      out.write("            .parking-grid {\r\n");
      out.write("                flex-direction: column;\r\n");
      out.write("                align-items: stretch;\r\n");
      out.write("                min-height: unset;\r\n");
      out.write("            }\r\n");
      out.write("            .entrance-exit {\r\n");
      out.write("                width: 100%;\r\n");
      out.write("                min-width: unset;\r\n");
      out.write("                height: 50px;\r\n");
      out.write("                writing-mode: horizontal-tb;\r\n");
      out.write("                text-orientation: initial;\r\n");
      out.write("                font-size: 1rem;\r\n");
      out.write("                margin: 0 0 10px 0;\r\n");
      out.write("                border-radius: 8px;\r\n");
      out.write("                justify-content: flex-start;\r\n");
      out.write("                padding-left: 20px;\r\n");
      out.write("            }\r\n");
      out.write("            .parking-rows {\r\n");
      out.write("                align-items: stretch;\r\n");
      out.write("            }\r\n");
      out.write("            .parking-row {\r\n");
      out.write("                gap: 3px;\r\n");
      out.write("            }\r\n");
      out.write("            .parking-spot {\r\n");
      out.write("                width: 28px;\r\n");
      out.write("                height: 40px;\r\n");
      out.write("                font-size: 10px;\r\n");
      out.write("                min-width: 18px;\r\n");
      out.write("                max-width: 40px;\r\n");
      out.write("            }\r\n");
      out.write("        }\r\n");
      out.write("    </style>\r\n");
      out.write("    <div class=\"content-wrapper\">\r\n");
      out.write("        <div class=\"container-xxl flex-grow-1 container-p-y\">\r\n");
      out.write("          <div class=\"container-xxl flex-grow-1 container-p-y\">\r\n");
      out.write("            <h1 class=\"mb-4 text-center\" style=\"color: var(--primary); font-weight: bold;\">Mapa Actual del Estacionamiento</h1>\r\n");
      out.write("            <div class=\"controls mb-4\">\r\n");
      out.write("              <div class=\"legend\">\r\n");
      out.write("                <div class=\"legend-item\">\r\n");
      out.write("                  <div class=\"legend-color\" style=\"background-color: var(--available);\"></div>\r\n");
      out.write("                  <span>Disponible</span>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"legend-item\">\r\n");
      out.write("                  <div class=\"legend-color\" style=\"background-color: var(--occupied);\"></div>\r\n");
      out.write("                  <span>Ocupado</span>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"legend-item\">\r\n");
      out.write("                  <div class=\"legend-color\" style=\"background-color: var(--selected);\"></div>\r\n");
      out.write("                  <span>Seleccionado</span>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"legend-item\">\r\n");
      out.write("                  <div class=\"legend-color\" style=\"background-color: var(--aisle);\"></div>\r\n");
      out.write("                  <span>Pasillo</span>\r\n");
      out.write("                </div>\r\n");
      out.write("                <div class=\"legend-item\">\r\n");
      out.write("                  <div class=\"legend-color\" style=\"background-color: var(--road);\"></div>\r\n");
      out.write("                  <span>Carril</span>\r\n");
      out.write("                </div>\r\n");
      out.write("              </div>\r\n");
      out.write("              <button id=\"confirmBtn\" onclick=\"confirmSelection()\" disabled>Confirmar Selección</button>\r\n");
      out.write("            </div>\r\n");
      out.write("            ");
      out.write("\r\n");
      out.write("            <div class=\"parking-grid\">\r\n");
      out.write("              <div class=\"parking-rows\">\r\n");
      out.write("                ");

                  int porFila = 20;
                  // Repetimos para 3 filas
                  for (int fila = 0; fila < 3; fila++) {
                    int start = fila * porFila;
                    int end   = Math.min(start + porFila, lista.size());
                
      out.write("\r\n");
      out.write("                  <!-- Cabecera de cada carril -->\r\n");
      out.write("                  <div class=\"road\">CARRIL</div>\r\n");
      out.write("\r\n");
      out.write("                  <!-- Fila de 20 plazas -->\r\n");
      out.write("                  <div class=\"parking-row\">\r\n");
      out.write("                    ");

                      for (int j = start; j < end; j++) {
                        Estacionamiento e = lista.get(j);
                        String numero   = e.getNumero();
                        String estado   = e.getEstado();
                        String cssClass =
                            "disponible".equalsIgnoreCase(estado) ? "available" :
                            "reservado".equalsIgnoreCase(estado)    ? "occupied"  :
                                                                    "";
                        int local = j - start; // 0..19
                    
      out.write("\r\n");
      out.write("                      <div\r\n");
      out.write("                        class=\"parking-spot ");
      out.print( cssClass );
      out.write("\"\r\n");
      out.write("                        data-spot=\"");
      out.print( e.getCodEsta() );
      out.write("\"\r\n");
      out.write("                        onclick=\"liberarSpot(this)\"\r\n");
      out.write("                      >\r\n");
      out.write("                        <span class=\"spot-number\">");
      out.print( numero );
      out.write("</span>\r\n");
      out.write("                      </div>\r\n");
      out.write("\r\n");
      out.write("                      ");
      out.write("\r\n");
      out.write("                      ");
 if ((local + 1) % 5 == 0 && (local + 1) < porFila) { 
      out.write("\r\n");
      out.write("                        <div class=\"aisle\">?</div>\r\n");
      out.write("                        <div class=\"aisle\">?</div>\r\n");
      out.write("                      ");
 } 
      out.write("\r\n");
      out.write("                    ");
 } 
      out.write("\r\n");
      out.write("                  </div>\r\n");
      out.write("                ");
 } 
      out.write("\r\n");
      out.write("              </div>\r\n");
      out.write("            </div>       \r\n");
      out.write("<script>\r\n");
      out.write("  // Lleva el control de los IDs seleccionados\r\n");
      out.write("  const selectedSpots = new Set();\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("   * Se llama al clicar sobre una plaza.\r\n");
      out.write("   * @param el  El <div class=\"parking-spot\"> clicado\r\n");
      out.write("   */\r\n");
      out.write("  function liberarSpot(el) {\r\n");
      out.write("    // Solo actuamos si estaba \"occupied\" o ya \"selected\"\r\n");
      out.write("    if (!el.classList.contains('occupied') && !el.classList.contains('selected')) {\r\n");
      out.write("      return;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    // Si estaba ocupado, lo marcamos como seleccionado\r\n");
      out.write("    if (el.classList.contains('occupied')) {\r\n");
      out.write("      el.classList.replace('occupied', 'selected');\r\n");
      out.write("      selectedSpots.add(el.dataset.spot);\r\n");
      out.write("\r\n");
      out.write("    // Si ya estaba seleccionado, lo deseleccionamos\r\n");
      out.write("    } else if (el.classList.contains('selected')) {\r\n");
      out.write("      el.classList.replace('selected', 'occupied');\r\n");
      out.write("      selectedSpots.delete(el.dataset.spot);\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    // Activar o desactivar el botón de confirmación\r\n");
      out.write("    updateConfirmButton();\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("   * Habilita el botón de confirmación si hay al menos una plaza seleccionada.\r\n");
      out.write("   */\r\n");
      out.write("  function updateConfirmButton() {\r\n");
      out.write("    const btn = document.getElementById('confirmBtn');\r\n");
      out.write("    btn.disabled = selectedSpots.size === 0;\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  /**\r\n");
      out.write("   * Al clicar en \"Confirmar Selección\", libera todas las plazas seleccionadas:\r\n");
      out.write("   * 1) Pide confirmación mostrando los números,\r\n");
      out.write("   * 2) Cambia la UI a \"available\",\r\n");
      out.write("   * 3) Envía un único POST al servlet con todos los IDs seleccionados.\r\n");
      out.write("   */\r\n");
      out.write("  function confirmSelection() {\r\n");
      out.write("    // Recogemos los números de plaza para el mensaje\r\n");
      out.write("    const numeros = Array.from(selectedSpots).map(id => {\r\n");
      out.write("      const el = document.querySelector(`.parking-spot[data-spot=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${id}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\"] .spot-number`);\r\n");
      out.write("      return el ? el.textContent.trim() : id;\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    if (!confirm(`Estás seguro de que quieres liberar la plaza ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${numeros.join(', ')}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("?`)) {\r\n");
      out.write("      return;\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    // Cambiamos la UI: de selected ? available\r\n");
      out.write("    selectedSpots.forEach(id => {\r\n");
      out.write("      const spotEl = document.querySelector(`.parking-spot[data-spot=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${id}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\"]`);\r\n");
      out.write("      if (spotEl) spotEl.classList.replace('selected', 'available');\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    // Preparamos y enviamos el form con todos los IDs\r\n");
      out.write("    const form = document.createElement('form');\r\n");
      out.write("    form.method = 'post';\r\n");
      out.write("    form.action = 'EstacionamientoServlet';\r\n");
      out.write("    form.style.display = 'none';\r\n");
      out.write("\r\n");
      out.write("    // Acción para el servlet\r\n");
      out.write("    const inpAcc = document.createElement('input');\r\n");
      out.write("    inpAcc.name  = 'accion';\r\n");
      out.write("    inpAcc.value = 'liberarMultiples';\r\n");
      out.write("    form.appendChild(inpAcc);\r\n");
      out.write("\r\n");
      out.write("    // Cada ID va en un campo 'ids'\r\n");
      out.write("    selectedSpots.forEach(id => {\r\n");
      out.write("      const inp = document.createElement('input');\r\n");
      out.write("      inp.name  = 'ids';\r\n");
      out.write("      inp.value = id;\r\n");
      out.write("      form.appendChild(inp);\r\n");
      out.write("    });\r\n");
      out.write("\r\n");
      out.write("    document.body.appendChild(form);\r\n");
      out.write("    form.submit();\r\n");
      out.write("  }\r\n");
      out.write("\r\n");
      out.write("  // Al cargar la página, inicializamos el estado del botón\r\n");
      out.write("  document.addEventListener('DOMContentLoaded', updateConfirmButton);\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
