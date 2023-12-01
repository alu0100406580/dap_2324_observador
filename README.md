💻 Práctica 7: Patrón Observador (API Mercadona)


✏️ Descripción

Este proyecto es una implementación del patrón de diseño Observador en Java. El objetivo principal de este proyecto es demostrar el uso de este patrón mediante la utilización de una API, en este caso la API de Mercadona. Este proyecto ha sido desarrollado por dúo de trabajo coordinado, con el objetivo de fusionar las contribuciones de cada miembro sin conflictos.


📂 Estructura del Proyecto

El programa consta de varias clases e interfaces, cada una con un propósito específico:

Product: Esta clase representa un producto con atributos como id, nombre, url, imageUrl y precio. También incluye métodos para leer datos JSON de una URL con reintentos y para dormir exponencialmente en caso de fallos de lectura.
ObserverChocolate y ObserverCereals: Estas clases implementan la interfaz Observer y representan a los observadores que están interesados en los cambios de los productos de chocolate y cereales, respectivamente.
ChocolateObservable y CerealObservable: Estas clases implementan la interfaz Observable y y representan fuentes de productos de chocolate y cereales, respectivamente. Cada clase contiene una URL de la API, listas de productos y nuevos productos, un programador de tareas y un conjunto de observadores.
ObserverChocolateWindow y ObserverCerealWindow: Estas clases extienden JFrame y representan las ventanas de la interfaz de usuario donde se muestran los productos de chocolate y cereales, respectivamente.
Observer Esta es una interfaz que define el método update, que es invocado por el sujeto observable para notificar a los observadores sobre cualquier cambio en su estado.
Observable: Esta es una interfaz que define los métodos que deben implementar las clases que quieran ser observables.
Main e InitGui: Estas clases se utilizan para iniciar la interfaz gráfica de usuario y gestionar las interacciones del usuario.

🔧 Cómo ejecutar el proyecto

Para ejecutar el proyecto, siga estos pasos:

Clone el repositorio en su máquina local.

Abra el proyecto en su IDE preferido (por ejemplo, Visual Studio Code).

Ejecute la clase Main.java.


👥 Autores

Darío Afonso Rodríguez alu0100406580@ull.edu.es
Laura Inés González González alu0101332819@ull.edu.es
