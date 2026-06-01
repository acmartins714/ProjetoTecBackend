import React, { useState } from 'react';
import { authService } from '../services/api';

const Login = ({ onLogin }) => {
    const [formData, setFormData] = useState({
        email: '',
        senha: '',
    });

    const [loading, setLoading] = useState(false);
    const [erro, setErro] = useState('');

    const handleInputChange = (event) => {
        const { name, value } = event.target;

        setFormData((oldValue) => ({
            ...oldValue,
            [name]: value,
        }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        setErro('');
        setLoading(true);

        try {
            const response = await authService.login(formData);

            localStorage.setItem('usuarioLogado', JSON.stringify(response.data));

            onLogin(response.data);
        } catch (error) {
            console.error('Erro ao realizar login:', error);
            setErro('E-mail ou senha inválidos.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">
            <div className="login-card">
                <h1>IESPFlix</h1>
                <h2>Login Administrativo</h2>

                <form onSubmit={handleSubmit}>
                    <div className="login-form-group">
                        <label>E-mail</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleInputChange}
                            required
                            placeholder="Digite seu e-mail"
                        />
                    </div>

                    <div className="login-form-group">
                        <label>Senha</label>
                        <input
                            type="password"
                            name="senha"
                            value={formData.senha}
                            onChange={handleInputChange}
                            required
                            placeholder="Digite sua senha"
                        />
                    </div>

                    {erro && (
                        <p className="login-error">
                            {erro}
                        </p>
                    )}

                    <button type="submit" className="login-button" disabled={loading}>
                        {loading ? 'Entrando...' : 'Entrar'}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default Login;