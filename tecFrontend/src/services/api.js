import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

export const usuarioService = {
  listar: () => api.get('/usuarios'),
  buscarPorId: (id) => api.get(`/usuarios/${id}`),
  salvar: (dados) => api.post('/usuarios', dados),
  deletar: (id) => api.delete(`/usuarios/${id}`),
};

export const conteudoService = {
  listar: () => api.get('/conteudos'),
  buscarPorId: (id) => api.get(`/conteudos/${id}`),
  salvar: (dados) => api.post('/conteudos', dados),
  deletar: (id) => api.delete(`/conteudos/${id}`),
};

// Você pode estender para os outros serviços (filmes, planos, assinaturas) seguindo a mesma lógica.
export default api;