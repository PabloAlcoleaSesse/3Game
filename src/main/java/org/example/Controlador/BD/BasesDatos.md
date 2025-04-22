### Documentación sobre el uso de archivos CSV como base de datos en un proyecto Java

#### 1. Propósito de usar CSV como almacenamiento de datos:
- **Simplicidad del sistema**: Los archivos CSV son fáciles de entender y manipular, tanto para desarrolladores como para usuarios.
- **No se requiere base de datos externa**: No es necesario instalar ni configurar un sistema de gestión de bases de datos (DBMS) como MySQL o PostgreSQL.
- **Portabilidad de los datos**: Los archivos CSV son altamente portables y pueden ser abiertos en cualquier editor de texto o software como Excel.

#### 2. Estructura del archivo CSV:
- **Columnas**: Cada columna representa un atributo de los datos. Por ejemplo:
    - `ID`: Identificador único.
    - `Nombre`: Nombre del objeto o entidad.
    - `Edad`: Edad o valor numérico.
    - `Puntaje`: Un valor asociado.
- **Filas**: Cada fila representa un registro o entidad.
- **Ejemplo**:
  ```csv
  ID,Nombre,Edad,Puntaje
  1,Juan,25,85
  2,Ana,30,90
  ```

#### 3. Clases implicadas en la lectura/escritura de datos CSV:
- **`CSVManager`**:
    - **Responsabilidad**: Manejar la lectura y escritura de archivos CSV.
    - Métodos principales: `guardarRegistro()`, `leerRegistros()`.
- **`DataService`**:
    - **Responsabilidad**: Proveer una capa de servicio para interactuar con los datos, utilizando `CSVManager`.
    - Métodos principales: `guardarDatos()`, `mostrarDatos()`.

#### 4. Métodos para guardar información:
- **Nombre del método**: `guardarRegistroEnCSV(Object objeto)`
- **Parámetros**:
    - `objeto`: Un objeto que representa el registro a guardar.
- **Paso a paso**:
    1. Abrir el archivo CSV en modo de escritura (append).
    2. Convertir el objeto en una línea de texto CSV.
    3. Escribir la línea en el archivo.
    4. Cerrar el archivo.
- **Evitar duplicados**:
    - Leer el archivo antes de escribir y verificar si el registro ya existe.
- **Ejemplo de implementación**:
  ```java
  public void guardarRegistroEnCSV(String filePath, String[] datos) throws IOException {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
          String linea = String.join(",", datos);
          writer.write(linea);
          writer.newLine();
      }
  }
  ```

#### 5. Métodos para mostrar información:
- **Nombre del método**: `mostrarRegistros()`
- **Cómo lee el archivo**:
    - Utiliza `BufferedReader` para leer línea por línea.
- **Conversión a objetos Java**:
    - Divide cada línea en columnas usando `String.split(",")`.
    - Crea un objeto con los valores obtenidos.
- **Mostrar información**:
    - Imprime en consola o utiliza una interfaz gráfica (Swing, JavaFX).
- **Ejemplo de implementación**:
  ```java
  public List<String[]> leerRegistrosDeCSV(String filePath) throws IOException {
      List<String[]> registros = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
          String linea;
          while ((linea = reader.readLine()) != null) {
              String[] datos = linea.split(",");
              registros.add(datos);
          }
      }
      return registros;
  }
  ```

#### 6. Manejo de errores y validación de datos:
- **Líneas mal formateadas**:
    - Ignorar líneas con un número incorrecto de columnas.
- **Campos vacíos**:
    - Validar cada campo antes de procesarlo.
- **Gestión de excepciones**:
    - Capturar `IOException` para errores de archivo.
    - Capturar `NumberFormatException` para conversiones numéricas.
- **Ejemplo**:
  ```java
  try {
      List<String[]> registros = leerRegistrosDeCSV("datos.csv");
  } catch (IOException e) {
      System.err.println("Error al leer el archivo: " + e.getMessage());
  }
  ```

#### 7. Ejemplo de uso en el flujo de la aplicación:
1. **Inicio del programa**:
    - La clase principal llama a `mostrarRegistros()` para cargar y mostrar los datos existentes.
2. **Guardar datos**:
    - Cuando el usuario introduce nuevos datos, se llama a `guardarRegistroEnCSV()`.
3. **Mostrar datos actualizados**:
    - Después de guardar, se llama nuevamente a `mostrarRegistros()`.

#### 8. Ventajas y limitaciones del enfoque actual con CSV:
- **Ventajas**:
    - Fácil de implementar y mantener.
    - No requiere dependencias externas.
    - Ideal para proyectos pequeños o prototipos.
- **Limitaciones**:
    - No es eficiente para grandes volúmenes de datos.
    - Carece de soporte para consultas complejas.
    - No garantiza la integridad de los datos (sin transacciones).
- **Escalabilidad**:
    - Si el proyecto crece, se puede migrar a una base de datos relacional (MySQL, PostgreSQL) o NoSQL (MongoDB) reutilizando la lógica de negocio.