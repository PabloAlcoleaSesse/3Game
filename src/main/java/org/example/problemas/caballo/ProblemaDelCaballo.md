### Explicación del Algoritmo del Problema del Caballo con Especificaciones de Métodos

A continuación, se detalla el algoritmo implementado en la clase `caballo`, explicando los métodos utilizados y su propósito.

---

#### **1. Descripción del Problema**
El **Problema del Caballo** (Knight’s Tour) consiste en encontrar un recorrido en un tablero de ajedrez \(N \times N\) donde un caballo visite todas las casillas exactamente una vez. El caballo se mueve en forma de "L" (dos casillas en una dirección y una en perpendicular, o viceversa).

---

#### **2. Tipo de Algoritmo**
El algoritmo utiliza **backtracking** combinado con la **heurística de Warnsdorff**:
- **Backtracking**: Explora todas las posibles soluciones de manera recursiva, retrocediendo cuando no hay movimientos válidos.
- **Warnsdorff**: Prioriza los movimientos con menos opciones futuras, reduciendo el espacio de búsqueda.

---

#### **3. Métodos Utilizados**

1. **`solve()`**:
    - **Propósito**: Punto de entrada para resolver el problema del caballo.
    - **Lógica**:
        - Inicializa el tablero y coloca el caballo en la posición inicial (\(0, 0\)).
        - Llama al método recursivo `solveUtil()` para intentar resolver el problema.
        - Limpia el tablero y el historial si no se encuentra solución.

   ```java
   public boolean solve() {
       Ficha start = new Ficha(Ficha.Tipo.CABALLO, 0, 0);
       board[0][0] = start;
       moveHistory.clear();

       if (solveUtil(0, 0, 1)) {
           return true;
       } else {
           board[0][0] = null;
           moveHistory.clear();
           return false;
       }
   }
   ```

2. **`solveUtil(int x, int y, int moveCount)`**:
    - **Propósito**: Método recursivo que explora todos los movimientos posibles.
    - **Lógica**:
        - Caso base: Si `moveCount` es igual a \(N \times N\), el recorrido está completo.
        - Genera una lista de movimientos posibles usando `getMoveOrder()`.
        - Itera sobre los movimientos, validando cada uno con `isValidMove()`.
        - Si un movimiento es válido, coloca el caballo, actualiza el historial y llama recursivamente a sí mismo.
        - Si no se encuentra solución, retrocede eliminando el caballo de la posición actual.

   ```java
   private boolean solveUtil(int x, int y, int moveCount) {
       if (moveCount == N * N) {
           return true;
       }

       int[] moveOrder = getMoveOrder(x, y);
       for (int i : moveOrder) {
           int nextX = x + dx[i];
           int nextY = y + dy[i];

           if (isValidMove(nextX, nextY)) {
               board[nextX][nextY] = new Ficha(Ficha.Tipo.CABALLO, nextX, nextY);
               moveHistory.add(new Ficha(Ficha.Tipo.CABALLO, nextX, nextY));

               if (solveUtil(nextX, nextY, moveCount + 1)) {
                   return true;
               }

               board[nextX][nextY] = null;
               moveHistory.remove(moveHistory.size() - 1);
           }
       }
       return false;
   }
   ```

3. **`getMoveOrder(int x, int y)`**:
    - **Propósito**: Implementa la heurística de Warnsdorff ordenando los movimientos según el número de opciones futuras.
    - **Lógica**:
        - Calcula el grado (número de movimientos válidos) para cada movimiento posible.
        - Ordena los movimientos en orden ascendente de grado.

   ```java
   private int[] getMoveOrder(int x, int y) {
       int[] moveOrder = new int[8];
       int[] degree = new int[8];

       for (int i = 0; i < 8; i++) {
           int nextX = x + dx[i];
           int nextY = y + dy[i];
           degree[i] = countValidMoves(nextX, nextY);
           moveOrder[i] = i;
       }

       for (int i = 0; i < 8; i++) {
           for (int j = i + 1; j < 8; j++) {
               if (degree[moveOrder[i]] > degree[moveOrder[j]]) {
                   int temp = moveOrder[i];
                   moveOrder[i] = moveOrder[j];
                   moveOrder[j] = temp;
               }
           }
       }

       return moveOrder;
   }
   ```

4. **`countValidMoves(int x, int y)`**:
    - **Propósito**: Cuenta el número de movimientos válidos desde una posición dada.
    - **Lógica**:
        - Itera sobre todos los movimientos posibles y verifica su validez usando `isValidMove()`.

   ```java
   private int countValidMoves(int x, int y) {
       int count = 0;
       for (int i = 0; i < 8; i++) {
           int nextX = x + dx[i];
           int nextY = y + dy[i];
           if (isValidMove(nextX, nextY)) {
               count++;
           }
       }
       return count;
   }
   ```

5. **`isValidMove(int x, int y)`**:
    - **Propósito**: Valida si un movimiento está dentro de los límites y si la casilla destino no ha sido visitada.
    - **Lógica**:
        - Verifica que las coordenadas estén dentro del tablero y que la casilla esté vacía.

   ```java
   private boolean isValidMove(int x, int y) {
       return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == null);
   }
   ```

6. **`getMoveHistory()`**:
    - **Propósito**: Devuelve el historial de movimientos realizados por el caballo.
    - **Lógica**:
        - Proporciona una lista de objetos `Ficha` que representan el recorrido del caballo.

   ```java
   public List<Ficha> getMoveHistory() {
       return moveHistory;
   }
   ```

---

#### **4. Condiciones de Éxito y Parada**
- **Éxito**: El algoritmo termina exitosamente cuando todas las casillas (\(N \times N\)) han sido visitadas (`moveCount == N * N`).
- **Parada**: La recursión se detiene cuando no hay movimientos válidos disponibles y el algoritmo retrocede.

---

#### **5. Ventajas y Limitaciones**
- **Ventajas**:
    - La heurística de Warnsdorff reduce significativamente el espacio de búsqueda.
    - El backtracking garantiza que se exploren todas las soluciones posibles si es necesario.
- **Limitaciones**:
    - Costoso computacionalmente para tableros grandes debido al crecimiento exponencial de posibilidades.
    - Puede fallar en encontrar una solución si la heurística no es efectiva en ciertas configuraciones.

Esta explicación detalla cómo los métodos trabajan en conjunto para resolver el problema del caballo de manera eficiente y estructurada.