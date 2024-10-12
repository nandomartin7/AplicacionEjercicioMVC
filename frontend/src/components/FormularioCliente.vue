<template>
    <div class="Agregar-container">
      <h2>Agregar Cliente</h2>
      <form @submit.prevent="submitForm" class="formularioRegistrarCliente">
        <p><input v-model="cliente.nombre" class="input-field" placeholder="Nombre" required /></p>
        <p><input v-model="cliente.apellido" class="input-field" placeholder="Apellido" required /></p>
        <p><input v-model="cliente.cedula" class="input-field" placeholder="Cédula" required /></p>
        <p><input v-model="cliente.correo" class="input-field" placeholder="Correo" required /></p>
        <p><input v-model="cliente.direccion" class="input-field" placeholder="Dirección" required /></p>
        <p><input v-model="cliente.telefono" class="input-field" placeholder="Teléfono" required /></p>
        
        <button type="submit" class="btnGuardar">Guardar</button>
      </form>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        cliente: {
          nombre: '',
          apellido: '',
          cedula: '',
          correo: '',
          direccion: '',
          telefono: ''
        }
      };
    },
    methods: {
      submitForm() {
        axios.post('http://localhost:8080/clientes', this.cliente)
          .then(() => {
            this.$router.push('/'); // Regresar a la lista de clientes después de guardar
          })
          .catch(error => {
            console.error(error);
          });
      }
    }
  };
</script>


<style scoped>
.Agregar-container{
  max-width: 400px; /* Ancho máximo del contenedor */
  margin: auto; /* Centra el contenedor horizontalmente */
  padding: 30px; /* Espacio interior del contenedor (padding) */
  border-radius: 10px; /* Bordes redondeados */
  background-color: #00d5ff; 
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Sombra del contenedor */
  text-align: center; 
}

h2{
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

.btnGuardar{
  width: 50%; /* Los botones ocupan el 100% del ancho del contenedor */
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
</style>
