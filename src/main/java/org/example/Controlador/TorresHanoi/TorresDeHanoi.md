### Explicación del Algoritmo para el Problema de las Torres de Hanói con Especificaciones de Métodos

A continuación, se detalla el algoritmo implementado en la clase `Torres`, explicando los métodos utilizados y su propósito.

---

#### **1. Descripción del Problema**
El **Problema de las Torres de Hanói** consiste en mover \(N\) discos de una torre de origen a una torre de destino, utilizando una torre auxiliar, cumpliendo las siguientes reglas:
- Solo se puede mover un disco a la vez.
- Un disco más grande no puede colocarse sobre uno más pequeño.
- Todos los discos deben comenzar en la torre de origen y terminar en la torre de destino.

---

#### **2. Tipo de Algoritmo**
El algoritmo utiliza un enfoque **recursivo**:
- Divide el problema en subproblemas más pequeños, moviendo \(N-1\) discos entre las torres hasta que el disco más grande pueda ser movido directamente.

---

#### **3. Métodos Utilizados**

1. **`Torres(int numDiscos)`**:
    - **Propósito**: Constructor que inicializa las torres y coloca los discos en la torre de origen.
    - **Lógica**:
        - Crea tres torres representadas como pilas (`Stack`).
        - Llena la torre de origen con los discos en orden descendente de tamaño.

   ```java
   public Torres(int numDiscos) {
       this.numDiscos = numDiscos;
       this.movimientos = new ArrayList<>();
       this.movimientosFichas = new ArrayList<>();

       // Inicializar las tres torres
       this.torres = new Stack[3];
       for (int i = 0; i < 3; i++) {
           torres[i] = new Stack<>();
       }

       // Colocar los discos en la torre de origen
       for (int i = numDiscos; i >= 1; i--) {
           torres[0].push(new Ficha(i));
       }
   }
   ```

2. **`resolver()`**:
    - **Propósito**: Método principal para resolver el problema de las Torres de Hanói.
    - **Lógica**:
        - Limpia los movimientos previos y reinicia las torres.
        - Llama al método recursivo `moverDiscos()` para resolver el problema.

   ```java
   public void resolver() {
       movimientos.clear();
       movimientosFichas.clear();

       // Reiniciar las torres
       for (int i = 0; i < 3; i++) {
           torres[i].clear();
       }

       // Colocar los discos en la torre de origen
       for (int i = numDiscos; i >= 1; i--) {
           torres[0].push(new Ficha(i));
       }

       moverDiscos(numDiscos, 0, 2, 1);
   }
   ```

3. **`moverDiscos(int n, int origen, int destino, int auxiliar)`**:
    - **Propósito**: Método recursivo que mueve los discos entre las torres.
    - **Lógica**:
        - Caso base: Si \(n = 1\), mueve un disco directamente de la torre de origen a la torre de destino.
        - Caso recursivo: Mueve \(n-1\) discos de la torre de origen a la torre auxiliar, luego mueve el disco más grande a la torre de destino, y finalmente mueve los \(n-1\) discos de la torre auxiliar a la torre de destino.

   ```java
   private void moverDiscos(int n, int origen, int destino, int auxiliar) {
       if (n == 1) {
           // Mover disco y registrar el movimiento
           Ficha disco = torres[origen].pop();
           torres[destino].push(disco);

           // Representación textual del movimiento
           char torreOrigen = (char)('A' + origen);
           char torreDestino = (char)('A' + destino);
           movimientos.add("Mover disco " + disco.getTamano() + " de " + torreOrigen + " a " + torreDestino);

           // Registrar el movimiento como objeto Ficha
           movimientosFichas.add(disco);
       } else {
           moverDiscos(n - 1, origen, auxiliar, destino);
           moverDiscos(1, origen, destino, auxiliar);
           moverDiscos(n - 1, auxiliar, destino, origen);
       }
   }
   ```

4. **`getMovimientos()`**:
    - **Propósito**: Devuelve una lista de los movimientos realizados en formato de texto.
    - **Lógica**:
        - Proporciona una lista de cadenas que describen cada movimiento.

   ```java
   public List<String> getMovimientos() {
       return movimientos;
   }
   ```

5. **`getMovimientosFichas()`**:
    - **Propósito**: Devuelve una lista de los movimientos realizados como objetos `Ficha`.
    - **Lógica**:
        - Proporciona una lista de objetos `Ficha` que representan los discos movidos.

   ```java
   public List<Ficha> getMovimientosFichas() {
       return movimientosFichas;
   }
   ```

---

#### **4. Condiciones de Éxito y Parada**
- **Éxito**: El algoritmo termina exitosamente cuando todos los discos se encuentran en la torre de destino en el orden correcto.
- **Parada**: La recursión se detiene cuando \(n = 1\), moviendo el disco más pequeño directamente.

---

#### **5. Ventajas y Limitaciones**
- **Ventajas**:
    - La solución recursiva es elegante y fácil de entender.
    - Garantiza la solución óptima en \(2^n - 1\) movimientos.
- **Limitaciones**:
    - La recursión puede ser costosa en términos de memoria para valores grandes de \(n\).

Esta explicación detalla cómo los métodos trabajan en conjunto para resolver el problema de las Torres de Hanói de manera eficiente y estructurada.