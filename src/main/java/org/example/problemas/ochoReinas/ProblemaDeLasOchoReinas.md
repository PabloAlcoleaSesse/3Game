### Explicación del Algoritmo para el Problema de las Ocho Reinas con Especificaciones de Métodos

A continuación, se detalla el algoritmo implementado en la clase `Reinas`, explicando los métodos utilizados y su propósito.

---

#### **1. Descripción del Problema**
El **Problema de las Ocho Reinas** consiste en colocar \(N\) reinas en un tablero de ajedrez \(N \times N\) de manera que ninguna reina pueda atacar a otra. Esto implica que:
- Ninguna reina puede compartir la misma fila, columna o diagonal.

---

#### **2. Tipo de Algoritmo**
El algoritmo utiliza **backtracking**:
- **Backtracking**: Explora todas las configuraciones posibles de manera recursiva, retrocediendo cuando se detecta un conflicto.

---

#### **3. Métodos Utilizados**

1. **`resolver(boolean todasLasSoluciones)`**:
    - **Propósito**: Punto de entrada para resolver el problema de las N reinas.
    - **Lógica**:
        - Llama al método recursivo `backtrack()` comenzando desde la primera fila.
        - Si `todasLasSoluciones` es `true`, encuentra todas las soluciones posibles; de lo contrario, se detiene al encontrar la primera solución.

   ```java
   public void resolver(boolean todasLasSoluciones) {
       backtrack(0, todasLasSoluciones);
   }
   ```

2. **`backtrack(int fila, boolean todasLasSoluciones)`**:
    - **Propósito**: Método recursivo que explora todas las posibles ubicaciones de las reinas.
    - **Lógica**:
        - Caso base: Si `fila == n`, se encuentra una solución y se guarda con `guardarSolucion()`.
        - Itera sobre todas las columnas de la fila actual.
        - Para cada columna, verifica si colocar una reina es válido usando `esMovimientoValido()`.
        - Si es válido, coloca la reina, actualiza el historial y llama recursivamente para la siguiente fila.
        - Si no se encuentra solución, retrocede eliminando la reina de la posición actual.

   ```java
   private boolean backtrack(int fila, boolean todasLasSoluciones) {
       if (fila == n) {
           guardarSolucion();
           return !todasLasSoluciones;
       }

       for (int columna = 0; columna < n; columna++) {
           if (esMovimientoValido(fila, columna)) {
               Ficha reina = new Ficha(Ficha.Tipo.REINA, fila, columna);
               tablero[fila][columna] = reina;
               historialMovimientos.add(reina);

               if (backtrack(fila + 1, todasLasSoluciones) && !todasLasSoluciones) {
                   return true;
               }

               tablero[fila][columna] = null;
               historialMovimientos.remove(historialMovimientos.size() - 1);
           }
       }
       return false;
   }
   ```

3. **`esMovimientoValido(int fila, int columna)`**:
    - **Propósito**: Valida si colocar una reina en una posición dada es seguro.
    - **Lógica**:
        - Verifica la columna para detectar reinas existentes.
        - Verifica la diagonal superior izquierda para detectar reinas existentes.
        - Verifica la diagonal superior derecha para detectar reinas existentes.

   ```java
   private boolean esMovimientoValido(int fila, int columna) {
       for (int i = 0; i < fila; i++) {
           if (tablero[i][columna] != null) return false;
       }

       for (int i = fila - 1, j = columna - 1; i >= 0 && j >= 0; i--, j--) {
           if (tablero[i][j] != null) return false;
       }

       for (int i = fila - 1, j = columna + 1; i >= 0 && j < n; i--, j++) {
           if (tablero[i][j] != null) return false;
       }

       return true;
   }
   ```

4. **`guardarSolucion()`**:
    - **Propósito**: Guarda la configuración actual de las reinas como una solución.
    - **Lógica**:
        - Crea una copia del historial de movimientos actual y la agrega a la lista de soluciones.

   ```java
   private void guardarSolucion() {
       List<Ficha> solucion = new ArrayList<>();
       for (Ficha reina : historialMovimientos) {
           solucion.add(new Ficha(Ficha.Tipo.REINA, reina.getFila(), reina.getColumna()));
       }
       soluciones.add(solucion);
   }
   ```

5. **`getSoluciones()`**:
    - **Propósito**: Devuelve la lista de todas las soluciones encontradas.
    - **Lógica**:
        - Proporciona acceso a la lista `soluciones`.

   ```java
   public List<List<Ficha>> getSoluciones() {
       return soluciones;
   }
   ```

---

#### **4. Condiciones de Éxito y Parada**
- **Éxito**: El algoritmo termina exitosamente cuando \(N\) reinas se colocan en el tablero sin conflictos.
- **Parada**: La recursión se detiene cuando todas las filas han sido procesadas (`fila == n`) o cuando no hay posiciones válidas disponibles en la fila actual.

---

#### **5. Ventajas y Limitaciones**
- **Ventajas**:
    - El backtracking garantiza que se exploren todas las soluciones posibles si es necesario.
    - La validación eficiente reduce las configuraciones innecesarias.
- **Limitaciones**:
    - Costoso computacionalmente para valores grandes de \(N\) debido al crecimiento exponencial de posibilidades.

Esta explicación detalla cómo los métodos trabajan en conjunto para resolver el problema de las N reinas de manera eficiente y estructurada.