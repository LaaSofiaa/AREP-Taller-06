
#  Taller Secure Application Design

En este taller de Arquitectura Empresarial, se diseñará y desplegará una aplicación segura y escalable en AWS. La arquitectura incluirá un servidor Apache para servir un cliente HTML+JavaScript de forma asíncrona y protegida con TLS, y un servidor Spring para manejar servicios RESTful también con TLS. Se implementará autenticación con contraseñas y certificados TLS emitidos por Let’s Encrypt.


## Instalación

**1.**  Clonar el repositorio

```bash
  git clone https://github.com/LaaSofiaa/AREP-Taller-06.git

  cd AREP-Taller-06
```
**2.**  Construir el proyecto mediante maven, donde debes tener previamente instalado este https://maven.apache.org . Luego pruebe el siguiente comando para compilar, empaquetar y ejecutar. 
```bash
  mvn clean install
  mvn package
```  
**3.**  Ejecuta el proyecto con el siguiente comando:
```bash
  java -jar target/Taller6-0.0.1-SNAPSHOT.jar

```

**4.**  Una vez este corriendo la aplicación prueba los siguiente:

* **Página Principal:**
```bash
  http://localhost:8080/auth.html
```


## Arquitectura del Proyecto 
### **Estructura del directorio**

El directorio del proyecto esta organizado de la siguiente manera:

```plaintext
src/
└── main/
    ├── java/
    │   └── com.edu.arep.Taller6/
    │       ├── Config/
    │       │   └── SecurityConfig
    │       ├── Controller/
    │       │   ├── AuthController
    │       │   └── PropertyController
    │       ├── Entity/
    │       │   ├── Property
    │       │   └── User
    │       ├── Repository/
    │       │   ├── PropertyRepository
    │       │   └── UserRepository
    │       ├── Service/
    │       │   ├── AuthService
    │       │   └── PropertyService
    │       └── Taller6Application
    ├── resources/
    │   ├── static/
    │   │   ├── auth.css
    │   │   ├── auth.html
    │   │   ├── auth.js
    │   │   ├── index.html
    │   │   ├── script.js
    │   │   └── styles.css
    │   └── templates/
    │   └── application.properties
└── test/
    └── java/
        └── com.edu.arep.Taller6/
            ├── AuthTest
            └── PropertyTest
```

### **Capas del Proyecto**

 El sistema se compone de tres capas principales:  

- **Frontend (HTML, CSS, JavaScript):** Proporciona una interfaz gráfica con formularios para autenticación y gestión de propiedades. Utiliza fetch para enviar solicitudes a la API REST.
- **Backend (Spring Boot, Java, JPA/Hibernate):** Expone una API RESTful con endpoints para registro, inicio de sesión y CRUD de propiedades. Maneja la lógica de autenticación, validación de datos y gestión de la base de datos.
- **Base de datos (MySQL):** Almacena la información de los usuarios y las propiedades en tablas estructuradas, permitiendo la persistencia de datos.

El frontend, backend y la base de datos están diseñados para ser desplegados en servidores separados en AWS.


###  **Diseño de Clases**

El sistema sigue el patrón MVC (Modelo-Vista-Controlador), y sus principales clases son:  

- **Config:**
  
  1. `SecurityConfig.java`: Configura la seguridad de la aplicación deshabilitando CSRF y habilitando CORS para aceptar solicitudes desde cualquier origen y método HTTP.
- **Entidad:**
  
  1. `Property.java`: Representa una propiedad con atributos como `id`, `address`, `price`, `size` y `description`.
  2. `User.java`: Representa la entidad de usuario en la base de datos con atributos como `id`, `email` y `password`, proporcionando métodos para acceder y modificar estos datos.
- **Repositorio:**
  
  1. `PropertyRepository.java`: Interactúa con la base de datos mediante JPA.
  2. `UserRepository.java`: Es una interfaz que extiende CrudRepository para realizar operaciones CRUD sobre los usuarios y permite buscar un usuario por su correo electrónico.
- **Servicio:**
  
  1. `PropertyService.java`: Implementa la lógica de negocio y operaciones sobre las propiedades.
  2. `AuthService.java`: Maneja la lógica de autenticación y registro de usuarios, encriptando las contraseñas con BCryptPasswordEncoder al registrar un nuevo usuario y validando las credenciales durante el inicio de sesión.
 
     El proceso de encriptación de contraseñas utiliza BCryptPasswordEncoder de Spring Security, que encripta la contraseña del usuario utilizando el algoritmo BCrypt, un algoritmo seguro que genera un hash único para cada contraseña, incluso si dos usuarios tienen la misma contraseña. Este hash incluye un sal aleatorio que aumenta la seguridad, previniendo ataques de diccionario o fuerza bruta. Al registrar un usuario, la contraseña se encripta y se almacena el hash en la base de datos. Al iniciar sesión, se compara la contraseña ingresada con el hash almacenado utilizando el método matches(), sin necesidad de desencriptar la contraseña.

     
- **Controlador**
  
  1. `PropertyController.java`: Expone los endpoints REST para la gestión de propiedades.
  2. `AuthController.java`: Controla las rutas de autenticación, permitiendo registrar nuevos usuarios y validar las credenciales durante el inicio de sesión.

    
### Diagrama del proyecto

![image](https://github.com/user-attachments/assets/5c422488-88f1-4267-825b-6ea0ad70dc98)

## Docker y AWS
1. **Creación de la instancia**

   Asi se crearon todas las instancias Apache, Spring, MySql

[Ver video de demostración](https://youtu.be/26UW_WXy4Yc)


2. **Demostración**

[Ver video de demostración](https://youtu.be/wuQp0Pf7BV0)

## Comandos para obtener el certificado 

### Paso 1
Instalar cerbot
```bash
  sudo yum install certbot -y
   sudo yum install python3-certbot-apache -y
```
### Paso 2

Una vez instalado Certbot, puedes generar el certificado SSL para tu dominio.

```bash
  sudo certbot --apache -d ejemplo.com -d www.ejemplo.com
```
### Paso 3

Después de instalar el certificado SSL, es posible que debas reiniciar tu servidor web para que los cambios surtan efecto.

```bash
  sudo systemctl restart httpd
  sudo systemctl status httpd
```

## Pruebas Automatizadas

Las pruebas realizadas en el sistema de gestión de propiedades incluyen los siguientes casos, implementados con JUnit y Mockito para validar la funcionalidad del controlador y servicio:

**getAllProperties:** Se verifica que el sistema pueda recuperar correctamente una lista de propiedades. Se simula una respuesta del servicio y se comprueba que el tamaño de la lista obtenida es el esperado.

**getPropertyById:** Se prueba la obtención de una propiedad por su ID, asegurando que el sistema devuelva la propiedad correcta y que la respuesta HTTP sea exitosa.

**createProperty:** Se valida que una nueva propiedad pueda ser creada correctamente. Se compara la información devuelta por el controlador con la propiedad ingresada.

**updateProperty:** Se comprueba que una propiedad existente pueda ser modificada y que la actualización refleje los cambios esperados en la base de datos.

**deleteProperty:** Se prueba que una propiedad pueda ser eliminada correctamente, asegurando que la operación sea ejecutada sin errores.

Cada prueba incluye validaciones de estado HTTP y verificaciones con Mockito para asegurar que los métodos del servicio sean invocados correctamente.

**registerUser:** Verifica que un usuario pueda registrarse correctamente. Simula el hash de la contraseña y guarda el usuario en la base de datos, asegurando que los datos guardados son correctos.

**loginUserSuccess:** Prueba un inicio de sesión exitoso. Simula la búsqueda del usuario por correo y verifica que la contraseña coincida con la almacenada (usando BCrypt).

**loginUserFailure:** Prueba un inicio de sesión fallido con una contraseña incorrecta. Asegura que no se retorne ningún usuario si la contraseña no coincide.

**registerUserAlreadyExists:** Valida que si un usuario ya está registrado, se lance una excepción y no se guarde un usuario duplicado.

---

Para correr las pruebas usamos el siguiente comando

```bash
  mvn test

```
![image](https://github.com/user-attachments/assets/e90cac90-613a-4bcf-901d-85d6b37e2dbc)
![image](https://github.com/user-attachments/assets/246f9d42-6ec8-49c0-ac0d-c7314a35ee71)
![image](https://github.com/user-attachments/assets/fcd584da-5a57-4b53-a9de-3173a479e251)

## Autor

**Laura Gil** - Desarrolladora y autora del proyecto. 





