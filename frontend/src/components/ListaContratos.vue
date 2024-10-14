<template>
    <div>
        <div class="btns-container">            
            <button @click="fetchContratos" class="btnVerContratos">Ver todos los contratos</button> 
            
            <router-link to="/formulario-contrato">
                <button class="btnAgregarContrato">Agregar Contrato</button> 
            </router-link> 
        
            <router-link to="/filtrar">
                <button class="btnFiltrarContrato">Filtrar Contratos</button> 
            </router-link>

            <button @click="cerrarSesion" class="btnCerrarSesion">Cerrar Sesion</button>
        </div>

        <div>
            <input v-model="searchId" class="searchId" placeholder="Buscar por Cedula del Contrato"/>
            <button @click="buscarContrato" class="btnBuscarContrato">Buscar</button>
        </div>

        <h2>Lista de Contratos</h2>
        <!-- Tabla para mostrar solo info del contrato -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Cedula</th>
                    <th>Contratista</th>
                    <th>Estado Activo</th>
                    <th>Fecha Ingreso</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="contrato in contratos" :key="contrato.cedula">
                    <td>{{ contrato.idContrato }}</td>
                    <td>{{ contrato.cliente }}</td>
                    <td>{{ contrato.cedula }}</td>
                    <td>{{ contrato.empleado ? contrato.empleado.email : 'Sin empleado asignado' }}</td>
                    <td>{{ contrato.estado }}</td>
                    <td>{{ contrato.fechaIngreso }}</td>
                    <td>
                        <button @click="verContrato(contrato)" class="btnVerContrato">Ver</button>
                        <button @click="editarContrato(contrato)" class="btnEditarContrato">Editar</button>
                        <button @click="eliminarContrato(contrato.cedula)" class="btnEliminarContrato">Eliminar</button>
                    </td>                    
                </tr>
            </tbody>
        </table>

        <!-- Modal para ver toda la información del contrato -->
        <div v-if="contratoSeleccionado">
            <h3>Detalles del Contrato</h3> 
            <p>ID: {{ contratoSeleccionado.idContrato }}</p>
            <p>Nombre: {{ contratoSeleccionado.cliente }}</p>
            <p>Cedula: {{ contratoSeleccionado.cedula }}</p>
            <p>Descripcion: {{ contratoSeleccionado.descripcion }}</p>
            <p>Contratista: {{ contratoSeleccionado.empleado ? contratoSeleccionado.empleado.email : 'Sin empleado asignado' }}</p>
            <p>Valor: {{ contratoSeleccionado.valor }}</p>
            <p>Estado Activo: {{ contratoSeleccionado.estado }}</p>
            <p>Fecha Inicio: {{ contratoSeleccionado.fechaInicio }}</p>
            <p>Fecha Finalizacion: {{ contratoSeleccionado.fechaFin }}</p>
            <p>Fecha Ingreso: {{ contratoSeleccionado.fechaIngreso }}</p>
            <button @click="cerrarModal">Cerrar</button>
        </div>

        <!-- Modal para editar contratos-->
        <div v-if="contratoEditando">
            <h3>Editar Contratp</h3>
            <p><label>Nombre: <input v-model="contratoEditando.cliente" required/></label></p>
            <p><label>Cedula: <input v-model="contratoEditando.cedula" required/></label></p>
            <p><label>Descripcion: <input v-model="contratoEditando.descripcion" required/></label></p>
            <p><label>Valor: <input type="number" v-model="contratoEditando.valor" step="0.01" required/></label></p>
            <p>
               <label>Estado Activo:
                    <select v-model="contratoEditando.estado">
                        <option :value="true">Activo</option>
                        <option :value="false">Inactivo</option>
                    </select>
                </label>
            </p>
            <p><label>Fecha Inicio:<input type="date" v-model="contratoEditando.fechaInicio" required /></label></p>
            <p><label>Fecha Finalizacion:<input type="date" v-model="contratoEditando.fechaFin" required /></label></p>
            <button @click="guardarCambios">Guardar</button>
            <button @click="cancelarEdicion">Cancelar</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default{
    data() {
        return{
            contratos: [],
            contratoSeleccionado: null,
            contratoEditando: null,
            searchId: ""
        };
    },
    methods: {
        fetchContratos() {
            axios.get('https://easygoing-analysis-production.up.railway.app/contratos')
            .then(response => {
                this.contratos = response.data;
            })
            .catch(error => {
                console.error("Error fetching contratos: ", error);
            });
        },
        buscarContrato(){
            if (this.searchId){
                axios.get(`https://easygoing-analysis-production.up.railway.app/contratos/${this.searchId}`)
                .then(response => {
                    this.contratos = [response.data];
                })
                .catch(error => {
                    console.error("Error buscando contrato:", error);
                    this.contratos = [];
                });
            } else {
                this.fetchContratos();
            }
        },
        verContrato(contrato){
            this.contratoSeleccionado = contrato;
        },
        editarContrato(contrato){
            this.contratoEditando = { ...contrato}; //Clonar el contrato para editarlo
        },
        guardarCambios() {
            axios.put(`https://easygoing-analysis-production.up.railway.app/contratos/${this.contratoEditando.cedula}`, this.contratoEditando)
            .then(response => {
                // Actualizar el contrato en la lista
                this.fetchContratos();
                this.cancelarEdicion();
                const index = this.contratoEditando.cedula;
                if (index !== -1){
                    this.contratos[index] = response.data;
                }
                this.contratoEditando = null;
            })
            .catch(error => {
                console.error("Error actualizando contrato:", error);
            });
        },
        cancelarEdicion(){
            this.contratoEditando = null;
        },
        cerrarSesion() {
            localStorage.removeItem('token'); // Eliminar el token de localStorage
            this.$router.push('/login'); // Redirigir a la página de login
        },
        eliminarContrato(cedula) {
            axios.delete(`https://easygoing-analysis-production.up.railway.app/contratos/${cedula}`)
            .then(() => {
                this.contratos = this.contratos.filter(contrato => contrato.cedula !== cedula);
            })
            .catch (error => {
                console.error("Error eliminando contrato:", error);
            });
        },
        cerrarModal(){
            this.contratoSeleccionado = null;
        }
    },
    mounted(){
        this.fetchContratos();
    }
};
</script>

<style scoped>
.btns-container {
    width: 80%;
    justify-content: center; /* Centrar el contenedor horizontalmente */
    margin: auto; /* Centra el contenedor en la página */
}


.btnCerrarSesion{
    flex: 1 1 auto;
    width: 20%; /* Los botones ocupan el % del ancho del contenedor */
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


.btnVerContratos{
    flex: 1 1 auto;
    width: 25%; /* Los botones ocupan el 100% del ancho del contenedor */
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

.btnAgregarContrato{
    flex: 1 1 auto;
    width: 20%; /* Los botones ocupan el 100% del ancho del contenedor */
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

.btnFiltrarContrato{
    flex: 1 1 auto;
    width: 20%; /* Los botones ocupan el 100% del ancho del contenedor */
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

.searchId{
  width: 25%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}

.btnBuscarContrato{
    width: 10%; /* Los botones ocupan el 100% del ancho del contenedor */
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
    width: 80%;
    border-collapse: collapse;
    margin: auto;
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
  
.btnVerContrato{
    min-width: auto; /* Los botones ocupan el 100% del ancho del contenedor */
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
    white-space: nowrap;
    text-overflow: ellipsis;
}

.btnEditarContrato{
    min-width: auto; /* Los botones ocupan el 100% del ancho del contenedor */
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
    white-space: nowrap;
    text-overflow: ellipsis;
}

.btnEliminarContrato{
    min-width: 25%; /* Los botones ocupan el 100% del ancho del contenedor */
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
    white-space: nowrap;
    text-overflow: ellipsis;
}

button {
    margin-right: 10px;
}
</style>