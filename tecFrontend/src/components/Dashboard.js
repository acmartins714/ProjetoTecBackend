import React, { useEffect, useMemo, useState } from 'react';
import {
  usuarioService,
  conteudoService,
  funcionarioService,
  filmeService,
  planoService,
  assinaturaService,
  metodoPagamentoService,
  favoritoService,
  eventoAssistidoService,
} from '../services/api';

const formatDateTimeForBackend = (value) => {
  if (!value) {
    return null;
  }

  if (value.length === 16) {
    return `${value}:00`;
  }

  return value;
};

const formatDateTimeForInput = (value) => {
  if (!value) {
    return '';
  }

  return value.substring(0, 16);
};

const getNestedValue = (item, path) => {
  return path.split('.').reduce((value, key) => value?.[key], item);
};

const createEntityReference = (id) => {
  if (!id) {
    return null;
  }

  return { id: Number(id) };
};

const Dashboard = ({ activeMenu }) => {
  const configs = useMemo(() => ({
    usuarios: {
      title: 'Usuários',
      service: usuarioService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Nome Completo', field: 'nomeCompleto' },
        { label: 'E-mail', field: 'email' },
        { label: 'Perfil', field: 'perfil' },
        { label: 'Tipo Pessoa', field: 'tipoPessoa' },
      ],
      fields: [
        { name: 'nomeCompleto', label: 'Nome Completo', type: 'text', required: true },
        { name: 'dataNascimento', label: 'Data de Nascimento', type: 'date', required: true },
        { name: 'email', label: 'E-mail', type: 'email', required: true },
        { name: 'senhaHash', label: 'Senha', type: 'password', required: true },
        {
          name: 'tipoPessoa',
          label: 'Tipo Pessoa',
          type: 'select',
          required: true,
          options: ['FISICA', 'JURIDICA'],
        },
        { name: 'cpfCnpj', label: 'CPF/CNPJ', type: 'text', required: true },
        { name: 'fantasia', label: 'Fantasia', type: 'text' },
        { name: 'logradouro', label: 'Logradouro', type: 'text' },
        { name: 'numero', label: 'Número', type: 'text' },
        { name: 'complemento', label: 'Complemento', type: 'text' },
        { name: 'bairro', label: 'Bairro', type: 'text' },
        { name: 'municipio', label: 'Município', type: 'text' },
        { name: 'uf', label: 'UF', type: 'text', maxLength: 2 },
        { name: 'cep', label: 'CEP', type: 'text' },
        { name: 'telefone', label: 'Telefone', type: 'text' },
        {
          name: 'perfil',
          label: 'Perfil',
          type: 'select',
          required: true,
          options: ['CLIENTE', 'ADMINISTRADOR', 'FUNCIONARIO'],
        },
        { name: 'criadoEm', label: 'Criado em', type: 'datetime-local', required: true },
        { name: 'atualizadoEm', label: 'Atualizado em', type: 'datetime-local', required: true },
      ],
      mapItemToForm: (item) => ({
        ...item,
        criadoEm: formatDateTimeForInput(item.criadoEm),
        atualizadoEm: formatDateTimeForInput(item.atualizadoEm),
      }),
      normalizePayload: (formData) => ({
        ...formData,
        criadoEm: formatDateTimeForBackend(formData.criadoEm),
        atualizadoEm: formatDateTimeForBackend(formData.atualizadoEm),
      }),
    },

    funcionarios: {
      title: 'Funcionários',
      service: funcionarioService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Nome', field: 'nome' },
        { label: 'Cargo', field: 'cargo' },
        { label: 'Cidade', field: 'localidade' },
        { label: 'UF', field: 'uf' },
      ],
      fields: [
        { name: 'nome', label: 'Nome', type: 'text', required: true },
        { name: 'cargo', label: 'Cargo', type: 'text', required: true },
        { name: 'cep', label: 'CEP', type: 'text' },
        { name: 'logradouro', label: 'Logradouro', type: 'text' },
        { name: 'numero', label: 'Número', type: 'text' },
        { name: 'bairro', label: 'Bairro', type: 'text' },
        { name: 'localidade', label: 'Localidade', type: 'text' },
        { name: 'uf', label: 'UF', type: 'text', maxLength: 2 },
      ],
    },

    conteudos: {
      title: 'Conteúdos',
      service: conteudoService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Título', field: 'titulo' },
        { label: 'Tipo', field: 'tipo' },
        { label: 'Ano', field: 'ano' },
        { label: 'Duração', field: 'duracaoMinutos' },
        { label: 'Relevância', field: 'relevancia' },
      ],
      fields: [
        { name: 'titulo', label: 'Título', type: 'text', required: true },
        {
          name: 'tipo',
          label: 'Tipo',
          type: 'select',
          required: true,
          options: ['FILME', 'SERIE'],
        },
        { name: 'ano', label: 'Ano', type: 'number', required: true },
        { name: 'duracaoMinutos', label: 'Duração em minutos', type: 'number', required: true },
        { name: 'relevancia', label: 'Relevância', type: 'number', step: '0.01', required: true },
        { name: 'sinopse', label: 'Sinopse', type: 'textarea' },
        { name: 'trailerUrl', label: 'URL do Trailer', type: 'text' },
        { name: 'genero', label: 'Gênero', type: 'text' },
        { name: 'criadoEm', label: 'Criado em', type: 'datetime-local' },
        { name: 'atualizadoEm', label: 'Atualizado em', type: 'datetime-local' },
      ],
      mapItemToForm: (item) => ({
        ...item,
        criadoEm: formatDateTimeForInput(item.criadoEm),
        atualizadoEm: formatDateTimeForInput(item.atualizadoEm),
      }),
      normalizePayload: (formData) => ({
        ...formData,
        ano: Number(formData.ano),
        duracaoMinutos: Number(formData.duracaoMinutos),
        relevancia: Number(formData.relevancia),
        criadoEm: formatDateTimeForBackend(formData.criadoEm),
        atualizadoEm: formatDateTimeForBackend(formData.atualizadoEm),
      }),
    },

    filmes: {
      title: 'Filmes',
      service: filmeService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Título', field: 'titulo' },
        { label: 'Gênero', field: 'genero' },
        { label: 'Data Lançamento', field: 'dataLancamento' },
        { label: 'Duração', field: 'duracaoMinutos' },
        { label: 'Classificação', field: 'classificacaoIndicativa' },
      ],
      fields: [
        { name: 'titulo', label: 'Título', type: 'text', required: true },
        { name: 'sinopse', label: 'Sinopse', type: 'textarea', required: true },
        { name: 'dataLancamento', label: 'Data de Lançamento', type: 'date', required: true },
        { name: 'genero', label: 'Gênero', type: 'text', required: true },
        { name: 'duracaoMinutos', label: 'Duração em minutos', type: 'number', required: true },
        { name: 'classificacaoIndicativa', label: 'Classificação Indicativa', type: 'text', required: true },
      ],
      normalizePayload: (formData) => ({
        ...formData,
        duracaoMinutos: Number(formData.duracaoMinutos),
      }),
    },

    planos: {
      title: 'Planos',
      service: planoService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Código', field: 'codigo' },
        { label: 'Limite Diário', field: 'limiteDiario' },
        { label: 'Streams Simultâneos', field: 'streams_simultaneos' },
      ],
      fields: [
        { name: 'codigo', label: 'Código', type: 'text', required: true },
        { name: 'limiteDiario', label: 'Limite Diário', type: 'number', required: true },
        { name: 'streams_simultaneos', label: 'Streams Simultâneos', type: 'number', required: true },
      ],
      normalizePayload: (formData) => ({
        ...formData,
        limiteDiario: Number(formData.limiteDiario),
        streams_simultaneos: Number(formData.streams_simultaneos),
      }),
    },

    assinaturas: {
      title: 'Assinaturas',
      service: assinaturaService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Cliente', field: 'cliente.nomeCompleto' },
        { label: 'Plano', field: 'plano.codigo' },
        { label: 'Status', field: 'status' },
        { label: 'Iniciada em', field: 'iniciadaEm' },
        { label: 'Cancelada em', field: 'canceladaEm' },
      ],
      fields: [
        { name: 'clienteId', label: 'ID do Cliente', type: 'number', required: true },
        { name: 'planoId', label: 'ID do Plano', type: 'number', required: true },
        {
          name: 'status',
          label: 'Status',
          type: 'select',
          required: true,
          options: ['ATIVA', 'EM_ATRASO', 'CANCELADA'],
        },
        { name: 'iniciadaEm', label: 'Iniciada em', type: 'datetime-local', required: true },
        { name: 'canceladaEm', label: 'Cancelada em', type: 'datetime-local' },
      ],
      mapItemToForm: (item) => ({
        ...item,
        clienteId: item.cliente?.id || '',
        planoId: item.plano?.id || '',
        iniciadaEm: formatDateTimeForInput(item.iniciadaEm),
        canceladaEm: formatDateTimeForInput(item.canceladaEm),
      }),
      normalizePayload: (formData) => ({
        cliente: createEntityReference(formData.clienteId),
        plano: createEntityReference(formData.planoId),
        status: formData.status,
        iniciadaEm: formatDateTimeForBackend(formData.iniciadaEm),
        canceladaEm: formatDateTimeForBackend(formData.canceladaEm),
      }),
    },

    metodosPagamento: {
      title: 'Métodos de Pagamento',
      service: metodoPagamentoService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Usuário', field: 'usuario.nomeCompleto' },
        { label: 'Bandeira', field: 'bandeira' },
        { label: 'Últimos 4', field: 'ultimos4' },
        { label: 'Portador', field: 'nomePortador' },
        { label: 'Criado em', field: 'criadoEm' },
      ],
      fields: [
        { name: 'usuarioId', label: 'ID do Usuário', type: 'number', required: true },
        { name: 'bandeira', label: 'Bandeira', type: 'text', required: true },
        { name: 'ultimos4', label: 'Últimos 4 dígitos', type: 'text', maxLength: 4, required: true },
        { name: 'mesExp', label: 'Mês de Expiração', type: 'number', required: true },
        { name: 'anoExp', label: 'Ano de Expiração', type: 'number', required: true },
        { name: 'nomePortador', label: 'Nome do Portador', type: 'text', required: true },
        { name: 'tokenGateway', label: 'Token Gateway', type: 'text', required: true },
        { name: 'criadoEm', label: 'Criado em', type: 'datetime-local', required: true },
      ],
      mapItemToForm: (item) => ({
        ...item,
        usuarioId: item.usuario?.id || '',
        criadoEm: formatDateTimeForInput(item.criadoEm),
      }),
      normalizePayload: (formData) => ({
        usuario: createEntityReference(formData.usuarioId),
        bandeira: formData.bandeira,
        ultimos4: formData.ultimos4,
        mesExp: Number(formData.mesExp),
        anoExp: Number(formData.anoExp),
        nomePortador: formData.nomePortador,
        tokenGateway: formData.tokenGateway,
        criadoEm: formatDateTimeForBackend(formData.criadoEm),
      }),
    },

    favoritos: {
      title: 'Favoritos',
      service: favoritoService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Usuário', field: 'usuario.nomeCompleto' },
        { label: 'Conteúdo', field: 'conteudo.titulo' },
      ],
      fields: [
        { name: 'usuarioId', label: 'ID do Usuário', type: 'number', required: true },
        { name: 'conteudoId', label: 'ID do Conteúdo', type: 'number', required: true },
      ],
      mapItemToForm: (item) => ({
        ...item,
        usuarioId: item.usuario?.id || item.id?.usuarioId || '',
        conteudoId: item.conteudo?.id || item.id?.conteudoId || '',
      }),
      normalizePayload: (formData) => ({
        usuario: createEntityReference(formData.usuarioId),
        conteudo: createEntityReference(formData.conteudoId),
      }),
    },

    eventosAssistidos: {
      title: 'Eventos Assistidos',
      service: eventoAssistidoService,
      idField: 'id',
      columns: [
        { label: 'ID', field: 'id' },
        { label: 'Usuário', field: 'usuario.nomeCompleto' },
        { label: 'Conteúdo', field: 'conteudo.titulo' },
        { label: 'Assistido em', field: 'assistidoEm' },
        { label: 'Progresso', field: 'progressoSegundos' },
      ],
      fields: [
        { name: 'usuarioId', label: 'ID do Usuário', type: 'number', required: true },
        { name: 'conteudoId', label: 'ID do Conteúdo', type: 'number', required: true },
        { name: 'assistidoEm', label: 'Assistido em', type: 'datetime-local', required: true },
        { name: 'progressoSegundos', label: 'Progresso em segundos', type: 'number', required: true },
      ],
      mapItemToForm: (item) => ({
        ...item,
        usuarioId: item.usuario?.id || item.id?.usuarioId || '',
        conteudoId: item.conteudo?.id || item.id?.conteudoId || '',
        assistidoEm: formatDateTimeForInput(item.assistidoEm),
      }),
      normalizePayload: (formData) => ({
        usuario: createEntityReference(formData.usuarioId),
        conteudo: createEntityReference(formData.conteudoId),
        assistidoEm: formatDateTimeForBackend(formData.assistidoEm),
        progressoSegundos: Number(formData.progressoSegundos),
      }),
    },
  }), []);

  const currentConfig = configs[activeMenu];

  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [editItem, setEditItem] = useState(null);
  const [formData, setFormData] = useState({});

  useEffect(() => {
    fetchData();
  }, [activeMenu]);

  const fetchData = async () => {
    if (!currentConfig) {
      return;
    }

    setLoading(true);

    try {
      const response = await currentConfig.service.listar();
      setData(Array.isArray(response?.data) ? response.data : []);
    } catch (error) {
      console.error('Erro ao buscar dados do back-end:', error);
      setData([]);
      alert('Não foi possível carregar os dados. Verifique se o backend está rodando e se o endpoint existe.');
    } finally {
      setLoading(false);
    }
  };

  const handleOpenModal = (item = null) => {
    if (item) {
      setEditItem(item);
      setFormData(currentConfig.mapItemToForm ? currentConfig.mapItemToForm(item) : item);
    } else {
      setEditItem(null);
      setFormData({});
    }

    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
    setEditItem(null);
    setFormData({});
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;

    setFormData((oldValue) => ({
      ...oldValue,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const payload = currentConfig.normalizePayload
          ? currentConfig.normalizePayload(formData)
          : formData;

      if (editItem) {
        await currentConfig.service.atualizar(editItem[currentConfig.idField], payload);
      } else {
        await currentConfig.service.salvar(payload);
      }

      handleCloseModal();
      fetchData();
    } catch (error) {
      console.error('Erro ao salvar registro:', error);
      alert('Erro ao salvar registro. Verifique os campos obrigatórios e o formato esperado pelo backend.');
    }
  };

  const handleDeletar = async (item) => {
    const id = item[currentConfig.idField];

    if (!id || typeof id === 'object') {
      alert('Não foi possível identificar um ID simples para exclusão deste registro.');
      return;
    }

    if (!window.confirm('Deseja realmente excluir este registro?')) {
      return;
    }

    try {
      await currentConfig.service.deletar(id);
      fetchData();
    } catch (error) {
      console.error('Erro ao excluir registro:', error);
      alert('Erro ao excluir registro. Verifique se o backend permite exclusão deste recurso.');
    }
  };

  const renderField = (field) => {
    const commonProps = {
      name: field.name,
      value: formData[field.name] || '',
      onChange: handleInputChange,
      required: field.required || false,
      maxLength: field.maxLength,
      step: field.step,
      className: 'form-input',
    };

    if (field.type === 'select') {
      return (
          <select {...commonProps}>
            <option value="">Selecione...</option>
            {field.options.map((option) => (
                <option key={option} value={option}>
                  {option}
                </option>
            ))}
          </select>
      );
    }

    if (field.type === 'textarea') {
      return (
          <textarea
              {...commonProps}
              rows="3"
          />
      );
    }

    return (
        <input
            {...commonProps}
            type={field.type || 'text'}
        />
    );
  };

  if (!currentConfig) {
    return <p>Menu não configurado.</p>;
  }

  if (loading) {
    return <p>Carregando registros...</p>;
  }

  return (
      <div>
        <div className="dashboard-header">
          <h3>Gerenciar {currentConfig.title}</h3>

          <button
              className="btn-save"
              onClick={() => handleOpenModal()}
          >
            + Novo Registro
          </button>
        </div>

        <div className="table-wrapper">
          <table className="data-table">
            <thead>
            <tr>
              {currentConfig.columns.map((column) => (
                  <th key={column.field}>{column.label}</th>
              ))}
              <th>Ações</th>
            </tr>
            </thead>

            <tbody>
            {data.length === 0 ? (
                <tr>
                  <td colSpan={currentConfig.columns.length + 1}>
                    Nenhum registro encontrado.
                  </td>
                </tr>
            ) : (
                data.map((item, index) => (
                    <tr key={item[currentConfig.idField] || index}>
                      {currentConfig.columns.map((column) => (
                          <td key={column.field}>
                            {String(getNestedValue(item, column.field) ?? '-')}
                          </td>
                      ))}

                      <td>
                        <button
                            className="btn-edit"
                            onClick={() => handleOpenModal(item)}
                        >
                          Editar
                        </button>

                        <button
                            className="btn-delete"
                            onClick={() => handleDeletar(item)}
                        >
                          Excluir
                        </button>
                      </td>
                    </tr>
                ))
            )}
            </tbody>
          </table>
        </div>

        {showModal && (
            <div className="modal-overlay">
              <div className="modal-content">
                <h4>
                  {editItem ? `Editar ${currentConfig.title}` : `Novo ${currentConfig.title}`}
                </h4>

                <form onSubmit={handleSubmit}>
                  <div className="form-grid">
                    {currentConfig.fields.map((field) => (
                        <div className="form-group" key={field.name}>
                          <label>{field.label}</label>
                          {renderField(field)}
                        </div>
                    ))}
                  </div>

                  <div className="modal-actions">
                    <button
                        type="button"
                        className="btn-cancel"
                        onClick={handleCloseModal}
                    >
                      Cancelar
                    </button>

                    <button
                        type="submit"
                        className="btn-save"
                    >
                      Salvar
                    </button>
                  </div>
                </form>
              </div>
            </div>
        )}
      </div>
  );
};

export default Dashboard;