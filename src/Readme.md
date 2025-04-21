# Proyecto de Juegos de Lógica y Estrategia

## Resumen del Proyecto
Este proyecto es una aplicación de escritorio desarrollada en Java que permite a los usuarios interactuar con diferentes juegos clásicos de lógica y estrategia. Los juegos incluidos son:
- **Torres de Hanoi**: Resolver el problema de mover discos entre torres siguiendo ciertas reglas.
- **Problema del Caballo**: Resolver el problema del recorrido del caballo en un tablero de ajedrez.
- **Problema de las Ocho Reinas**: Resolver el problema de las N reinas en un tablero de ajedrez de n x n.

El proyecto guarda los datos en ***Bases de Datos*** de tipo ***CSV*** que se guardan localmente en el equipo del usuario. Los datos incluyen información sobre los juegos, como el número de movimientos realizados y el tiempo tomado para resolver cada juego.


## Estructura del Proyecto

El proyecto está organizado de la siguiente manera: 
    
- `src/main/java/org.example`: contiene el codigo fuente de la aplicación.
  - `BD`: contiene las clases que gestionan la base de datos.
  - `problemas` : contiene las clases que implementan los diferentes algoritmos que resuelven los problemas.
  - `vista`: contiene las clases que implementan la interfaz gráfica de usuario (GUI).
  - `Recursos.img`: contiene los recursos utilizados por la aplicación, como imágenes y archivos de configuración.
    

- `BaseDatos`: contiene los archivos CSV que almacenan los datos de los juegos.
  - `hanoi_moves.csv`: archivo CSV que almacena los movimientos realizados en el juego de Torres de Hanoi.
  - `Knight_moves.csv`: archivo CSV que almacena los movimientos realizados en el problema del Caballo.
  - `queen_solutions.csv`: archivo CSV que almacena las soluciones encontradas para el problema de las Ocho Reinas.
  - `game_records.csv`: archivo CSV que almacena los registros de los juegos jugados por el usuario.

## Dependencias
El proyecto utiliza Maven para la gestión de dependencias y no incluye librerías externas adicionales.