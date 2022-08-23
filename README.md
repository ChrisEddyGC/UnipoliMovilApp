# UnipoliMovilApp
 aplicacion movil para el sistema "agenda institucional" de la universidad politecnica de durango

para el desarrollo de la aplicacion se utilizo android studio
        Android Studio Chipmunk | 2021.2.1 Patch 1

Desglose de funciones

1.		Home
Este fragmento es el único que muestra información estática o local de la aplicación, lo único dinámico son dos botones que dirigen al usuario a su navegador web con enlaces hacia la página web institucional y al control escolar

2.		Community news
Para el despliegue de las noticias de la comunidad dentro de la aplicación se dio uso al paradigma de Adaptadores, que se compone de tres piezas: clase CommunityNew, adaptador de información y el fragmento que contendrá la información de la base de datos.
El modelo CommunityNew tiene atributos que llevan el exacto nombre de los campos dentro de un documento de CommunityNew.

3.		Messages
Para el despliegue de los mensajes dentro de la aplicación se dio uso al paradigma de Adaptadores, que se compone de tres piezas: clase Message, adaptador de información y el fragmento que contendrá la información de la base de datos.
El modelo Message tiene atributos que llevan el exacto nombre de los campos dentro de un documento de Message.

4.		Departments
En este fragmento se realiza una consulta

5.		Agenda
Para el despliegue de las agendas dentro de la aplicación se dio uso al paradigma de Adaptadores, que se compone de tres piezas: clase Agenda, adaptador de información y el fragmento que contendrá la información de la base de datos.
El modelo Agenda tiene atributos que llevan el exacto nombre de los campos dentro de un documento de Agenda.

6.		Profile
Dentro de esta actividad se llama a la base de datos utilizando el id del usuario en sesión activa, utilizando la información del usuario (career,grade,group,BIS, period) se obtiene el Schedule del usuario y después se llenan los Textview con la información correspondiente del usuario

7.		Settings
Dentro de esta actividad se llama a la base de datos utilizando el id del usuario en sesión activa, cada uno de los campos dentro del formulario de actualización se llena automáticamente, y una vez que el usuario decida actualizar la información será necesario que el campo de correo electrónico contenga el correo electrónico institucional del usuario para validad dicha transacción.
También está un pequeño formulario donde el usuario ingresa su correo electrónico institucional para realizar el trámite de eliminación de cuenta

8.		Carpeta Model
Las clases dentro de la carpeta model deben contener las variables con el mismo nombre que el campo de donde obtienen el valor, pues estas clases son las que se utilizan para generar la consulta a la base de datos

9.		Carpeta adapter
Las clases dentro de la carpeta Adapter son las encargadas de procesar la información obtenida de las consultas para asignarlas a cada ítem dentro de un recyclerview o directamente en un fragmento o una actividad

10.	Carpeta menú
Dentro de esta carpeta se guardan los actionbar que son utilizados para navegar a través de la aplicación

11.	Carpeta layout
Dentro de la carpeta layout existen tres tipos de layouts:
Activity: que son las actividades de la aplicación
Fragment: los fragmentos que se navegan por medio de los actionbar
View: son los layouts que contienen la estructura de un ítem del recyclerview correspondiente, son los que se llaman dentro de los adapter
