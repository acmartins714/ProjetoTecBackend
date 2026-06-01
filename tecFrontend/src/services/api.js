import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

const criarCrudService = (endpoint) => ({
  listar: () => api.get(endpoint),
  buscarPorId: (id) => api.get(`${endpoint}/${id}`),
  salvar: (dados) => api.post(endpoint, dados),
  atualizar: (id, dados) => api.put(`${endpoint}/${id}`, dados),
  deletar: (id) => api.delete(`${endpoint}/${id}`),
});

export const authService = {
  login: (dados) => api.post('/usuarios/login', dados),
};

export const usuarioService = criarCrudService('/usuarios');

export const conteudoService = criarCrudService('/conteudos');

export const funcionarioService = criarCrudService('/funcionarios');

export const filmeService = criarCrudService('/filmes');

export const planoService = criarCrudService('/planos');

export const assinaturaService = criarCrudService('/assinaturas');

export const metodoPagamentoService = criarCrudService('/metodopagamento');

export const favoritoService = criarCrudService('/favoritos');

export const eventoAssistidoService = criarCrudService('/eventosassistidos');

export default api;