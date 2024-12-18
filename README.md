## web grammar analyzer

Como parte del curso de compiladores de 2021, construí un analizador web para manejar gramáticas recursivas por la izquierda y remover su recursividad por la izquierda, siguiendo  el teorema descrito en la página 212 del libro de compiladores «Compiladores, principios, técnicas y herramientas»

```sh
A  ->  A α | β

A  -> β A'
A' -> α A' | ε 
```

## Caracteristicas

- Soporte de sintaxis de asignación de variables
- Elimincación de recursividad por la izquierda
- Generación de variables, terminales, producciones
- Funcion primera y funcion siguiente

## Stack utilizado

- Java 17 (OpenJDK)
- Spring Boot (2.4.4)
- VueJS 2

## Ejecución 

```sh
mvn clean install
```

## Vista Principal
