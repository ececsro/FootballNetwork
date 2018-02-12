# Football Network



Football Network es una red social que propone unir al publico general y los scouts de clubes con los jugadores de fútbol de todos los niveles, desde profesionales a amateurs, pasando por cantera o veteranos.
# Secciones de la web
- Sección Publica: Se podrán consultar los datos de los jugadores de la parte publica, pero no permitirá publicar comentarios ni contactar con los jugadores.
- Sección Privada: Podrá editar su perfil en el que se encontrarán los campos propios de jugador (edad, club, posicion, contacto, pierna buena, bio...), contactar con jugadores, publicar comentarios y valorar jugadores. Tambien tendrá una lista de scouting en la que podra añadir jugadores a los que realizar seguimiento.
# Entidades
- Usuario: El usuario podrá interactuar con los diferentes elementos propios de los jugadores (publicar comentarios, valorar jugadores, contactar...). 
- Jugador: El jugador es un componente opcional del usuario, en el que se podra dar de alta como jugador para que otros usuarios puedan interactuar con su perfil.
- Valoracion: La valoracion permite a los usuarios valorar positiva o negativamente a los jugadores con un sistema de "karma".
- Scouting: los usuarios disponen de la opcion de añadir a jugadores a la lista de scouting en la que pueden recopilados a los jugadores que consideran mas prometedores o que simplemente desean tener más a mano el acceso a sus perfiles.
- Comentario: los usuarios pueden dejar un comentario en el perfil de los jugadores que puede visualizar el resto de los usuarios.

# Servicio Interno
- Contacto: los usuarios podran comunicarse con los jugadores de manera directa mediante un sistema de contacto que enviará un correo personalizado al email del jugador en cuestión. De esta manera pueden establecer relaciones de manera confidencial los juagadores y los usuarios mediante correo electronico y no solo por comentarios públicos.
# Integrantes
- Alvaro Barroso Mato
    - Correo de la Universidad: a.barrosoma@alumnos.urjc.es
    - Correo personal :         alvarobm61@gmail.com
    - Github:                   https://github.com/alvaroBarrosoMato 
# Fase 2
La aplicación esta manipulada principalmente desde la cabecera que consta de 3 accesos a diferentes funcionalidades de la web y a la pantalla principal "HOME" pulsando en el logo de la web. Esta diseñada mediante dos componentes css que gobiernan el estilo de la cabecera y de los diferentes componentes de las pantallas.
 ![Pantalla de HOME](https://i.gyazo.com/ebb000909d46fe571c6818b1e5bfdbfb.png)
La pantalla de home nos permitira visualizar y acceder a todos los elementos de la web desde la cabecera como se había adelantado previamente. Además permite el acceso a un sistema de busqueda rapida de jugadores mediante un cuadro de busqueda. El uso de este cuadro lleva a la pantalla de jugadores( Players) que muestra la lista de jugadores cuyo nombre coincide con el especificado en la búsqueda.

 ![Pantalla de lista de Jugadores](https://i.gyazo.com/7844fd2d5ce1403fd95c98d1744b7b27.png)
Esta interfaz permite visualizar los jugadores de la base de datos asi como algunos de los principales atributos de los mismos: apellido, posición, valoración...
Uno de los puntos principales de esta interfaz es la presencia de una casilla que indica si el jugador se encuentra en la lista de Scouting del usuario en cuestión; en el caso de que se muestre con una cruz en rojo no estará, y si tiene un símbolo verde estará incluido en la lista.

 ![Pantalla de Jugador](https://i.gyazo.com/f1a315290e26085db583d1a4f81ced1f.png)
 Esta es la pantalla de visualización de los jugadores, esta compuesta por las siguientes partes:
 - Imagen de jugador: en el caso de que el usuario haya incluido una imagen durante el proceso de creación del usuario se visualizará aquí, en su defecto se muestra el icono que vemos en este caso.
 - Perfil del usuario: este incluye todos los aspectos del jugador.
 - Scouting: siguiendo el mismo código que en la pantalla anterior se muestra el simbolo de Scouting junto con un icono verde o rojo, si se pulsa el icono se cambia su estado: si esta siendo ojeado se le quita de la lista y en el caso contrario se le añade.
 - Comentarios: se muestra en la parte inferior de la pantalla los comentarios que ha recibido el jugador, acompañados de el nombre del usuario que ha comentado y el "karma" que tiene el comentario:
    - karma: el karma es un sistema de valoracion de los comentarios: si se pulsa el boton verde se subirña un punto y si se pulsa el boton rojo se bajará.
    - Añadir comentario: para añadir comentarios hay una caja de texto en la parte inferior que permite añadir un comentario.
- Contrato: en la sección derecha de la interfaz se muestra el contrato del jugador a lo largo de los años.
 
 ![Pantalla de Scouting](https://i.gyazo.com/14744a498fff550cfc03ef830bb7715d.png)
 En esta pantalla se pueden consultar los diferentes jugadores presentes en la lista de scouting y se comporta de la misma manera que la pantalla de visualización de los jugadores.
 ![Pantalla de Creacion De Usuario](https://i.gyazo.com/08dbf75bf9f36ef8e89d6e0816ea8348.png)
 En esta interfaz se crean los diferentes perfiles de jugadores de los usuarios.
## Diagrama de Navegación
 ![Diagrama de Navegación](/DiagramaPantallas.png)
## Modelo de Datos
 ![Modelo de Datos](https://i.gyazo.com/a2baf882dc712b94d68ba1067a99608f.png)
