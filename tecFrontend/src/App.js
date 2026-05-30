import React, { useContext, useState } from 'react';
import { ThemeProvider, ThemeContext } from './context/ThemeContext';
import Dashboard from './components/Dashboard';
import './index.css';

const AppContent = () => {
  const { theme, toggleTheme } = useContext(ThemeContext);
  const [activeMenu, setActiveMenu] = useState('conteudos'); // Tela inicial padrão

  return (
    <div className="app-container">
      {/* Sidebar de Navegação baseada nos Controllers do Swagger */}
      <aside className="sidebar">
        <h2>IESPFlix</h2>
        <ul>
          <li className={activeMenu === 'conteudos' ? 'active' : ''} onClick={() => setActiveMenu('conteudos')}>
            📺 Catálogo Geral
          </li>
          <li className={activeMenu === 'usuarios' ? 'active' : ''} onClick={() => setActiveMenu('usuarios')}>
            👥 Usuários
          </li>
          <li className={activeMenu === 'funcionarios' ? 'active' : ''} onClick={() => setActiveMenu('funcionarios')}>
            👷 Funcionários
          </li>
        </ul>
      </aside>

      {/* Área de Conteúdo Principal */}
      <main className="main-content">
        <header className="navbar">
          <h2>Painel Administrativo</h2>
          <button className="theme-btn" onClick={toggleTheme}>
            {theme === 'light' ? '🌙 Modo Escuro' : '☀️ Modo Claro'}
          </button>
        </header>

        <Dashboard activeMenu={activeMenu} />
      </main>
    </div>
  );
};

// Encapsulando a aplicação com o provedor de tema global
function App() {
  return (
    <ThemeProvider>
      <AppContent />
    </ThemeProvider>
  );
}

export default App;