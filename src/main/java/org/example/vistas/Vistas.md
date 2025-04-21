### Documentación de las Vistas del Proyecto

A continuación, se detalla la estructura y funcionalidad de las vistas principales del proyecto, organizadas según los puntos solicitados.

---

#### 1. **Clase: `mi_ventana`**
- **Propósito**:  
  Es la ventana principal de la aplicación. Actúa como un contenedor para todas las vistas (paneles) y gestiona la navegación entre ellas utilizando un `CardLayout`.

- **Componentes utilizados**:
    - **Botones (`JButton`)**:
        - "Torres de Hanoi", "Caballo", "Reinas", "Mostrar registros del juego".
    - **Etiquetas (`JLabel`)**:
        - Título "Inicio".
    - **Paneles (`JPanel`)**:
        - Panel principal con `CardLayout` y panel de inicio con botones de navegación.
    - **Diseño (`CardLayout`, `BorderLayout`, `GridBagLayout`)**:
        - `CardLayout` para cambiar entre vistas.
        - `BorderLayout` para organizar el panel de inicio.
        - `GridBagLayout` para alinear los botones en el centro.

- **Eventos o acciones asociadas**:
    - Cada botón tiene un `ActionListener` que cambia la vista actual al panel correspondiente (por ejemplo, `panelHanoi`, `panelCaballo`, etc.).
    - Solicita datos al usuario mediante `JOptionPane` para configurar los parámetros iniciales de cada juego (como el tamaño del tablero o el número de discos).

- **Interacción con otras clases**:
    - Crea instancias de los paneles de los juegos (`panelHanoi`, `panelCaballo`, `panelReinas`) y del historial (`PanelHistorial`).
    - Navega entre vistas utilizando el `CardLayout`.

---

#### 2. **Clase: `panelReinas`**
- **Propósito**:  
  Implementa la lógica y la interfaz gráfica para resolver el problema de las N reinas. Permite al usuario visualizar soluciones en un tablero gráfico.

- **Componentes utilizados**:
    - **Paneles (`JPanel`)**:
        - Tablero (`boardPanel`) con un `GridLayout` para representar el tablero de ajedrez.
        - Área de texto (`JTextArea`) para mostrar las soluciones.
    - **Botones (`JButton`)**:
        - "Solve N-Queens", "Next Solution", "Back to Menu".
    - **Etiquetas (`JLabel`)**:
        - Celdas del tablero.
    - **Diseño (`BorderLayout`, `GridLayout`)**:
        - `BorderLayout` para organizar el tablero, área de texto y botones.
        - `GridLayout` para el tablero de ajedrez.

- **Eventos o acciones asociadas**:
    - Botón "Solve N-Queens": Llama al algoritmo de resolución y muestra la primera solución.
    - Botón "Next Solution": Navega entre las soluciones disponibles.
    - Botón "Back to Menu": Regresa al menú principal.

- **Interacción con otras clases**:
    - Utiliza la clase `Reinas` para resolver el problema.
    - Guarda los resultados en la base de datos mediante la clase `BaseDeDatos`.

---

#### 3. **Clase: `panelCaballo`**
- **Propósito**:  
  Implementa la lógica y la interfaz gráfica para resolver el problema del recorrido del caballo en un tablero de ajedrez.

- **Componentes utilizados**:
    - **Paneles (`JPanel`)**:
        - Tablero (`boardPanel`) con un `GridLayout` para representar el tablero.
        - Área de texto (`JTextArea`) para mostrar el historial de movimientos.
    - **Botones (`JButton`)**:
        - "Solve Knight's Tour", "Back to Menu".
    - **Etiquetas (`JLabel`)**:
        - Celdas del tablero.
    - **Diseño (`BorderLayout`, `GridLayout`)**:
        - `BorderLayout` para organizar el tablero, área de texto y botones.
        - `GridLayout` para el tablero de ajedrez.

- **Eventos o acciones asociadas**:
    - Botón "Solve Knight's Tour": Llama al algoritmo de resolución y muestra el recorrido del caballo paso a paso.
    - Botón "Back to Menu": Regresa al menú principal.

- **Interacción con otras clases**:
    - Utiliza la clase `caballo` para resolver el problema.
    - Guarda los resultados en la base de datos mediante la clase `BaseDeDatos`.

---

#### 4. **Clase: `panelHanoi`**
- **Propósito**:  
  Implementa la lógica y la interfaz gráfica para resolver el problema de las Torres de Hanoi. Permite al usuario visualizar los movimientos de los discos entre las torres.

- **Componentes utilizados**:
    - **Paneles (`JPanel`)**:
        - Torres (`towersPanel`) con un `GridLayout` para representar las tres torres.
        - Área de texto (`JTextArea`) para mostrar el historial de movimientos.
    - **Botones (`JButton`)**:
        - "Solve Towers of Hanoi", "Back to Menu".
    - **Diseño (`BorderLayout`, `GridLayout`, `BoxLayout`)**:
        - `BorderLayout` para organizar las torres, área de texto y botones.
        - `GridLayout` para las torres.
        - `BoxLayout` para apilar los discos en cada torre.

- **Eventos o acciones asociadas**:
    - Botón "Solve Towers of Hanoi": Llama al algoritmo de resolución y anima los movimientos de los discos.
    - Botón "Back to Menu": Regresa al menú principal.

- **Interacción con otras clases**:
    - Utiliza la clase `Torres` para resolver el problema.
    - Guarda los resultados en la base de datos mediante la clase `BaseDeDatos`.

---

#### 5. **Clase: `PanelHistorial`**
- **Propósito**:  
  Muestra un historial de los juegos realizados, incluyendo información como el tipo de problema, tamaño, solución y fecha.

- **Componentes utilizados**:
    - **Tabla (`JTable`)**:
        - Muestra los registros del historial.
    - **Paneles (`JPanel`)**:
        - Panel de filtros y tabla.
    - **Botones (`JButton`)**:
        - "Volver".
    - **ComboBox (`JComboBox`)**:
        - Filtro para seleccionar el tipo de problema.
    - **Diseño (`BorderLayout`, `FlowLayout`)**:
        - `BorderLayout` para organizar la tabla y los filtros.
        - `FlowLayout` para el panel de filtros.

- **Eventos o acciones asociadas**:
    - Filtro (`JComboBox`): Filtra los registros según el tipo de problema seleccionado.
    - Botón "Volver": Regresa al menú principal.

- **Interacción con otras clases**:
    - Utiliza la clase `MostrarBD` para obtener los datos del historial desde la base de datos.

---

Esta documentación proporciona una visión clara y estructurada de las vistas del proyecto, facilitando su mantenimiento y ampliación.