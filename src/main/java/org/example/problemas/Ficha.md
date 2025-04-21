### Documentación Técnica: Clase `Ficha`

#### 1. Propósito General
La clase `Ficha` actúa como una representación genérica de las piezas utilizadas en tres algoritmos clásicos: Torre de Hanói, el problema del caballo (Knight’s Tour) y el problema de las ocho reinas. Su diseño flexible permite modelar diferentes tipos de piezas (discos, caballos, reinas) con atributos y comportamientos específicos para cada problema, manteniendo una estructura común que facilita la reutilización y la abstracción.

---

#### 2. Atributos y Significado
La clase `Ficha` contiene los siguientes atributos:

- **`Tipo tipo`**: Enum que identifica el tipo de ficha (`CABALLO`, `REINA`, `DISCO`), permitiendo diferenciar su uso en los distintos algoritmos.
- **`int fila` y `int columna`**: Coordenadas en un tablero (para los problemas del caballo y las reinas). Representan la posición de la ficha.
- **`int tamano`**: Tamaño del disco (para el problema de las Torres de Hanói). Es relevante para las reglas de apilamiento.
- **Métodos `getters` y `setters`**: Proveen acceso y modificación controlada de los atributos.

---

#### 3. Métodos Clave
La clase `Ficha` incluye los siguientes métodos principales:

- **Constructor para problemas de tablero (`CABALLO` y `REINA`)**:
  ```java
  public Ficha(Tipo tipo, int fila, int columna)
  ```
  Crea una ficha con un tipo específico y posición en el tablero.

- **Constructor para discos (`DISCO`)**:
  ```java
  public Ficha(int tamano)
  ```
  Crea un disco con un tamaño específico para las Torres de Hanói.

- **`toString()`**:
  Proporciona una representación textual de la ficha, útil para depuración y visualización.

---

#### 4. Comportamiento Específico Según el Algoritmo

##### **4.1. Torre de Hanói**
- **Representación**:  
  Cada disco es una instancia de `Ficha` con el tipo `DISCO` y un atributo `tamano` que define su tamaño.
- **Reglas de apilamiento**:  
  El tamaño del disco se utiliza para validar movimientos, asegurando que un disco más grande no se apile sobre uno más pequeño.
- **Ejemplo de uso**:
  ```java
  Ficha disco = new Ficha(3); // Disco de tamaño 3
  torre.push(disco); // Apilar disco en una torre
  ```

##### **4.2. Problema del Caballo**
- **Representación**:  
  El caballo es una ficha con el tipo `CABALLO` y atributos `fila` y `columna` que indican su posición en el tablero.
- **Movimientos posibles**:  
  Los movimientos del caballo se calculan en la clase `caballo`, pero las instancias de `Ficha` se utilizan para registrar las posiciones visitadas.
- **Ejemplo de uso**:
  ```java
  Ficha caballo = new Ficha(Ficha.Tipo.CABALLO, 0, 0); // Caballo en la posición inicial
  tablero[0][0] = caballo; // Colocar el caballo en el tablero
  ```

##### **4.3. Problema de las Ocho Reinas**
- **Representación**:  
  Cada reina es una ficha con el tipo `REINA` y atributos `fila` y `columna` que indican su posición en el tablero.
- **Validación de ataques**:  
  Las posiciones de las reinas se validan en la clase `Reinas`, pero las instancias de `Ficha` representan las reinas colocadas en el tablero.
- **Ejemplo de uso**:
  ```java
  Ficha reina = new Ficha(Ficha.Tipo.REINA, 2, 3); // Reina en la posición (2, 3)
  tablero[2][3] = reina; // Colocar la reina en el tablero
  ```

---

#### 5. Ventajas del Uso de una Clase Común
- **Reutilización**:  
  La clase `Ficha` se utiliza en los tres algoritmos, evitando duplicación de código y facilitando el mantenimiento.
- **Abstracción**:  
  Proporciona una interfaz común para representar piezas, independientemente del problema.
- **Flexibilidad**:  
  Permite extender el proyecto para incluir nuevos algoritmos que utilicen piezas similares, simplemente añadiendo nuevos tipos al enum `Tipo`.

---

#### 6. Ejemplo de Uso en Cada Algoritmo

##### **Torre de Hanói**
```java
Ficha disco = new Ficha(3); // Crear un disco de tamaño 3
torres[0].push(disco); // Apilar disco en la torre inicial
```

##### **Problema del Caballo**
```java
Ficha caballo = new Ficha(Ficha.Tipo.CABALLO, 0, 0); // Caballo en la posición inicial
tablero[0][0] = caballo; // Colocar el caballo en el tablero
```

##### **Problema de las Ocho Reinas**
```java
Ficha reina = new Ficha(Ficha.Tipo.REINA, 4, 2); // Reina en la posición (4, 2)
tablero[4][2] = reina; // Colocar la reina en el tablero
```

---

Esta documentación detalla cómo la clase `Ficha` centraliza la representación de piezas en los tres algoritmos, destacando su diseño reutilizable y flexible.