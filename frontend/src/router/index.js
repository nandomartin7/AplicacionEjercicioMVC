import { createRouter, createWebHistory } from 'vue-router';
import ListaContratos from '../components/ListaContratos.vue';
import BuscarContratos from '@/components/BuscarContratos.vue';
import FormularioContrato from '@/components/FormularioContrato.vue';
import Index from '../components/Index.vue';

const routes = [
  {
    path: '/',
    name: 'LoginPage',
    component: Index
  },
  {
    path: '/lista-contratos',
    name: 'ListaContratos',
    component: ListaContratos,
    meta: { requiresAuth: true } // Proteger esta ruta
  },
  {
    path: '/formulario-contrato',
    name: 'FormularioContrato',
    component: FormularioContrato,
    meta: { requiresAuth: true }
  },
  {
    path: '/filtrar',
    name: 'BuscarContratos',
    component: BuscarContratos
  },
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