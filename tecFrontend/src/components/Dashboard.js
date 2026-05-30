import React, { useState, useEffect } from 'react';
import { usuarioService, conteudoService, funcionarioService } from '../services/api';

const Dashboard = ({ activeMenu }) => {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [editItem, setEditItem] = useState(null);
  const [formData, setFormData] = useState({});

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
      } else if (activeMenu === 'funcionarios') {
        response = await funcionarioService.listar();
      }
      setData(response?.data || []);
    } catch (error) {
      console.error("Erro ao buscar dados do back-end:", error);
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
    } else if (activeMenu === 'funcionarios') {
        setData([
            { id: 1, nome: 'Ana Silva', cargo: 'Gerente', localidade: 'João Pessoa', uf: 'PB' },
            { id: 2, nome: 'Carlos Souza', cargo: 'Desenvolvedor', localidade: 'Recife', uf: 'PE' }
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
        } else if (activeMenu === 'funcionarios') {
          await funcionarioService.deletar(id);
        } else {
          await conteudoService.deletar(id);
        }
        fetchData();
      } catch (err) {
        alert("Erro ao deletar, removendo localmente (Interface).");
        setData(data.filter(item => item.id !== id));
      }
    }
  };

  const handleOpenModal = (item = null) => {
    if (item) {
      setEditItem(item);
      setFormData(item);
    } else {
      setEditItem(null);
      setFormData({});
    }
    setShowModal(true);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (activeMenu === 'funcionarios') {
        if (editItem) {
          await funcionarioService.atualizar(editItem.id, formData);
        } else {
          await funcionarioService.salvar(formData);
        }
      } else {
          alert("Funcionalidade de salvar disponível apenas para Funcionários neste exemplo.");
          return;
      }
      setShowModal(false);
      fetchData();
    } catch (error) {
      console.error("Erro ao salvar:", error);
      alert("Erro ao salvar registro.");
    }
  };

  if (loading) return <p>Carregando registros...</p>;

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <h3>Gerenciar {
          activeMenu === 'usuarios' ? 'Usuários' : 
          activeMenu === 'funcionarios' ? 'Funcionários' : 'Catálogo de Conteúdos'
        }</h3>
        {activeMenu === 'funcionarios' && (
          <button className="btn-save" onClick={() => handleOpenModal()} style={{ marginBottom: '20px', padding: '10px 20px', backgroundColor: '#e50914', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            + Novo Funcionário
          </button>
        )}
      </div>

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
          ) : activeMenu === 'funcionarios' ? (
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Cargo</th>
              <th>Cidade/UF</th>
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
              ) : activeMenu === 'funcionarios' ? (
                <>
                  <td>{item.nome}</td>
                  <td>{item.cargo}</td>
                  <td>{item.localidade}/{item.uf}</td>
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
                {activeMenu === 'funcionarios' && (
                  <button className="btn-edit" onClick={() => handleOpenModal(item)} style={{ marginRight: '10px', backgroundColor: '#555', color: 'white', border: 'none', padding: '5px 10px', borderRadius: '4px', cursor: 'pointer' }}>Editar</button>
                )}
                <button className="btn-delete" onClick={() => handleDeletar(item.id)}>Excluir</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && (
        <div className="modal-overlay" style={{ position: 'fixed', top: 0, left: 0, right: 0, bottom: 0, backgroundColor: 'rgba(0,0,0,0.7)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000 }}>
          <div className="modal-content" style={{ backgroundColor: '#141414', padding: '30px', borderRadius: '8px', width: '400px', color: 'white' }}>
            <h4>{editItem ? 'Editar Funcionário' : 'Novo Funcionário'}</h4>
            <form onSubmit={handleSubmit}>
              <div style={{ marginBottom: '15px' }}>
                <label style={{ display: 'block', marginBottom: '5px' }}>Nome:</label>
                <input type="text" name="nome" value={formData.nome || ''} onChange={handleInputChange} required style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #333', backgroundColor: '#222', color: 'white' }} />
              </div>
              <div style={{ marginBottom: '15px' }}>
                <label style={{ display: 'block', marginBottom: '5px' }}>Cargo:</label>
                <input type="text" name="cargo" value={formData.cargo || ''} onChange={handleInputChange} required style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #333', backgroundColor: '#222', color: 'white' }} />
              </div>
              <div style={{ marginBottom: '15px' }}>
                <label style={{ display: 'block', marginBottom: '5px' }}>CEP:</label>
                <input type="text" name="cep" value={formData.cep || ''} onChange={handleInputChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #333', backgroundColor: '#222', color: 'white' }} />
              </div>
              <div style={{ display: 'flex', gap: '10px', marginBottom: '15px' }}>
                <div style={{ flex: 2 }}>
                  <label style={{ display: 'block', marginBottom: '5px' }}>Localidade:</label>
                  <input type="text" name="localidade" value={formData.localidade || ''} onChange={handleInputChange} style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #333', backgroundColor: '#222', color: 'white' }} />
                </div>
                <div style={{ flex: 1 }}>
                  <label style={{ display: 'block', marginBottom: '5px' }}>UF:</label>
                  <input type="text" name="uf" value={formData.uf || ''} onChange={handleInputChange} maxLength="2" style={{ width: '100%', padding: '8px', borderRadius: '4px', border: '1px solid #333', backgroundColor: '#222', color: 'white' }} />
                </div>
              </div>
              <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '10px' }}>
                <button type="button" onClick={() => setShowModal(false)} style={{ padding: '8px 15px', borderRadius: '4px', border: 'none', cursor: 'pointer' }}>Cancelar</button>
                <button type="submit" style={{ padding: '8px 15px', borderRadius: '4px', border: 'none', backgroundColor: '#e50914', color: 'white', cursor: 'pointer' }}>Salvar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Dashboard;