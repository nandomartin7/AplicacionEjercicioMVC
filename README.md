# Aplicación Ejercicio MVC - CRUD de Contratos

## Descripción

Este proyecto es una aplicación web desarrollada utilizando Spring Boot para el backend y Vue.js para el frontend. La aplicación permite gestionar un CRUD (Crear, Leer, Actualizar, Eliminar) de contratos, con funciones de autenticación para los empleados. Además, incluye una la aplicacion de un ejercicio mvc de funcionalidad para buscar contratos dentro de un rango de fechas y contabilizar los contratos ingresados por cada empleado.

## Tecnologías Utilizadas

- **Backend**: Spring Boot, Java (version 17), MySQL (Base de Datos)
- **Frontend**: Vue.js
- **Despliegue**: Railway (Backend), Vercel (Frontend)
- **Control de versiones**: Git y GitHub

## Funcionalidades Principales

- **CRUD de contratos**: Los empleados pueden crear, ver, actualizar y eliminar contratos.
- **Autenticación**: Solo usuarios registrados pueden acceder a las funcionalidades de la aplicación.
- **Búsqueda de contratos por rango de fechas**: El empleado puede buscar contratos entre fechas específicas.
- **Conteo de contratos por empleado**: Función que permite saber cuántos contratos ha ingresado cada empleado en un período determinado.

## Guia del Básica del Backend
- **Modelos**: Contamos con 2 modelos empleado y contrato, por la parte de contrato se relaciona con el empleado atravez del idEmpleado.
- **Servicios**: Contamos con 2 servicios EmpleadoSerivce y ContratoService, aquí se enceuntra el desarrollo de todas las funcionalidades para empleado y contrato.
- **Repositorios**: Se utiliza para la comunicacion con la base de datos MySQL
- **Controladores**: Contamos con 3:  
  - **EmpleadoController**: Se crean los endpoints y se comunica con el servicio de empleado para operaciones de login y registro
  - **ContratoController**: Se crean los endpoints y se comunica con el servicio de contrato para las operaciones CRUD
  - **ReporteController**: Se crea el endpoint y se comunica con el servicio de contratos y empleados para hacer el filtrado por fechas
- **Configuraciones**: Aquí tenemos la configuracion del CORS en WebConfig y en SecurityConfig el control de acceso
- **ApplicationProperties** : Esta configurada la conexion a la base de datos MySQL

## Guia del Básica del Frontend
- **Components**: Contamos con 4 paginas.
  - Index.vue: Pagina inicial con el login y registro de empleados
  - ListaContratos.vue: Pagina principal con las operaciones CRUD de contrato
  - FormularioContrato.vue: Pagina para agregar un nuevo contrato
  - BuscarContratos.vue: Pagina para filtrar los contratos por fechas

- **index.js**: Gestión de las rutas del la pagina web.

- **vercel.json**: Asignacion de la pagina inical para que Vercel funcione sin problema

 
## Despliegue del Proyecto

El proyecto está desplegado en las siguientes plataformas:

- **Frontend**: [Vercel – Acceder a la aplicación](#)

- **Backend**: [Railway – Repositorio del backend](#)

Link de accedo a la pagina web desplegada: https://aplicacion-ejercicio-mvc.vercel.app

## Repositorios del Proyecto

- **Repositorio principal del proyecto**: https://github.com/nandomartin7/AplicacionEjercicioMVC.git
- **Repositorio del backend**: https://github.com/nandomartin7/AplicacionEjercicioMVCBackend.git
- **Repositorio del frontend**: https://github.com/nandomartin7/AplicacionEjercicioMVCFrontend.git

## Despliegue en Railway y Vercel

Para desplegar el proyecto, se siguieron los siguientes tutoriales:

- **Desplegar Frontend en Vercel**: https://www.youtube.com/watch?v=Ez2VnA2OaII
- **Desplegar Backend en Railway**: https://www.youtube.com/watch?v=UsbqrZueoro

## Inspiración y Ejemplos Utilizados

Este proyecto se basó en otros proyectos de ejemplo para guiar su desarrollo:

- **Proyecto de Mateo Encalada Minicore_Springboot**: [Ver ejemplo](https://udlaec-my.sharepoint.com/:v:/g/personal/juan_leon_udla_edu_ec/ESdNYEkE6cVJlwjrAqLhNEcBW-9oX0JbOZT6mRyTiykpqQ?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=TuiLsv)
- **Proyecto de Domenica Rueda MIniCore Spring**: [Ver ejemplo](https://udlaec-my.sharepoint.com/:v:/g/personal/juan_leon_udla_edu_ec/EQSxLCQvYSdIgxKFWn_zGVMBTIDb2_T9sJ2kN-DbHWXRjw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=wkZsNE)

## Video Demostrativo

Puedes ver una demostración del proyecto en el siguiente video:  
https://youtu.be/E8J3tpuC854
 
## Instalación y Configuración

### Backend

1. Clonar el repositorio del backend:

    ```bash
    git clone https://github.com/nandomartin7/AplicacionEjercicioMVCBackend.git
    ```

2. Configurar la base de datos en `application.properties` con las credenciales de MySQL.
3. Ejecutar el backend usando Maven:

    ```bash
    mvn spring-boot:run
    ```

### Frontend

1. Clonar el repositorio del frontend:

    ```bash
    git clone https://github.com/nandomartin7/AplicacionEjercicioMVCFrontend.git
    ```

2. Instalar las dependencias:

    ```bash
    npm install
    ```

3. Ejecutar el frontend en modo desarrollo:

    ```bash
    npm run serve
    ```

## Contacto
Fernando Camacho  
Telf: 0984147484     Email: fernando.camacho@ulda.edu.ec
