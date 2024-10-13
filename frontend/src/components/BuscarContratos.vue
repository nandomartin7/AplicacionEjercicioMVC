<template>
  <div class="buscar-contratos">
    <div class="contenedor-central">
      <label for="fechaInicio">Fecha de Inicio:</label>
      <input type="date" v-model="fechaInicio" />

      <label for="fechaFin">Fecha de Fin:</label>
      <input type="date" v-model="fechaFin" />

      <button @click="buscarContratos">Buscar</button>
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
      <p>No se encontraron contratos en el rango de fechas seleccionado.</p>
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
        .get('http://localhost:8080/reporte/filtrar', {
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
  background-color: beige;
  padding: 20px;
  border-radius: 8px;
  display: inline-block;
}

.acordeon-empleado {
  margin-top: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.acordeon-header {
  padding: 10px;
  font-weight: bold;
  cursor: pointer;
  background-color: #e0e0e0;
  display: flex;
  justify-content: space-between;
}

.acordeon-content {
  padding: 10px;
  background-color: #fff;
}

.acordeon-content table {
  width: 100%;
  border-collapse: collapse;
}

.acordeon-content th, .acordeon-content td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: center;
}
</style>
