import React, { useContext, useEffect, useState } from 'react';
import { ThemeProvider, ThemeContext } from './context/ThemeContext';
import Dashboard from './components/Dashboard';
import Login from './components/Login';
import './index.css';

const AppContent = () => {
  const { theme, toggleTheme } = useContext(ThemeContext);
  const [activeMenu, setActiveMenu] = useState('conteudos');
  const [usuarioLogado, setUsuarioLogado] = useState(null);

    useEffect(() => {
        const usuarioSalvo = localStorage.getItem('usuarioLogado');

        if (usuarioSalvo) {
            setUsuarioLogado(JSON.parse(usuarioSalvo));
        }
    }, []);

    const handleLogin = (usuario) => {
        setUsuarioLogado(usuario);
    };

    const handleLogout = () => {
        localStorage.removeItem('usuarioLogado');
        setUsuarioLogado(null);
    };

    const menus = [
    { key: 'conteudos', label: '📺 Conteúdos' },
    { key: 'filmes', label: '🎬 Filmes' },
    { key: 'usuarios', label: '👥 Usuários' },
    { key: 'funcionarios', label: '👷 Funcionários' },
    { key: 'planos', label: '💳 Planos' },
    { key: 'assinaturas', label: '🧾 Assinaturas' },
    { key: 'metodosPagamento', label: '💰 Métodos de Pagamento' },
    { key: 'favoritos', label: '⭐ Favoritos' },
    { key: 'eventosAssistidos', label: '▶️ Eventos Assistidos' },
  ];

  if (!usuarioLogado) {
      return <Login onLogin={handleLogin} />;
  }

  return (
      <div className="app-container">
        <aside className="sidebar">
          <h2>IESPFlix</h2>

          <ul>
            {menus.map((menu) => (
                <li
                    key={menu.key}
                    className={activeMenu === menu.key ? 'active' : ''}
                    onClick={() => setActiveMenu(menu.key)}
                >
                  {menu.label}
                </li>
            ))}
          </ul>
        </aside>

        <main className="main-content">
            <header className="navbar">
                <div>
                    <h2>Painel Administrativo</h2>
                    <p className="navbar-subtitle">
                        Logado como {usuarioLogado.email}
                    </p>
                </div>

                <div className="navbar-actions">
                    <button className="theme-btn" onClick={toggleTheme}>
                        {theme === 'light' ? '🌙 Modo Escuro' : '☀️ Modo Claro'}
                    </button>

                    <button className="logout-btn" onClick={handleLogout}>
                        Sair
                    </button>
                </div>
            </header>

          <Dashboard activeMenu={activeMenu} />
        </main>
      </div>
  );
};

function App() {
  return (
      <ThemeProvider>
        <AppContent />
      </ThemeProvider>
  );
}

export default App;