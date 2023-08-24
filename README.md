# APLICACIONES DISTRIBUIDAS (HTTP, SOCKETS, HTML, JS,MAVEN, GIT)

En este proyecto, usando maven, git, js, html, sockets y http requests se busca construir una aplicación que permita devolver la información de la película solicitada con solo haber ingresado el nombre. Para esto se utilizó la API gratuita https://www.omdbapi.com/.

## Estructura y Diseño

El proyecto sigue una estructura coherente basada en la arquitectura cliente-servidor, con un énfasis en la eficiencia y la escalabilidad mediante el uso de un ThreadPool. Además, el patrón MVC se aplica de manera simplificada, donde el HttpServer actúa como el controlador, los métodos de generación de respuestas son la vista y los métodos de gestión de datos representan el modelo.

## Instalación

Primero se clona el repositorio.

~~~
git clone https://github.com/jorge-stack/Taller_01.git
~~~

Una vez clonado se ingresa a la carpeta del proyecto, la cual contenga el pom y se usa el comando:

~~~
mvn package
~~~

## Corriendo el proyecto

Primero se tiene listo el proyecto usando:

~~~
mvn clean install
~~~

 Luego con el siguiente comando se ejecuta

 ~~~
 java -cp "./target/classes" edu.escuelaing.arep.app.HttpServer
 ~~~

 Una vez aparezca en terminal "Operando Buscador de Películas ..." en su navegador web, ingrese a 

 ~~~
 http://localhost:35000/
 ~~~

 Aparecerá un contenedor donde se puede ingresar el nombre de la película de interés

![Buscador](https://github.com/sebasporras14/AREP-DistributedApplications/blob/master/img/buscador.png)

 Y al dar click en "Buscar" en el siguiente contenedor aparecerá alguna información de la película ingresada


 ![información](https://github.com/sebasporras14/AREP-DistributedApplications/blob/master/img/pelicula.png)


## Construido con 
* [Maven](https://maven.apache.org/) - Dependency Management
* [java](https://rometools.github.io/rome/) - Used to generate RSS Feeds


## Autor

* **Sebastian Porras**

### Fecha

08/23/2023 

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
