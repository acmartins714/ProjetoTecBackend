import React, { useState, useEffect } from 'react';
import { usuarioService, conteudoService } from '../services/api';

const Dashboard = ({ activeMenu }) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
  }, [activeMenu]);

  const fetchData = async () => {
    setLoading(true);
    try {
      let response;
      if (activeMenu === 'usuarios') {
        response = await usuarioService.listar();
      } else if (activeMenu === 'conteudos') {
        response = await conteudoService.listar();
      }
      setData(response?.data || []);
    } catch (error) {
      console.error("Erro ao buscar dados do back-end:", error);
      // Mock de segurança caso a API do backend ainda não esteja de pé na porta 8080
      setMockData();
    } finally {
      setLoading(false);
    }
  };

  const setMockData = () => {
    if (activeMenu === 'usuarios') {
      setData([
        { id: 1, nomeCompleto: 'Alexandre Martins', email: 'alexandre@email.com', perfil: 'ADMINISTRADOR', tipoPessoa: 'FISICA' },
        { id: 2, nomeCompleto: 'João Silva', email: 'joao@email.com', perfil: 'USUARIO', tipoPessoa: 'FISICA' }
      ]);
    } else {
      setData([
        { id: 1, titulo: 'O Senhor dos Anéis', tipo: 'FILME', ano: 2001, relevancia: 98 },
        { id: 2, titulo: 'Breaking Bad', tipo: 'SERIE', ano: 2008, relevancia: 99 }
      ]);
    }
  };

  const handleDeletar = async (id) => {
    if (window.confirm("Deseja realmente excluir este registro?")) {
      try {
        if (activeMenu === 'usuarios') {
          await usuarioService.deletar(id);
        } else {
          await conteudoService.deletar(id);
        }
        fetchData(); // Atualiza a lista
      } catch (err) {
        alert("Erro ao deletar, removendo localmente (Interface).");
        setData(data.filter(item => item.id !== id));
      }
    }
  };

  if (loading) return <p>Carregando registros...</p>;

  return (
    <div>
      <h3>Gerenciar {activeMenu === 'usuarios' ? 'Usuários' : 'Catálogo de Conteúdos'}</h3>
      <table className="data-table">
        <thead>
          {activeMenu === 'usuarios' ? (
            <tr>
              <th>ID</th>
              <th>Nome Completo</th>
              <th>E-mail</th>
              <th>Perfil</th>
              <th>Ações</th>
            </tr>
          ) : (
            <tr>
              <th>ID</th>
              <th>Título</th>
              <th>Tipo</th>
              <th>Ano Lançamento</th>
              <th>Relevância (%)</th>
              <th>Ações</th>
            </tr>
          )}
        </thead>
        <tbody>
          {data.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>
              {activeMenu === 'usuarios' ? (
                <>
                  <td>{item.nomeCompleto}</td>
                  <td>{item.email}</td>
                  <td><strong>{item.perfil}</strong></td>
                </>
              ) : (
                <>
                  <td>{item.titulo}</td>
                  <td>{item.tipo}</td>
                  <td>{item.ano}</td>
                  <td>{item.relevancia}%</td>
                </>
              )}
              <td>
                <button className="btn-delete" onClick={() => handleDeletar(item.id)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Dashboard;