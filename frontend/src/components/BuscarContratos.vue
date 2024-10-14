<template>
  <div class="buscar-contratos">
    <div class="contenedor-central">
      <div class="fechas-wrapper">
        <p>
          <label for="fechaInicio" class="label" >Fecha de Inicio:</label>
          <input type="date" v-model="fechaInicio" class="input-date"/>
        </p>
        <p>
          <label for="fechaFin" class="label">Fecha de Fin:</label>
          <input type="date" v-model="fechaFin" class="input-date" />
        </p>
      </div>
      <p><button @click="buscarContratos" class="btnBuscar">Buscar</button></p>
    </div>

    <div v-if="empleadosFiltrados.length" class="contenedor-resultados">
      <h3>Resultados de la búsqueda</h3>
      <!-- Acordeón por empleado -->
      <div v-for="(empleado, index) in empleadosFiltrados" :key="index" class="acordeon-empleado">
        <div class="acordeon-header" @click="toggleContratos(index)">
          <!-- Mostrar la clave tal como viene del backend -->
          <span>{{ empleado.empleadoKey }}</span>
          <span>(Contratos: {{ empleado.contratos.length }})</span>
        </div>
        <!-- Contratos del empleado -->
        <div v-if="empleado.mostrarContratos" class="acordeon-content">
          <table>
            <thead>
              <tr>
                <th>ID Contrato</th>
                <th>Cédula Cliente</th>
                <th>Descripción</th>
                <th>Estado</th>
                <th>Fecha de Ingreso</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(contrato, idx) in empleado.contratos" :key="idx">
                <td>{{ contrato.idContrato }}</td>
                <td>{{ contrato.cedula }}</td>
                <td>{{ contrato.descripcion }}</td>
                <td>{{ contrato.estado }}</td>
                <td>{{ contrato.fechaIngreso }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
    <div v-else>
      <h3>No se encontraron contratos en el rango de fechas seleccionado.</h3>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      fechaInicio: '',
      fechaFin: '',
      empleadosFiltrados: []
    };
  },
  methods: {
    buscarContratos() {
      if (!this.fechaInicio || !this.fechaFin) {
        console.error('Debe seleccionar ambas fechas.');
        return;
      }

      // Realizar la solicitud al backend con Axios
      axios
        .get('https://easygoing-analysis-production.up.railway.app/reporte/filtrar', {
          params: {
            fechaInicial: this.fechaInicio,
            fechaFin: this.fechaFin
          }
        })
        .then((response) => {
          // Procesar la respuesta y asignar a empleadosFiltrados
          this.empleadosFiltrados = Object.keys(response.data).map((key) => {
            return {
              empleadoKey: key, // Usar directamente la clave del mapa (nombre, apellido, email)
              contratos: response.data[key],
              mostrarContratos: false // Inicialmente los contratos están colapsados
            };
          });
        })
        .catch((error) => {
          console.error('Error al buscar los contratos:', error);
          this.empleadosFiltrados = [];
        });
    },
    toggleContratos(index) {
      // Alternar la visibilidad de los contratos de un empleado
      this.empleadosFiltrados[index].mostrarContratos =
        !this.empleadosFiltrados[index].mostrarContratos;
    }
  }
};
</script>

<style scoped>
.buscar-contratos {
  text-align: center;
  margin-top: 50px;
}

.contenedor-central {
  display: inline-block;
  max-width: 400px; /* Ancho máximo del contenedor */
  margin: auto; /* Centra el contenedor horizontalmente */
  padding: 20px; /* Espacio interior del contenedor (padding) */
  border-radius: 10px; /* Bordes redondeados */
  background-color: #00d5ff; 
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); /* Sombra del contenedor */
  text-align: center;
}

.fechas-wrapper {
  display: flex;
  justify-content: center; /* Centrar los elementos horizontalmente */
  gap: 20px; /* Espacio entre las dos fechas */
}

.label{
  font-size: 18px; /* Tamaño de la fuente */
}

.input-date{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px;
  margin-top: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center;
}

.btnBuscar{
  width: 50%; /* Los botones ocupan el 100% del ancho del contenedor */
  padding: 12px; /* Espacio interior del botón */
  background-color: rgb(247, 247, 241); 
  color: black; 
  border: none; 
  border-radius: 5px; /* Bordes redondeados */
  font-size: 18px; 
  cursor: pointer; /* Cambia el cursor a mano al pasar por encima */
  transition: background-color 0.3s, margin 0.3s; /* Transiciones suaves al hacer hover */
  text-align: center; 
}

.acordeon-empleado {
  width: 80%;
  margin: auto;
  margin-top: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
}

.acordeon-header {
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
  background-color: #00d5ff; 
  display: flex;
  justify-content: space-between;
}

.acordeon-content {
  width: 80%;
  margin: auto;
  padding: 10px;
  background-color: #fff;
}

.acordeon-content table {
  width: 100%;
  border-collapse: collapse;
}

.acordeon-content table thead{
  background-color: #99dfff; 
}

.acordeon-content th, .acordeon-content td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: center;
}
</style>
