<template>
    <div class="login-container">
        <h2> Iniciar Sesión</h2>
        <form @submit.prevent="login" class="FormularioInicio">
            <p><input v-model="credentials.email" class="input-field" placeholder="Correo Electronico" required /></p>
            <p> <input type="password" v-model="credentials.password" class="input-field" placeholder="Contraseña" required /> </p>
            <button type="submit" class="btnIniciarSesion">Iniciar Sesion</button>
            <p v-if="loginError" class="error-message">{{ loginError }}</p>
        </form>

        <button @click="toggleRegister" class="btnFormularioRegistro"> {{ showRegister ? 'Cancelar Registro' : 'Regístrate' }}</button>

        <div v-if="showRegister" class="register-container">
            <h2>Registrate</h2>
            <form @submit.prevent="register" class="FormularioRegistro">
                <p><input v-model="newUser.nombre" class="input-field" placeholder="Nombre" required/></p>
                <p><input v-model="newUser.apellido" class="input-field" placeholder="Apellido" required/></p>
                <p><input v-model="newUser.email" class="input-field" placeholder="Correo Electronico" required/></p>
                <p><input type="password" v-model="newUser.password" class="input-field" placeholder="Contraseña" required/></p>
                <button type="submit" class="btnRegistrar">Registrarse</button>
                <p v-if="registerMessage" class="registro-message">{{ registerMessage }}</p>
            </form>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default{
  name: 'LoginPage',
  data() {
    return{
      credentials:{
        email: '',
        password: ''
      },
      newUser: {
        nombre: '',
        apellido: '',
        email: '',
        password: ''
      },
      showRegister: false,
      loginError: '',  //Mensaje de error para el inicio de sesión
      registerMessage: ''  //Mensaje de éxito o error para el registro
    };
  },

  methods: {
    toggleRegister(){
      this.showRegister = !this.showRegister;
      this.registerMessage = '';// Resetea el mensaje de registro al alternar
    },


    login() {
      //Logica para iniciar sesión
      axios.post('http://localhost:8080/auth/login', this.credentials)
      .then(response =>{
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('empleado', JSON.stringify(response.data.empleado));  // Guardar el objeto empleado como JSON
        this.$router.push('/'); // Redirigir a la página principal
      })
      .catch(error =>{
        if (error.response) {
          this.loginError = error.response.data.message || "Error en inicio de sesión.";
        } else {
          this.loginError = "Error en la conexión.";
        }
      });
    },

    register() {
      // Lógica para registrarse
      axios.post('http://localhost:8080/auth/registro', this.newUser)
      .then(() => {
        this.registerMessage = "Registro exitoso. Puedes iniciar sesión ahora.";
        this.newUser.nombre = ''; //Resetea el campo nombre del empleado
        this.newUser.apellido = ''; //Resetea el campo apellido del empleado
        this.newUser.email = ''; //Resetea el campo correo elecctronico del empleado
        this.newUser.password = ''; //Resetea el campo contraseña del empleado
      })
      .catch(error => {
        // Manejar el error de registro
        if (error.response) {
          this.registerMessage = error.response.data.message || "Error en registro.";
        } else {
          this.registerMessage = "Error en la conexión.";
        }
      });
    }
 }
};
</script>


<style scoped>
body {
  background-color: #b3e5fc; /* Fondo celeste */
}

/* Contenedor principal para el inicio de sesión */
.login-container {
  max-width: 400px; /* Ancho máximo del contenedor */
  margin: auto; /* Centra el contenedor horizontalmente */
  padding: 30px; /* Espacio interior del contenedor (padding) */
  border-radius: 10px; /* Bordes redondeados */
  background-color: #00d5ff; 
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Sombra del contenedor */
  text-align: center; 
}

/* Título principal (Iniciar sesión / Registrarse) */
h2 {
  text-align: center;  
  color: #333333; 
  font-family: 'Arial', sans-serif; /* Fuente del texto */
  margin-bottom: 20px; 
}

.input-field{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s;  
  text-align: center;  
}

.btnIniciarSesion{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

.btnFormularioRegistro{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

/* Mensaje de error en rojo */
.error-message {
  color: white;
  text-align: center;
  font-size: 25px;
  font-weight: bold; 
}

/* Contenedor de registro */
.register-container {
  margin-top: 20px; /* Espacio superior entre la sección de registro e inicio de sesión */
  padding-top: 20px; /* Espacio interior en la parte superior */
  border-top: 1px solid #eeeeee; /* Línea superior que separa la sección de registro */
  text-align: center; /* Centra el contenido del registro */
}

.btnRegistrar{
  width: 50%; /* Los botones ocupan el % del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241);  
  color: black;  
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px;  
  margin-top: 10px; /* Espacio superior entre botones */
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

.registro-message{
  color: white; 
  text-align: center;
  font-size: 25px;
  font-weight: bold; 
}

/* Estilos para pantallas pequeñas (responsive) */
@media (max-width: 500px) {
  .login-container,
  .register-container {
    padding: 20px; /* Reduce el padding en pantallas más pequeñas */
  }
}
</style>