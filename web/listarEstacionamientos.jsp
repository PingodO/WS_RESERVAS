<%@ page import="java.util.*, modelo.Estacionamiento, servicio.EstacionamientoService" %>
<%
    EstacionamientoService service = new EstacionamientoService();
    List<Estacionamiento> lista = service.listar();
%>
<html>
<head><title>Lista de Estacionamientos</title></head>
<body>
    <h2>Estacionamientos Registrados</h2>
    <table border="1">
        <tr><th>ID</th><th>Número</th><th>Estado</th><th>Acciones</th></tr>
        <%
            for (Estacionamiento e : lista) {
        %>
        <tr>
            <td><%= e.getCodEsta() %></td>
            <td><%= e.getNumero() %></td>
            <%
              String cssEstado = "";
              if ("disponible".equalsIgnoreCase(e.getEstado())) {
                cssEstado = "available";
              } else if ("reservado".equalsIgnoreCase(e.getEstado())) {
                cssEstado = "occupied";
              }
            %>
            <td class="<%= cssEstado %>"><%= e.getEstado() %></td>

            <td>
              <form action="EstacionamientoServlet" method="post" style="display:inline;"
                    onsubmit="return confirm('¿Estás seguro de que quieres liberar la plaza <%= e.getNumero() %>?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="id" value="<%= e.getCodEsta() %>">
                <input type="submit" value="Eliminar">
              </form>
            </td>

        </tr>
        <% } %>
    </table>
    <a href="estacionamiento.jsp">Registrar nuevo</a>
        <style>
        :root {
            --primary: #2c3e50;
            --secondary: #3498db;
            --available: #2ecc71;
            --occupied: #e74c3c;
            --selected: #f1c40f;
            --aisle: #95a5a6;
            --road: #7f8c8d;
            --text-dark: #34495e;
            --text-light: #ecf0f1;
        }
        .parking-grid {
            width: 100%;
            display: flex;
            flex-direction: row;
            align-items: stretch; /* Cambiado a stretch */
            justify-content: flex-start;
            gap: 0;
            min-height: 420px; /* Ajusta según tus filas */
        }
        .parking-rows {
            display: flex;
            flex-direction: column;
            gap: 10px;
            flex-grow: 1;
            align-items: stretch; /* Cambia a stretch */
            width: 100%;
        }
        .parking-row {
            display: flex;
            gap: 5px;
            flex-wrap: wrap;
            width: 100%;
            justify-content: center; /* Centra los spots en la fila */
        }
        .entrance-exit {
            width: 60px;
            min-width: 60px;
            height: 220px; /* Más corto */
            background-color: var(--secondary);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            writing-mode: vertical-rl;
            text-orientation: mixed;
            font-weight: bold;
            border-radius: 12px;
            font-size: 1.05rem;
            margin-left: 18px;
            margin-top: 0;
            box-shadow: 0 2px 8px rgba(44,62,80,0.08);
            letter-spacing: 1px;
            transition: height 0.2s;
        }
        .parking-spot {
            width: 40px;
            height: 60px;
            flex: 1 1 40px; /* Permite que se adapten */
            max-width: 60px;
            min-width: 28px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            cursor: pointer;
            border-radius: 4px;
            transition: all 0.2s;
            position: relative;
            overflow: hidden;
            font-size: 12px;
        }
        .spot-number {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 12px;
            font-weight: bold;
            color: inherit;
        }
        .available {
            background-color: var(--available);
            color: var(--text-dark);
            cursor: not-allowed;
            border: 1px solid #27ae60;
        }
        .occupied {
            background-color: var(--occupied);
            color: var(--text-light);
            border: 1px solid #c0392b;
        }
        .selected {
            background-color: var(--selected);
            color: var(--text-dark);
            transform: scale(1.05);
            box-shadow: 0 0 0 2px var(--secondary);
            border: 1px solid #f39c12;
        }
        .aisle {
            background-color: var(--aisle);
            min-width: 20px;
            cursor: default;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-weight: bold;
        }
        .road {
            background-color: var(--road);
            height: 40px;
            width: 100%;
            margin: 15px 0;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.3rem;
            font-weight: bold;
            color: #fff; /* Mejor contraste */
            letter-spacing: 2px;
            border-radius: 6px;
        }
        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            flex-wrap: wrap;
            gap: 10px;
        }
        .legend {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
        }
        .legend-item {
            display: flex;
            align-items: center;
            gap: 5px;
            font-size: 14px;
        }
        .legend-color {
            width: 20px;
            height: 20px;
            border-radius: 3px;
        }
        button {
            background-color: var(--secondary);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.2s;
        }
        button:hover {
            background-color: #2980b9;
        }
        button:disabled {
            background-color: #95a5a6;
            cursor: not-allowed;
        }
        .selection-info {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        @media (max-width: 768px) {
            .parking-spot {
                width: 30px;
                height: 45px;
                font-size: 10px;
            }
            .spot-number {
                font-size: 7px;
            }
        }
        @media (max-width: 900px) {
            .parking-grid {
                flex-direction: column;
                align-items: stretch;
                min-height: unset;
            }
            .entrance-exit {
                width: 100%;
                min-width: unset;
                height: 50px;
                writing-mode: horizontal-tb;
                text-orientation: initial;
                font-size: 1rem;
                margin: 0 0 10px 0;
                border-radius: 8px;
                justify-content: flex-start;
                padding-left: 20px;
            }
            .parking-rows {
                align-items: stretch;
            }
            .parking-row {
                gap: 3px;
            }
            .parking-spot {
                width: 28px;
                height: 40px;
                font-size: 10px;
                min-width: 18px;
                max-width: 40px;
            }
        }
    </style>
    <div class="content-wrapper">
        <div class="container-xxl flex-grow-1 container-p-y">
          <div class="container-xxl flex-grow-1 container-p-y">
            <h1 class="mb-4 text-center" style="color: var(--primary); font-weight: bold;">Mapa Actual del Estacionamiento</h1>
            <div class="controls mb-4">
              <div class="legend">
                <div class="legend-item">
                  <div class="legend-color" style="background-color: var(--available);"></div>
                  <span>Disponible</span>
                </div>
                <div class="legend-item">
                  <div class="legend-color" style="background-color: var(--occupied);"></div>
                  <span>Ocupado</span>
                </div>
                <div class="legend-item">
                  <div class="legend-color" style="background-color: var(--selected);"></div>
                  <span>Seleccionado</span>
                </div>
                <div class="legend-item">
                  <div class="legend-color" style="background-color: var(--aisle);"></div>
                  <span>Pasillo</span>
                </div>
                <div class="legend-item">
                  <div class="legend-color" style="background-color: var(--road);"></div>
                  <span>Carril</span>
                </div>
              </div>
              <button id="confirmBtn" onclick="confirmSelection()" disabled>Confirmar Selección</button>
            </div>
            <%-- MAPA --%>
            <div class="parking-grid">
              <div class="parking-rows">
                <%
                  int porFila = 20;
                  // Repetimos para 3 filas
                  for (int fila = 0; fila < 3; fila++) {
                    int start = fila * porFila;
                    int end   = Math.min(start + porFila, lista.size());
                %>
                  <!-- Cabecera de cada carril -->
                  <div class="road">CARRIL</div>

                  <!-- Fila de 20 plazas -->
                  <div class="parking-row">
                    <%
                      for (int j = start; j < end; j++) {
                        Estacionamiento e = lista.get(j);
                        String numero   = e.getNumero();
                        String estado   = e.getEstado();
                        String cssClass =
                            "disponible".equalsIgnoreCase(estado) ? "available" :
                            "reservado".equalsIgnoreCase(estado)    ? "occupied"  :
                                                                    "";
                        int local = j - start; // 0..19
                    %>
                      <div
                        class="parking-spot <%= cssClass %>"
                        data-spot="<%= e.getCodEsta() %>"
                        onclick="liberarSpot(this)"
                      >
                        <span class="spot-number"><%= numero %></span>
                      </div>

                      <%-- Después de cada 5 plazas (no al final) insertamos 2 pasillos --%>
                      <% if ((local + 1) % 5 == 0 && (local + 1) < porFila) { %>
                        <div class="aisle">?</div>
                        <div class="aisle">?</div>
                      <% } %>
                    <% } %>
                  </div>
                <% } %>
              </div>
            </div>       
<script>
  // Lleva el control de los IDs seleccionados
  const selectedSpots = new Set();

  /**
   * Se llama al clicar sobre una plaza.
   * @param el  El <div class="parking-spot"> clicado
   */
  function liberarSpot(el) {
    // Solo actuamos si estaba "occupied" o ya "selected"
    if (!el.classList.contains('occupied') && !el.classList.contains('selected')) {
      return;
    }

    // Si estaba ocupado, lo marcamos como seleccionado
    if (el.classList.contains('occupied')) {
      el.classList.replace('occupied', 'selected');
      selectedSpots.add(el.dataset.spot);

    // Si ya estaba seleccionado, lo deseleccionamos
    } else if (el.classList.contains('selected')) {
      el.classList.replace('selected', 'occupied');
      selectedSpots.delete(el.dataset.spot);
    }

    // Activar o desactivar el botón de confirmación
    updateConfirmButton();
  }

  /**
   * Habilita el botón de confirmación si hay al menos una plaza seleccionada.
   */
  function updateConfirmButton() {
    const btn = document.getElementById('confirmBtn');
    btn.disabled = selectedSpots.size === 0;
  }

  /**
   * Al clicar en "Confirmar Selección", libera todas las plazas seleccionadas:
   * 1) Pide confirmación mostrando los números,
   * 2) Cambia la UI a "available",
   * 3) Envía un único POST al servlet con todos los IDs seleccionados.
   */
  function confirmSelection() {
    // Recogemos los números de plaza para el mensaje
    const numeros = Array.from(selectedSpots).map(id => {
      const el = document.querySelector(`.parking-spot[data-spot="${id}"] .spot-number`);
      return el ? el.textContent.trim() : id;
    });

    if (!confirm(`Estás seguro de que quieres liberar la plaza ${numeros.join(', ')}?`)) {
      return;
    }

    // Cambiamos la UI: de selected ? available
    selectedSpots.forEach(id => {
      const spotEl = document.querySelector(`.parking-spot[data-spot="${id}"]`);
      if (spotEl) spotEl.classList.replace('selected', 'available');
    });

    // Preparamos y enviamos el form con todos los IDs
    const form = document.createElement('form');
    form.method = 'post';
    form.action = 'EstacionamientoServlet';
    form.style.display = 'none';

    // Acción para el servlet
    const inpAcc = document.createElement('input');
    inpAcc.name  = 'accion';
    inpAcc.value = 'liberarMultiples';
    form.appendChild(inpAcc);

    // Cada ID va en un campo 'ids'
    selectedSpots.forEach(id => {
      const inp = document.createElement('input');
      inp.name  = 'ids';
      inp.value = id;
      form.appendChild(inp);
    });

    document.body.appendChild(form);
    form.submit();
  }

  // Al cargar la página, inicializamos el estado del botón
  document.addEventListener('DOMContentLoaded', updateConfirmButton);
</script>



</body>
</html>
