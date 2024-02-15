# RETO 3 APIs REST

### Miembros del Equipo

- [Luis Alvarado Zambrano](https://github.com/LuisAlvaradoDXC)
- [Pavlo Dudnyk Petrenko](https://github.com/PABLOKUMAR1993)

## Diseño de la API

    El proyecto ha sido diseñado y planteado en Figma, se puede ver aquí:

    https://www.figma.com/file/wLLSFEKoRy2sAO287TOgEP/Untitled?type=whiteboard&node-id=0%3A1&t=tYWV1kxgXl38bemu-1

    Toda la información referente a los métodos del RestController de Account están ahí con capturas de las posibles
    respuestas y excepciones que puede generar el programa.

## Objetivos del Proyecto

### Reto 2.0 - Crea un proyecto spring boot para el servicio de cuentas (Accounts)
    • Carga la base para los modelos/entidades:
    • Account: id, type, balance, owner (Customer)
    • Customer: id, name, email
    • Define un controlador REST para Accountt que escuche todas las peticions en la ruta /accounts
    • Define las propiedades del proyecto (yaml)
    • Añade los perfiles dev y prod en los puertos 9900 y 9943 respectivamente.
    • Asimismo, haz que dev use H2 y prod MySql en la base de datos accounts_db.
    • Bonus: Añade un filtro al proyecto que permita capturar logs del controlador de destino (opcional).

### Reto 3.1 - Diseña la API para el servicio Accounts
    • La api debe permitir:
        • Listar (todas y de manera individual), crear, actualizar, borrar cuentas.
        • Añadir dinero al balance, indicando cuenta, cantidad y propietario.
        • Hacer un retiro, indicando cuenta, cantidad y propietario.
        • Borrar todas las cuentas de un usuario.
    • Define los recursos y sus ids.
    • Define los métodos pertinentes para los recursos.
    • Define el negociado de representación.

### Reto 3.2 - Crea el servicio Accounts
    • Implementa los endpoints de la API
    • Añade las validaciones de los datos
