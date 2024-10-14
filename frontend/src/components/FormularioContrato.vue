<template>
    <div class="Agregar-container">
        <h2>Agregar Contrato</h2>
        <form @submit.prevent="submitForm" class="formularioRegistrarContrato">
            <p><input v-model="contrato.cliente" class="input-field" placeholder="Nombre del Cliente" required/></p>
            <p><input v-model="contrato.cedula" class="input-field" placeholder="Cedula del Cliente" required/></p>
            <p><input v-model="contrato.descripcion" class="input-field" placeholder="Descripcion del Contrato" required/></p>
            <p><input type="number" v-model="contrato.valor" class="input-field" step="0.01" placeholder="Valor del contrato" required/></p>
            <p>
               <label class="label">Estado Activo:
                    <select v-model="contrato.estado" class="input-select">
                        <option :value="true">Activo</option>
                        <option :value="false">Inactivo</option>
                    </select>
                </label>
            </p>
            <p><label class="label">Fecha Inicion: </label></p>
            <p><input type="date" v-model="contrato.fechaInicio" class="input-date" required></p>
            <p><label class="label">Fecha Finalizacion:</label></p> 
            <p><input type="date" v-model="contrato.fechaFin" class="input-date" required ></p>
            
            <button type="submit" class="btnGuardar">Guardar</button>
        </form>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            contrato: {
                cliente: '',
                cedula: '',
                descripcion: '',
                empleado: '',
                valor: '',
                estado: true,
                fechaInicio: '',
                fechaFin: ''
            }
        };
    },

    methods: {
        submitForm() {
            const empleado = JSON.parse(localStorage.getItem('empleado'));
            this.contrato.empleado = empleado;

            axios.post('https://easygoing-analysis-production.up.railway.app/contratos', this.contrato)
            .then(() => {
                this.$router.push('/lista-contratos'); //Regresa a la lista de clientes luego de guardar
            })
            .catch( error => {
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

.input-select{
  width: 50%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}

.input-date{
  width: 70%; /* Los inputs ocupan el % del contenedor */
  padding: 12px; /* Espacio interior de los inputs */
  border: 1px solid #dddddd; /* Borde del input */
  border-radius: 10px; /* Bordes redondeados */
  font-size: 16px; /* Tamaño de la fuente */
  background-color: #f9f9f9;  
  transition: border-color 0.3s; 
  text-align: center; 
}

.label{
    font-size: 18px;
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