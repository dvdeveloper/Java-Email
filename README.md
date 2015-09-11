# Ejemplo JavaMail API

Descargar .jar página oficial <br/>
https://java.net/projects/javamail/downloads

##Configuración

Se debe configurar los párametros en el archivo Config.java

```java
  static String email = "EMAIL_GMAIL";
  static String password = "PASSWORD_GMAIL";
  static String host = "smtp.gmail.com";
  static String port = "465";
  
  static String enviar_email = "EMAIL_GMAIL";
  static String titulo = "Correo prueba";
  static String Mensaje = "Contentenido de correo prueba desde java!";
  
  static String ruta_archivo = "RUTA_DESCARGA_ARCHIVOS_ADJUNTOS";
```

## Contenido

1. EnviarEmail
2. Leer último email
3. Leer todos los email
4. Leer todos los email no leidos
5. Descargar adjuntos 
