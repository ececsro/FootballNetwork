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
## Diagrama Entidad-Relacion
 ![Diagrama Entidad-Relacion](/Entidad-Relacion.png)
 # Fase 3
 
## Despliegue en Servidor
### Maquina Virtual
Para comenzar con el despliegue es necesario conseguir un terminal en el que poder mantener el servidor de la aplicacion, para ello se usa una maquina virtual que llevará incorporado el SO Linux Server 16.04.4.
#### Instalacion de Linux Server
 - Descargar Virtual Box.
 - Montar una nueva maquina virtual desde Virtual Box.
 - Descargar Linux Server 16.04.4.
 - Inicializar con Linux Server 16.04.4.
    - Configuración durante el primer arranque de Linux Server 16.04.4.
    - Hostname: un nombre para reconocer la MV en la red.
    - User full name: el nombre del usuario: en mi caso "Alvaro Barroso".
    - Username: nombre de usuario: corto, minusculas. En mi caso "alvaro".
    - Password: la contraseña con la que se autenticará al usuario necesaria para arrancar el servidor y la aplicación.
    - Write partition changes to disk:  “Yes”.
    - Write to disk (again):  “Yes”.
    - Software selection: “standard system utilities” estará seleccionado por defecto. Pulsar intro.
    - GRUB boot loader: el seleccionado por defecto.
 - Ya se tiene instalado el SO en la MV.
#### Instalación de Java
En esta fase descargaremos e instalaremos la version de java que dara soporte a nuestra aplicación y servicio interno.
 - Inicializar el servidor
 - Comenzar secuencia de comandos:
    - sudo apt\-get update  > Actualiza la lista de referencias apt-get.
    - java \-version > Comprueba que java no esta instalado.
    - sudo apt\-get install default\-jre > instala el jre por defecto (en mi caso es 1.8.1.151).
    - java \-version > Comprueba que java esta instalado.
#### Instalacion de MySQL
 - Inicializar el servidor
 - Comenzar secuencia de comandos:
    - sudo apt\-get update  > Actualiza la lista de referencias apt-get.
    - sudo apt\-get install mysql\-server > instala MySQLServer. Configuracion durante la instalacion:
        - username : root
        - password : brujula61
 - Una vez instalado hay que ejecutar el programa, abrir los schemas y crear uno nuevo:
    - mysql \-u root \-p
    - introducir la contraseña
    - show databases;
    - Create footballnetwork
 - Ya estaria todo lo relativo a la base de datos
### Configuracion de Maquina Virtual
Previo al despliegue de la aplicación en la maquina virtual se realizan una serie de cambios en el codigo que permitirán su exportación:
- Configurar las direcciones de servicio interno y del servidor (para obtener la ip usar IFCONFIG)
    - En la maquina virtual introducir comando : ifconfig
    - Guardar dirección IP de la maquina virtual : En mi caso 10.0.2.15.
        - Modificar variable servicePath a > servicePath = "http://10.0.2.15:8060/".
- Configuración de la red de interconexión de la VM:

 ![Configuracion de Red](https://i.gyazo.com/5e57c2f771b1000e0d18e2d46cc16134.png)
- Encontrar la ip de referencia a la red de maquinas virtuales en el SO host:
    - ipconfig > introducir en caso de Windows
    
    ![Configuracion de Red](https://i.gyazo.com/9e147532a3277b67cfa5292a911c0f36.png)
    - en mi caso 192.168.56.1 es la ip que hará referencia a la VM.
    - ifconfig > en caso de Linux
    
- Instalar aplicaciones en Linux Server:
    - Montar el usb que contiene las aplicaciones
    - Copiar los datos a la carpeta destino
### Despliegue final
- Instalar aplicaciones en Linux Server:
    - Copiar ambos ficheros .jar (ContactService y FootballNetwork)
    - Habilitar el usb en la MV.
    - Montar el usb que contiene las aplicaciones.
        - sudo fdisk \-l > muestra los dispositivos conectados
        - buscar uno con caracteristicas como el usb que hemos conectado
        ![Montar USB](https://i.gyazo.com/7ea0a822912d59d4afd9539126e42763.png)
        - en mi caso /dev/sdb1
        - pmount /dev/sdb1
    - Copiar los datos a la carpeta destino
        - cp /media/sdb1 /server
- Crear script BASH para inicializar los dos componentes de la aplición al mismo tiempo
    - setup.sh
    
    ![BASH](https://i.gyazo.com/eb680256bac0edf8a5930e5000fb4e4d.png)
    
### Inicializacion de la aplicacion
- cd al directorio donde se encuentre la aplicacion y el setup.sh
- chmod 754 ./setup.sh
- bash ./setup.sh
## Servicio Interno
- Sistema de envio email
- Soporte gmail
- Envio sobre SMTP
![Clase de envio de email](https://i.gyazo.com/561bf30232e6beea22715c5c150a9568.png)
- RestController
- Soporte de un solo comando
    - send (POST)
    - Cuerpo
        - Email
            - To
            - From
            - Msg
## Diagrama de Navegación
 ![Diagrama de Navegación](https://i.gyazo.com/a4c883477b921fd2d530f450a75234e6.png)
## Diagrama de Clases y Templates
 ![Diagrama de Clases y Templates](https://i.gyazo.com/70eb3386bf7598a12627cf98f128e862.png)
# Fase 4
## Instalacion de vagrant
  Para comenzar la instalación de Vagrant se comenzará por su descarga, en este caso de la versión de windows ( [descargar](https://releases.hashicorp.com/vagrant/2.0.3/vagrant_2.0.3_x86_64.msi?_ga=2.64933999.115064628.1521550726-2022707724.1521550726)). Una vez descargado se procede a su instalación.
  En esta fase se comienza con la separación de las partes de la aplicación en diferentes maquinas, para ello se elabora para empezar un pequeño esquema que describe como se distribullen las direcciones IP de los distintos elementos.
   ![Diagrama IPs](https://raw.githubusercontent.com/alvaroBarrosoMato/FootballNetwork/master/IPDiagram.png)
   Una vez establecido como se organizarán las IPs ya se puede proceder al montaje de la VagrantBox.
- Se comienza creando un directorio en el lugar del host donde se desee montar el sistema. En mi caso: 

        C:\Users\alvar\Desktop\DAD\Vagrant
 - Una vez creado el directorio se posiciona en él y se abre la Windows PowerShell:

        Mayus + click derecho -> abrir ventana de Windows PowerShell aquí.
 - Se crea el VagrantFile:

        vagrant init
    Este es el resultado deseado, en el cual confirma la creación del vagrantfile.
![vagrant init](https://i.gyazo.com/620e189ec7faf0c022e59d7140a14005.png)
 - A continuacion se procede a la edición del vagrantfile para cumplir los requisitos del usuario.
## Vagrantfile
En mi caso el vagrantfile se encarga de montar las diferentes maquinas en una VagrantBox, de esta manera me permite configurarlas y aprovisionarlas a todas a la vez y en un mismo espacio, asi como arrancarlas y cerrarlas al mismo tiempo.

        # -*- mode: ruby -*-
        # vi: set ft=ruby :

        Vagrant.configure("2") do |config|
          config.vm.define "host" do |host|
            host.vm.box = "ubuntu/trusty64"
            host.vm.network "private_network", ip: "192.168.10.21"
            host.vm.hostname = "host"
            host.vm.provision "shell", inline: <<-SHELL
                echo    Comenzando con la instalacion de java
                sudo apt-get update
                sudo apt-get install -y default-jdk
                sudo apt-get install -y default-jre
                echo    Instalacion finalizada
            SHELL
          end

          config.vm.define "si" do |si|
            si.vm.box = "ubuntu/trusty64"
            si.vm.network "private_network", ip: "192.168.10.22"
            si.vm.hostname = "si"
            si.vm.provision "shell", inline: <<-SHELL
                echo    Comenzando con la instalacion de java
                sudo apt-get update
                sudo apt-get install -y default-jdk
                sudo apt-get install -y default-jre
                echo    Instalacion finalizada
               sudo echo "192.168.10.21 host" | sudo tee -a /etc/hosts
            SHELL
            end

            config.vm.define "database" do |database|
              database.vm.box = "ubuntu/trusty64"
              database.vm.network "private_network", ip: "192.168.10.23"
              database.vm.hostname = "database"
              database.vm.provision "shell", inline: <<-SHELL
                echo    Comenzando con la instalacion de java
                sudo apt-get update
                sudo apt-get install -y default-jdk
                sudo apt-get install -y default-jre
                echo    Instalacion finalizada
               sudo echo "192.168.10.21 host" | sudo tee -a /etc/hosts
            SHELL
        end
        config.vm.define "hostBU" do |hostBU|
            hostBU.vm.box = "ubuntu/trusty64"
            hostBU.vm.network "private_network", ip: "192.168.10.24"
            hostBU.vm.hostname = "host"
            hostBU.vm.provision "shell", inline: <<-SHELL
                echo    Comenzando con la instalacion de java
                sudo apt-get update
                sudo apt-get install -y default-jdk
                sudo apt-get install -y default-jre
                echo    Instalacion finalizada
            SHELL
          end
        end

En mi VagrantFile se configuran 4 maquinas en una VagrantBox con sus corresponidentes direcciones IP. Adicionalmente se las aprovisiona:
Mediante la provisión se permite la ejecución de un script BASH cada vez que la maquina se levante en un nuevo host. Esta funcionalidad permite la instalación de java en nuestro caso de manera rapida y automatica en todas las maquinas que lo requieren.
## Levantar el sistema
Una vez realizados estos cambios ya solo queda levantar el sistema. Para ello ejecutar en la consola:

        vagrant up
La creación de las maquinas llevará un tiempo pero una vez finalizado ya se tendrán 4 maquinas con sus correspondientes jre y jdk instalados. 
Tras la creación de las maquinas es turno de configurar cada maquina de manera individual:
### Sistema Interno
Para acceder a esta maquina se usa:

    vagrant ssh si
Una vez ejecutado este comando nos encontramos en la maquina
Desde el SO host copiamos el archivo .jar que corresponde al servicio interno en la carpeta contenedora de la vagrantBox (la que contiene el vagrantfile), en mi caso: 

    C:\Users\alvar\Desktop\DAD\Vagrant
![vagrant init](https://i.gyazo.com/7274f1b5433521bd47e93541337ce8d3.png)
Esta carpeta funcionará como una carpeta compartida entre todas las maquinas del sistema .
Desde la ventana de comandos se ejecuta el archivo mediante:

    java -jar nombredelarchivo.jar

En este momento el Servicio Interno ya esta operativo.



