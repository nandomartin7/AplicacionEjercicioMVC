import { createRouter, createWebHistory } from 'vue-router';
import ListaClientes from '../components/ListaClientes.vue';
import FormularioCliente from '../components/FormularioCliente.vue';
import LoginPage from '../components/LoginPage.vue'; // Importa el nuevo componente 

const routes = [
  {
    path: '/',
    name: 'ListaClientes',
    component: ListaClientes,
    meta: { requiresAuth: true } // Proteger esta ruta
  },
  {
    path: '/formulario-cliente',
    name: 'FormularioCliente',
    component: FormularioCliente,
    meta: { requiresAuth: true } // Proteger esta ruta
  },
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

// Proteger las rutas
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token');
  if (to.meta.requiresAuth && !token) {
    next({ name: 'LoginPage' }); // Redirigir a Login si no hay token
  } else {
    next();
  }
});

export default router;