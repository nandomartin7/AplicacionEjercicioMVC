<template>
    <div>
      
      <!-- Botón para ver todos los clientes -->
      <button @click="cerrarSesion" class="btnCerrarSesion">Cerrar la Sesion</button>
      
      
      <!-- Botón para ver todos los clientes -->
      <p>
        <button @click="fetchClientes" class="btnVerClientes">Ver Todos los Clientes</button>
      </p>
      
      <!-- Botón para agregar nuevo cliente -->
      <p>
        <router-link to="/formulario-cliente">
            <button class="btnAgregarCliente">Agregar Cliente</button> 
        </router-link>
      </p>


      <!-- Búsqueda por ID -->
      <div>
        <input v-model="searchId" placeholder="Buscar por ID del Cliente" />
        <button @click="buscarCliente" class="btnBuscarCliente">Buscar</button>
      </div>
      
      <h2>Lista de Clientes</h2>
      <!-- Tabla para mostrar solo Nombre, Apellido y Cedula -->
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Cédula</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="cliente in clientes" :key="cliente.idCliente">
            <td>{{ cliente.idCliente}}</td>
            <td>{{ cliente.nombre }}</td>
            <td>{{ cliente.apellido }}</td>
            <td>{{ cliente.cedula }}</td>
            <td>
              <button @click="verCliente(cliente)" class="btnVerCliente">Ver</button>
              <button @click="editarCliente(cliente)" class="btnEditarCliente">Editar</button>
              <button @click="eliminarCliente(cliente.idCliente)" class="btnEliminarCliente">Eliminar</button>
            </td>
          </tr>
        </tbody>
      </table>
  
      <!-- Modal para ver toda la información del cliente -->
      <div v-if="clienteSeleccionado">
        <h3>Detalles del Cliente</h3>
        <p>Nombre: {{ clienteSeleccionado.nombre }}</p>
        <p>Apellido: {{ clienteSeleccionado.apellido }}</p>
        <p>Cédula: {{ clienteSeleccionado.cedula }}</p>
        <p>Correo: {{ clienteSeleccionado.correo }}</p>
        <p>Dirección: {{ clienteSeleccionado.direccion }}</p>
        <p>Teléfono: {{ clienteSeleccionado.telefono }}</p>
        <button @click="cerrarModal">Cerrar</button>
      </div>
  
      <!-- Modal para editar cliente -->
      <div v-if="clienteEditando">
        <h3>Editar Cliente</h3>
        <p><label>Nombre: <input v-model="clienteEditando.nombre" /></label></p>
        <p><label>Apellido: <input v-model="clienteEditando.apellido" /></label></p>
        <p><label>Cedula: <input v-model="clienteEditando.cedula" /></label></p>
        <p><label>Correo: <input v-model="clienteEditando.correo" /></label></p>
        <p><label>Dirección: <input v-model="clienteEditando.direccion" /></label></p>
        <p><label>Teléfono: <input v-model="clienteEditando.telefono" /></label></p>  
        <button @click="guardarCambios">Guardar</button>
        <button @click="cancelarEdicion">Cancelar</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        clientes: [],
        clienteSeleccionado: null,
        clienteEditando: null,
        searchId: ""
      };
    },
    methods: {
      fetchClientes() {
        axios.get('http://localhost:8080/clientes')
          .then(response => {
            this.clientes = response.data;
          })
          .catch(error => {
            console.error("Error fetching clients:", error);
          });
      },
      buscarCliente() {
        if (this.searchId) {
          axios.get(`http://localhost:8080/clientes/${this.searchId}`)
            .then(response => {
              this.clientes = [response.data];
            })
            .catch(error => {
              console.error("Error searching client:", error);
              this.clientes = [];
            });
        } else {
          this.fetchClientes();
        }
      },
      verCliente(cliente) {
        this.clienteSeleccionado = cliente;
      },
      editarCliente(cliente) {
        this.clienteEditando = { ...cliente }; // Clonar el cliente para edición
      },
      guardarCambios() {
        axios.put(`http://localhost:8080/clientes/${this.clienteEditando.idCliente}`, this.clienteEditando)
          .then(response => {
            // Actualizar el cliente en la lista
            const index = this.clientes.findIndex(c => c.idCliente === this.clienteEditando.idCliente);
            if (index !== -1) {
              this.clientes[index] = response.data;
            }
            this.clienteEditando = null;
          })
          .catch(error => {
            console.error("Error updating client:", error);
          });
      },
      cancelarEdicion() {
        this.clienteEditando = null;
      },
      cerrarSesion() {
        localStorage.removeItem('token'); // Eliminar el token de localStorage
        this.$router.push('/login'); // Redirigir a la página de login
      },
      eliminarCliente(id) {
        axios.delete(`http://localhost:8080/clientes/${id}`)
          .then(() => {
            this.clientes = this.clientes.filter(cliente => cliente.idCliente !== id);
          })
          .catch(error => {
            console.error("Error deleting client:", error);
          });
      },
      cerrarModal() {
        this.clienteSeleccionado = null;
      }
    },
    mounted(){
        this.fetchClientes();
    }
  };
  </script>
  

  <style scoped>
  .btnCerrarSesion{
    width: 30%; /* Los botones ocupan el % del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: red; 
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    font-size: 15px;  
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
    font-weight: bold;
  }

  .btnVerClientes{
    width: 30%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: rgb(247, 247, 241); 
    color: black;  
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    font-size: 15px; 
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
  }

  .btnAgregarCliente{
    width: 30%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: rgb(247, 247, 241); 
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    font-size: 15px; 
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
  }

  .btnBuscarCliente{
    width: 30%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: rgb(247, 247, 241);
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    font-size: 15px;
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
  }
  
  h2 {
    text-align: center; 
    color: #333333; 
    font-family: 'Arial', sans-serif; 
    margin-bottom: 20px; /* Espacio inferior del título */
  }

  table {
    width: 100%;
    border-collapse: collapse;
  }
  
  th{
    background-color: aqua;
    border: 1px solid black;
    padding: 8px;
    text-align: left;
  }

  td{
    border: 1px solid black;
    padding: 8px;
    text-align: left;
  }
  
  .btnVerCliente{
    width: 20%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: rgb(26, 228, 26);
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
    font-weight: bold;
  }

  .btnEditarCliente{
    width: 20%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: rgb(255, 191, 0); 
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
    font-weight: bold;
  }

  .btnEliminarCliente{
    width: 25%; /* Los botones ocupan el 100% del ancho del contenedor */
    padding: 12px; /* Espacio interior del botón */
    background-color: red; 
    color: black; 
    border: none; 
    border-radius: 5px; /* Bordes redondeados */
    margin-top: 10px; /* Espacio superior entre botones */
    cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
    transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
    text-align: center; 
    font-weight: bold;
  }

  button {
    margin-right: 10px;
  }
  </style>